package com.ayesh.spectrum.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ayesh.spectrum.R
import com.ayesh.spectrum.data.local.entity.MovieEntity
import com.ayesh.spectrum.data.mapper.toMovieEntity
import com.ayesh.spectrum.domain.model.MovieDetailModel
import com.ayesh.spectrum.extention.showDialogAlert
import com.ayesh.spectrum.presentation.vm.MovieDetailViewModel
import com.ayesh.spectrum.utils.Constants
import com.ayesh.spectrum.utils.Resource
import com.bumptech.glide.Glide
import com.xiteb.argo.presentation.state.DialogState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie_details.*

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private val viewModel: MovieDetailViewModel by viewModels()
    private var movieId: Int = 0
    private lateinit var entity: MovieEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        movieId = intent.getIntExtra("id", 0)
        backImageView.setOnClickListener { finish() }

        favImageView.setOnClickListener {
            viewModel.saveMovieToLocal(entity)
            //setupFavView(entity.id)
        }

        viewModel.updateFavDBChange.observe(this){
            setupFavView(entity.id)
        }

        viewModel.getMovieDetails(movieId)
        viewModel.movieDetailResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    updateUis(it.data)

                }
                is Resource.Error -> {
                    progressBar.visibility = View.GONE
                    showDialogAlert(it.message, type = DialogState.Error)
                }
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                else -> {

                }
            }
        }
    }

    private fun updateUis(data: MovieDetailModel?) {
        data?.let {
            entity = data.toMovieEntity()
            setupFavView(data.id)
            Glide
                .with(this)
                .load(Constants.IMAGE_URL_PREFIX + it.backDrop)
                .placeholder(R.drawable.ic_baseline_movie_24)
                .into(backDropImageView)
            Glide
                .with(this)
                .load(Constants.IMAGE_URL_PREFIX + it.poster)
                .placeholder(R.drawable.ic_baseline_movie_24)
                .into(posterImageView)

            movieTitleTextView.text = it.title
            movieTagTextView.text = it.tagLine
            overViewTextView.text = it.overview
            ratingTextView.text = it.voteAvg
            languageTextView.text = it.languages
            statusTextView.text = it.status
            genresTextView.text = it.genreIds
            if (it.tagLine.isEmpty())
                movieTagTextView.visibility = View.GONE
            releaseDateTextView.text = it.releaseDate
        }

    }

    private fun setupFavView(id: Int) {
        viewModel.isMovieExit(id)
        viewModel.isMovieExistData.observe(this) { it ->
            it.data?.let { isExit ->
                if (isExit > 0) {
                    favImageView.setBackgroundResource(R.drawable.ic_fav_fill)
                } else {
                    favImageView.setBackgroundResource(R.drawable.ic_fav_border)
                }
            }

        }

    }
}