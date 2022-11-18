package com.movies.kintreeassignment.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movies.kintreeassignment.R
import com.movies.kintreeassignment.databinding.MoviesCardLayoutBinding
import com.movies.kintreeassignment.services.model.MoviesModel
import com.movies.kintreeassignment.view.fragments.MoviesSearchFragment

class MoviesListAdpater(
    private val appCompatActivity: AppCompatActivity,
    private val moviesList: ArrayList<MoviesModel>,
    private val moviesSearchFragment: MoviesSearchFragment
) : RecyclerView.Adapter<MoviesListAdpater.MoviesViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesListAdpater.MoviesViewHolder {
        val view = LayoutInflater.from(appCompatActivity)
            .inflate(R.layout.movies_card_layout, parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesListAdpater.MoviesViewHolder, position: Int) {
        val moviesModel = moviesList.get(position)

        holder.binding.movieTitleTv.text = moviesModel.title
        holder.binding.releaseYearTv.text = "Release Year: ${moviesModel.year}"
        Glide.with(appCompatActivity).load(moviesModel.poster).into(holder.binding.moviePosterIv)


    }

    override fun getItemCount() = moviesList.size

    inner class MoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: MoviesCardLayoutBinding = MoviesCardLayoutBinding.bind(view)

        init {
            view.setOnClickListener(View.OnClickListener {
                val moviesModel = moviesList.get(layoutPosition)
                moviesSearchFragment.naviagteToMoviesInfoFrag(moviesModel.imdbID)
            })
        }
    }
}