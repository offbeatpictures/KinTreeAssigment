package com.movies.kintreeassignment.view.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.movies.kintreeassignment.databinding.FragmentSecondBinding
import com.movies.kintreeassignment.services.model.MoviesModel
import com.movies.kintreeassignment.viewmodel.MoviesViewModel
import org.json.JSONObject


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class MovieInfoFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private lateinit var appCompatActivity: AppCompatActivity

    private lateinit var id: String

    private lateinit var moviesViewModel: MoviesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("id")?.let {
            id = it
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        appCompatActivity = context as AppCompatActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        getMovieInfoFromApi()

        moviesViewModel.moviesModel.observe(appCompatActivity, Observer {
            if (it.isSuccessful) {
                bindMovieDetails(it.body())
            }
        })
    }

    private fun bindMovieDetails(body: MoviesModel?) {
        Glide.with(appCompatActivity).load(body?.poster).into(binding.moviePosterIv)

        val jsonObject = JSONObject(Gson().toJson(body))
        val keys = jsonObject.keys()

        while (keys.hasNext()) {
            val key = keys.next()
            val value = jsonObject.get(key)
            if (value is String && !key.equals("Poster")) {
                addTextView("${key.toString()} : ${value}")
            }
        }

    }


    private fun addTextView(s: String) {
        val textView = TextView(appCompatActivity)
        textView.text = s
        textView.typeface= Typeface.DEFAULT_BOLD
        textView.setTextColor(Color.BLACK)


        val params: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        params.setMargins(20, 10, 20, 10)

        textView.layoutParams = params

        binding.movieInfoContainer.addView(textView)
    }

    private fun getMovieInfoFromApi() {
        moviesViewModel.getMovieInfo(id)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}