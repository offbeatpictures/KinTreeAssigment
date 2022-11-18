package com.movies.kintreeassignment.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.movies.kintreeassignment.R
import com.movies.kintreeassignment.databinding.FragmentFirstBinding
import com.movies.kintreeassignment.services.model.MoviesModel
import com.movies.kintreeassignment.view.adapters.MoviesListAdpater
import com.movies.kintreeassignment.viewmodel.MoviesViewModel


class MoviesSearchFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var appCompatActivity: AppCompatActivity

    private lateinit var moviesViewModel: MoviesViewModel
    private val moviesList = ArrayList<MoviesModel>()

    private lateinit var moviesListAdpater: MoviesListAdpater

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appCompatActivity = context as AppCompatActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdpater()

        binding.searchEt.doOnTextChanged { text, start, before, count ->
            if (count > 0) binding.clearBtn.visibility =
                View.VISIBLE else binding.clearBtn.visibility = View.GONE
        }


        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        binding.searchEt.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                return@OnEditorActionListener true
            }
            false
        })

        binding.clearBtn.setOnClickListener(View.OnClickListener {
            binding.searchEt.text.clear()
        })

        moviesViewModel.moviesList.observe(appCompatActivity, Observer {
            if (it.isSuccessful && it.body()?.totalResults != null) {
                moviesList.clear()
                moviesListAdpater.notifyDataSetChanged()
                moviesList.addAll(it.body()?.search!!)
                setLoaderVisiblity(false)
                moviesListAdpater.notifyDataSetChanged()

            } else {
                moviesList.clear()
                setLoaderVisiblity(false)
                moviesListAdpater.notifyDataSetChanged()
            }
        })


    }

    private fun setAdpater() {
        moviesListAdpater = MoviesListAdpater(appCompatActivity, moviesList,this)
        binding.moviesRv.apply {
            layoutManager = GridLayoutManager(appCompatActivity, 2)
            adapter = moviesListAdpater
        }

    }

     fun naviagteToMoviesInfoFrag(id:String) {
        val bundle=Bundle()
        bundle.putString("id",id)
        Navigation.findNavController(binding.root).navigate(R.id.action_FirstFragment_to_SecondFragment,bundle);
    }


    private fun setLoaderVisiblity(s: Boolean) {
        if (s) {
            binding.progressBar.visibility = View.VISIBLE
            binding.noResultTv.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            if (moviesList.size == 0) binding.noResultTv.visibility =
                View.VISIBLE else binding.noResultTv.visibility = View.GONE
        }
    }

    private fun performSearch() {
        binding.noResultTv.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        moviesViewModel.getMoviesList(binding.searchEt.text.trim().toString())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}