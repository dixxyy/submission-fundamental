package com.example.dicodingapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingapp.data.local.entity.FavoriteEntity
import com.example.dicodingapp.databinding.ItemEventBinding


class FavoriteAdapter(
    private val onItemClick: (FavoriteEntity) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private var favoriteList = listOf<FavoriteEntity>()

    fun setFavorites(favorites: List<FavoriteEntity>) {
        this.favoriteList = favorites
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: FavoriteEntity) {
            binding.apply {
                // Set title
                eventTitle.text = favorite.title

                // Load image using Glide
                Glide.with(itemView.context)
                    .load(favorite.imageUrl) // Replace with the correct property name
                    .into(eventImage)

                // Handle item click
                root.setOnClickListener {
                    onItemClick(favorite)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int = favoriteList.size
}