package com.paymob.movieapp.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.paymob.movieapp.R
import com.paymob.movieapp.data.features.movies.models.ListViewType
import com.paymob.movieapp.data.features.movies.models.Movie
import com.paymob.movieapp.data.network.error.ErrorEntity
import com.paymob.movieapp.databinding.FragmentHomeBinding
import com.paymob.movieapp.presentation.base.BaseFragment
import com.paymob.movieapp.presentation.ui.home.mvi.MoviesIntent
import com.paymob.movieapp.presentation.ui.home.mvi.MoviesViewModel
import com.paymob.movieapp.presentation.utils.bindResourceImage
import com.paymob.movieapp.presentation.utils.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoviesViewModel by viewModels()

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMoviesAdapter()
        setupReleaseDatesAdapter()

        observeViewState()

        viewModel.setViewIntent(MoviesIntent.GetMovies)

        binding.ivListViewType.setOnClickListener {
            viewModel.setViewIntent(MoviesIntent.ChangeListViewType)
        }
    }

    private fun setupMoviesAdapter() {
        moviesAdapter = MoviesAdapter(
            onMovieClick = {
                findNavController().navigate(
                    HomeFragmentDirections.actionNavigationHomeToFragmentMovieDetails(
                        movie = it
                    )
                )
            },
            onFavouriteClick = {
                viewModel.setViewIntent(
                    MoviesIntent.ChangeMovieFavorite(
                        movie = it
                    )
                )
            }
        )

        binding.rvMovies.adapter = moviesAdapter
    }
    private fun setupReleaseDatesAdapter() {
        val releaseDates = resources.getStringArray(R.array.release_dates).toList()
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.release_dates,
            R.layout.simple_spinner_item
        ).also { adapter ->
            binding.spinnerDates.adapter = adapter
        }

//        binding.spinnerDates.setSelection(0)

        binding.spinnerDates.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setViewIntent(
                    MoviesIntent.ChangeReleaseYear(
                        releaseYear = releaseDates.getOrNull(position)
                    )
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun observeViewState() {
        lifecycleScope(this){
            viewModel.viewState.collect {
                it.run {
                    onLoading(isLoading)
                    handleResponseError(error)
                    observeMoviesList(movies)
                    observeListViewType(listViewType)
                    observeReleaseYear(releaseYear)
                }
            }
        }
    }

    private fun observeReleaseYear(releaseYear: String) {
        binding.tvPopular.text = getString(R.string.popular, releaseYear)
    }

    private fun observeListViewType(listViewType: ListViewType) {
        moviesAdapter.changeViewType(listViewType)
        val (mLayoutManager, resourceId) = when(listViewType){
            ListViewType.LINEAR -> LinearLayoutManager(requireContext()) to R.drawable.baseline_grid_view_24
            ListViewType.GRID -> GridLayoutManager(requireContext(), 2) to R.drawable.baseline_format_list_bulleted_24
        }
        binding.rvMovies.layoutManager = mLayoutManager
        bindResourceImage(
            binding.ivListViewType,
            resourceId
        )
    }

    private fun observeMoviesList(movies: List<Movie>) {
        moviesAdapter.submitList(movies)
    }

    private fun handleResponseError(errorEntity: ErrorEntity?) {
        errorEntity?.let {
            val errorMessage = handleError(errorEntity).let {
                viewModel.setViewIntent(MoviesIntent.UserMessageShown)
                it
            }
            displayErrorMessage(errorMessage)
        }
    }

    private fun displayErrorMessage(errorMessage: String?) {
        errorMessage?.let {
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}