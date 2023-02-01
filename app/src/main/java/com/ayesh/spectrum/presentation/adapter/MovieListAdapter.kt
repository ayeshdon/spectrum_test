package com.ayesh.spectrum.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayesh.spectrum.R
import com.ayesh.spectrum.domain.model.MovieModel
import com.ayesh.spectrum.utils.Constants
import com.bumptech.glide.Glide

class MovieListAdapter(
    private val context: Context
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private var movieList: MutableList<MovieModel> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_grid_item, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = movieList[position]
        Glide
            .with(context)
            .load(Constants.IMAGE_URL_PREFIX + item.poster)
            .placeholder(R.drawable.ic_baseline_movie_24)
            .into(holder.posterImageView)
        holder.releaseDateTextView.text = item.releaseDate
        holder.titleTextView.text = item.title
        holder.voteTextView.text = "${item.voteCount} Votes"
        holder.voteAvgTextView.text = item.voteAvg.toString()
        holder.movieItemRootView.tag = position
        holder.movieItemRootView.setOnClickListener {
            var pos: Int = it.tag as Int
            onItemClickListener?.let { it(movieList[pos]) }
        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    fun addAll(addList: List<MovieModel>) {
        movieList.addAll(addList)
        notifyDataSetChanged()

    }

    private var onItemClickListener: ((MovieModel) -> Unit)? = null

    fun setOnItemClickListener(listener: (MovieModel) -> Unit) {
        onItemClickListener = listener
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val voteAvgTextView: TextView = itemView.findViewById(R.id.voteAvgTextView)
        val voteTextView: TextView = itemView.findViewById(R.id.voteTextView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val releaseDateTextView: TextView = itemView.findViewById(R.id.releaseDateTextView)
        val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)
        val movieItemRootView: LinearLayout = itemView.findViewById(R.id.movieItemRootView)
    }
}