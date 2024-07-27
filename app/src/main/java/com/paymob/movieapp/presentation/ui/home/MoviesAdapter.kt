package com.paymob.movieapp.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paymob.movieapp.BuildConfig.IMAGE_URL
import com.paymob.movieapp.R
import com.paymob.movieapp.data.features.movies.models.ListViewType
import com.paymob.movieapp.data.features.movies.models.Movie
import com.paymob.movieapp.databinding.ItemGridMovieBinding
import com.paymob.movieapp.databinding.ItemMovieBinding
import com.paymob.movieapp.presentation.utils.bindImage
import com.paymob.movieapp.presentation.utils.bindResourceImage

class MoviesAdapter(
    private val onMovieClick: (Movie) -> Unit,
    private val onFavouriteClick: (Movie) -> Unit
) : ListAdapter<Movie, RecyclerView.ViewHolder>(Companion) {

    private var listViewType: ListViewType = ListViewType.LINEAR

    companion object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(
            oldItem: Movie,
            newItem: Movie
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldOrder: Movie,
            newOrder: Movie
        ): Boolean {
            return oldOrder == newOrder
        }
    }

    override fun getItemViewType(position: Int): Int {
        return listViewType.viewType
    }

    fun changeViewType(viewType: ListViewType){
        this.listViewType = viewType
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        when(viewType){
            ListViewType.LINEAR.viewType -> {
                val binding =
                    ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MoviesLinearViewHolder(binding)
            }
            ListViewType.GRID.viewType -> {
                val binding =
                    ItemGridMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MoviesGridViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MoviesLinearViewHolder(binding)
            }
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = currentList[position]
        item?.let {
            when (holder) {
                is MoviesLinearViewHolder -> {
                    holder.bind(it, position)
                }
                is MoviesGridViewHolder -> {
                    holder.bind(it, position)
                }
                else -> {}
            }
        }
    }

    inner class MoviesLinearViewHolder(
        private val itemBinding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: Movie, position: Int) {
            with(item){
                itemBinding.tvMovieName.text = title
                itemBinding.movieRating.rating = rating
                itemBinding.tvMovieReleaseDate.text = releaseDate
                bindImage(
                    imageView = itemBinding.ivMoviePoster,
                    imgUrl = posterFullPath,
                    placeHolderIsAppIcon = false
                )
                bindResourceImage(
                    itemBinding.ivMovieBookmark,
                    if(isBookMarked) R.drawable.baseline_bookmark_24 else R.drawable.baseline_bookmark_border_24
                )
            }

            itemBinding.itemContainer.setOnClickListener {
                onMovieClick(item)
            }

            itemBinding.cardMovieBookmark.setOnClickListener {
                // Callback to update it in local db
                onFavouriteClick(item)

                // Change the value and notify to update UI
                item.apply {
                    isBookMarked = !isBookMarked
                }
                notifyItemChanged(position)
            }
        }
     }

    inner class MoviesGridViewHolder(
        private val itemBinding: ItemGridMovieBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: Movie, position: Int) {
            with(item){
                itemBinding.tvMovieName.text = title
                itemBinding.movieRating.rating = rating
                itemBinding.tvMovieReleaseDate.text = releaseDate
                bindImage(
                    imageView = itemBinding.ivMoviePoster,
                    imgUrl = posterFullPath,
                    placeHolderIsAppIcon = false
                )
                bindResourceImage(
                    itemBinding.ivMovieBookmark,
                    if(isBookMarked) R.drawable.baseline_bookmark_24 else R.drawable.baseline_bookmark_border_24
                )
            }

            itemBinding.itemContainer.setOnClickListener {
                onMovieClick(item)
            }

            itemBinding.cardMovieBookmark.setOnClickListener {
                // Callback to update it in local db
                onFavouriteClick(item)

                // Change the value and notify to update UI

                item.apply {
                    isBookMarked = !isBookMarked
                }
                notifyItemChanged(position)
            }
        }
     }

}