package com.paymob.movieapp.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paymob.movieapp.databinding.ItemGridMovieBinding
import com.paymob.movieapp.databinding.ItemMovieBinding

class MoviesAdapter(
    private val onMovieClick: (String) -> Unit,
    private val onFavouriteClick: (String) -> Unit
) : ListAdapter<String, RecyclerView.ViewHolder>(Companion) {

    private var viewType: Int = 1

    companion object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldOrder: String,
            newOrder: String
        ): Boolean {
            return oldOrder == newOrder
        }
    }

    override fun getItemViewType(position: Int): Int {
        return 1
    }

    fun changeViewType(viewType: Int){
        this.viewType = viewType
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder =
        when(viewType){
            1 -> {
                val binding =
                    ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MoviesLinearViewHolder(binding)
            }
            2 -> {
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
                    holder.bind(it)
                }
                is MoviesGridViewHolder -> {
                    holder.bind(it)
                }
                else -> {}
            }
        }
    }

    inner class MoviesLinearViewHolder(
        private val itemBinding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private val _context = itemBinding.root.context

        fun bind(item: String) {
            with(item){
            }

            itemBinding.itemContainer.setOnClickListener {
                onMovieClick(item)
            }

            itemBinding.cardMovieBookmark.setOnClickListener {
                onFavouriteClick(item)
            }
        }
     }

    inner class MoviesGridViewHolder(
        private val itemBinding: ItemGridMovieBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private val _context = itemBinding.root.context

        fun bind(item: String) {
            with(item){
            }

            itemBinding.itemContainer.setOnClickListener {
                onMovieClick(item)
            }

            itemBinding.cardMovieBookmark.setOnClickListener {
                onFavouriteClick(item)
            }
        }
     }

}