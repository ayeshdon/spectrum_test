package com.ayesh.spectrum.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayesh.spectrum.R
import com.ayesh.spectrum.data.mapper.toMovieModel
import com.ayesh.spectrum.extention.showDialogAlert
import com.ayesh.spectrum.presentation.adapter.MovieListAdapter
import com.ayesh.spectrum.presentation.vm.DashboardViewModel
import com.ayesh.spectrum.utils.Resource
import com.xiteb.argo.presentation.state.DialogState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_search_result.*

@AndroidEntryPoint
class SearchResultActivity : AppCompatActivity() {
    private lateinit var searchText: String
    private val viewModel: DashboardViewModel by viewModels()
    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false
    private var totalPage = 1
    private var searchPage = 1
    private lateinit var movieAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        searchText = intent.extras?.getString("query").toString()
        searchTextView.text = "You search \"$searchText\" "
        backImageView.setOnClickListener { finish() }

        setupGridView()
        getData()

    }

    private fun getData() {
        viewModel.searchMovieFromAPI(searchText, searchPage)
        viewModel.searchResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    searchLoadingTextView.visibility = View.GONE
                    it.data?.let { data ->
                        movieAdapter.addAll(data.results.map { model ->
                            model.toMovieModel()
                        })
                        totalPage = data.total_pages
                    }
                }
                is Resource.Error -> {
                    searchLoadingTextView.visibility = View.GONE
                    showDialogAlert(it.message, type = DialogState.Error)
                }
                is Resource.Loading -> {
                    if (searchPage > 1) {
                        searchLoadingTextView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setupGridView() {
        movieAdapter = MovieListAdapter(this)
        nowPlayingRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = movieAdapter
            addOnScrollListener(this@SearchResultActivity.scrollListener)
        }

        movieAdapter.setOnItemClickListener {
            val intent = Intent(this@SearchResultActivity, MovieDetailsActivity::class.java)
            intent.putExtra("id", it.id)
            startActivity(intent)
        }


    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val shouldPaginate =
                isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isScrolling
            if (shouldPaginate) {
                searchPage++
                if (searchPage <= totalPage) {
                    viewModel.searchMovieFromAPI(
                        searchText,
                        searchPage
                    )
                    isScrolling = false
                } else {

                }

            } else {
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }
}