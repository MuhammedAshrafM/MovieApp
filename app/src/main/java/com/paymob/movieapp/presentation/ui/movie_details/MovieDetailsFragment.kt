package com.paymob.movieapp.presentation.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.paymob.movieapp.BuildConfig
import com.paymob.movieapp.R
import com.paymob.movieapp.databinding.FragmentMovieDetailsBinding
import com.paymob.movieapp.presentation.base.BaseFragment
import com.paymob.movieapp.presentation.ui.home.mvi.MoviesIntent
import com.paymob.movieapp.presentation.ui.home.mvi.MoviesViewModel
import com.paymob.movieapp.presentation.utils.bindImage
import com.paymob.movieapp.presentation.utils.bindResourceImage
import com.paymob.movieapp.presentation.utils.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val movie by lazy {
        args.movie
    }

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUi()
        observeViewState()

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivMovieBookmark.setOnClickListener {
            movie?.let {
                viewModel.setViewIntent(
                    MoviesIntent.ChangeMovieFavorite(
                        movie = it
                    )
                )

                // Change the value to update UI
                it.apply {
                    isBookMarked = !isBookMarked
                }
                updateMovieBookmarkUi(it.isBookMarked)
            }

        }
    }

    private fun updateUi() {
        movie?.run {
            binding.tvMovieName.text = title
            binding.tvMovieRating.text = "$voteAverage/10 TMDb"
            binding.tvMovieReleaseDate.text = releaseDate
            binding.tvLanguageVal.text = originalLanguage
            binding.tvRatingAvgVal.text = "$voteAverage"
            binding.tvOverviewVal.text = overview
            bindImage(
                imageView = binding.ivMoviePoster,
                imgUrl = posterFullPath,
                placeHolderIsAppIcon = false
            )
            updateMovieBookmarkUi(isBookMarked)
        }
    }

    private fun updateMovieBookmarkUi(isBookMarked: Boolean){
        bindResourceImage(
            binding.ivMovieBookmark,
            if(isBookMarked) R.drawable.baseline_bookmark_24 else R.drawable.baseline_bookmark_border_24
        )
    }

    private fun observeViewState() {
        lifecycleScope(this){
            viewModel.viewState.collect {
                it.run {
                    onLoading(isLoading)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}