package com.ayesh.spectrum.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayesh.spectrum.R
import com.ayesh.spectrum.data.mapper.toMovieModel
import com.ayesh.spectrum.extention.showDialogAlert
import com.ayesh.spectrum.presentation.adapter.MovieListAdapter
import com.ayesh.spectrum.presentation.ui.MovieDetailsActivity
import com.ayesh.spectrum.presentation.vm.DashboardViewModel
import com.ayesh.spectrum.utils.Constants
import com.ayesh.spectrum.utils.Resource
import com.xiteb.argo.presentation.state.DialogState
import kotlinx.android.synthetic.main.fragment_now_playing.*


class NowPlayingFragment : Fragment() {

    private lateinit var movieAdapter: MovieListAdapter
    private lateinit var dashboardViewModel: DashboardViewModel
    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false
    private var totalPage = 1
    private var playNowPagePage = 1


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGridView()
        dashboardViewModel.getPlayNowMovieList(playNowPagePage, URL_CATEGORY)
        dashboardViewModel.playNowResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    nowPlayingLoadingTextView.visibility = View.GONE
                    it.data?.let { data ->
                        movieAdapter.addAll(data.results.map { model ->
                            model.toMovieModel()
                        })
                        totalPage = data.total_pages
                    }
                }
                is Resource.Error -> {
                    nowPlayingLoadingTextView.visibility = View.GONE
                    showDialogAlert(it.message, type = DialogState.Error)
                }
                is Resource.Loading -> {
                    if (playNowPagePage > 1) {
                        nowPlayingLoadingTextView.visibility = View.VISIBLE
                    }
                }
            }
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
                playNowPagePage++
                if (playNowPagePage <= totalPage) {
                    dashboardViewModel.getPlayNowMovieList(
                        playNowPagePage,
                        URL_CATEGORY
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProvider(requireActivity())[DashboardViewModel::class.java]
        return inflater.inflate(R.layout.fragment_now_playing, container, false)
    }

    private fun setupGridView() {
        movieAdapter = MovieListAdapter(requireActivity())
        nowPlayingRecyclerView.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = movieAdapter
            addOnScrollListener(this@NowPlayingFragment.scrollListener)
        }

        movieAdapter.setOnItemClickListener {
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra("id", it.id)
            startActivity(intent)
        }


    }

    override fun onStart() {
        super.onStart()
    }

    companion object {
        var URL_CATEGORY = ""
        fun newInstance(category: String): NowPlayingFragment {
            val fragment = NowPlayingFragment()
            URL_CATEGORY = category
            return fragment
        }
    }
}