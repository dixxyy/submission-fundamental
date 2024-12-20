package com.example.dicodingapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingapp.data.remote.response.ListEventsItem
import com.example.dicodingapp.databinding.ItemEventBinding
import com.example.dicodingapp.ui.detail.DetailActivity

class EventAdapter(private val listener: OnItemClickListener):
    ListAdapter<ListEventsItem, EventAdapter.MyViewHolder>(DIFF_CALLBACK){

    interface OnItemClickListener {
        fun onItemClick(eventId : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
        holder.itemView.setOnClickListener {
            event.id?.let { id ->
                val context = holder.itemView.context
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.EXTRA_EVENT_ID, id.toString())
                }
                context.startActivity(intent)
            }
        }
    }
    class MyViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem){
            binding.eventTitle.text = event.name ?: "No Name"

            Glide.with(binding.root.context)
                .load(event.mediaCover)
                .into(binding.eventImage)
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}