package com.ayesh.spectrum.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ayesh.spectrum.R
import com.ayesh.spectrum.presentation.fragment.NowPlayingFragment
import com.ayesh.spectrum.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_dashboard.*

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setCurrentFragment(NowPlayingFragment.newInstance(Constants.URL_NOW_PLAYING))
        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.home_nav -> setCurrentFragment(NowPlayingFragment.newInstance(Constants.URL_NOW_PLAYING))
                R.id.popular_nav -> setCurrentFragment(NowPlayingFragment.newInstance(Constants.URL_POPULAR))
                R.id.top_rated_nav -> setCurrentFragment(NowPlayingFragment.newInstance(Constants.URL_TOP_RATED))
                R.id.upcoming_nav -> setCurrentFragment(NowPlayingFragment.newInstance(Constants.URL_UPCOMING))
            }
            true
        }


    }

    private fun setCurrentFragment(frg: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, frg)
            commit()
        }
    }
}