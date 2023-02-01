package com.ayesh.spectrum.presentation.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ayesh.spectrum.R
import com.ayesh.spectrum.data.mapper.toMovieModel
import com.ayesh.spectrum.extention.showDialogAlert
import com.ayesh.spectrum.presentation.adapter.MovieListAdapter
import com.ayesh.spectrum.presentation.ui.MovieDetailsActivity
import com.ayesh.spectrum.presentation.vm.DashboardViewModel
import com.ayesh.spectrum.utils.Resource
import com.xiteb.argo.presentation.state.DialogState
import kotlinx.android.synthetic.main.fragment_now_playing.*

class FavMoviesFragment : Fragment() {

    private lateinit var movieAdapter: MovieListAdapter
    private lateinit var dashboardViewModel: DashboardViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProvider(requireActivity())[DashboardViewModel::class.java]
        return inflater.inflate(R.layout.fragment_fav_movies_rated, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGridView()
        dashboardViewModel.getFavMovieList()
        dashboardViewModel.favMoviesResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    it.data?.let { data ->
                        movieAdapter.addAll(data.map { model ->
                            model.toMovieModel()
                        })
                    }
                }
                is Resource.Error -> {
                    showDialogAlert(it.message, type = DialogState.Error)
                }
                is Resource.Loading -> {
                }
            }
        }
    }
    private fun setupGridView() {
        movieAdapter = MovieListAdapter(requireActivity())
        nowPlayingRecyclerView.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = movieAdapter
        }

        movieAdapter.setOnItemClickListener {
            val intent = Intent(activity, MovieDetailsActivity::class.java)
            intent.putExtra("id", it.id)
            startActivity(intent)
        }


    }
    companion object {

        fun newInstance() =
            FavMoviesFragment()
    }
}