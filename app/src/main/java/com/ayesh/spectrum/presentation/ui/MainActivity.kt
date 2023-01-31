package com.ayesh.spectrum.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ayesh.spectrum.R
import com.ayesh.spectrum.extention.showDialogAlert
import com.ayesh.spectrum.presentation.vm.MovieViewModel
import com.ayesh.spectrum.utils.Resource
import com.xiteb.argo.presentation.state.DialogState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.genresCache.observe(this@MainActivity) {
            when (it) {
                is Resource.Success -> {
                    val i = Intent(this@MainActivity, DashboardActivity::class.java)
                    startActivity(i)
                    finish()
                }
                is Resource.Error -> {
                    showDialogAlert(it.message, type = DialogState.Error)
                }
                else -> {

                }
            }
        }
    }
}