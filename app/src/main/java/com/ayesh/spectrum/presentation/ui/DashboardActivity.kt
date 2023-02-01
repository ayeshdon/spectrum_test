package com.ayesh.spectrum.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ayesh.spectrum.R
import com.ayesh.spectrum.presentation.fragment.FavMoviesFragment
import com.ayesh.spectrum.presentation.fragment.NowPlayingFragment
import com.ayesh.spectrum.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_dashboard.*


@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        initUis()
        setCurrentFragment(NowPlayingFragment.newInstance(Constants.URL_NOW_PLAYING))
        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.home_nav -> setCurrentFragment(NowPlayingFragment.newInstance(Constants.URL_NOW_PLAYING))
                R.id.popular_nav -> setCurrentFragment(NowPlayingFragment.newInstance(Constants.URL_POPULAR))
                R.id.top_rated_nav -> setCurrentFragment(NowPlayingFragment.newInstance(Constants.URL_TOP_RATED))
                R.id.upcoming_nav -> setCurrentFragment(NowPlayingFragment.newInstance(Constants.URL_UPCOMING))
                R.id.favourites_nav -> setCurrentFragment(FavMoviesFragment.newInstance())
            }
            true
        }


    }

    private fun initUis() {
        searchImageView.setOnClickListener {
            searchMovies()
        }

        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchMovies()
                true
            } else {
                false
            }
        }
    }

    private fun searchMovies() {
        var searchQuery = searchEditText.text.toString()
        if (searchQuery.trim().isEmpty()) {
            Toast.makeText(this, "Please enter text to search", Toast.LENGTH_SHORT).show()
        } else {
            startActivity(Intent(this@DashboardActivity, SearchResultActivity::class.java).apply {
                putExtra("query", searchQuery)
            })
        }

    }

    private fun setCurrentFragment(frg: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, frg)
            commit()
        }
    }
}