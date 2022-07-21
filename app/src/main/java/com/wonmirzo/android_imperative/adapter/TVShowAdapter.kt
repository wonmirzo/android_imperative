package com.wonmirzo.android_imperative.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wonmirzo.android_imperative.activity.MainActivity
import com.wonmirzo.android_imperative.databinding.ItemTvShowBinding
import com.wonmirzo.android_imperative.model.TVShow

class TVShowAdapter(var activity: MainActivity, private var items: ArrayList<TVShow>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemTvShowBinding.inflate(LayoutInflater.from(parent.context))
        return TVShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvShow = items[position]
        if (holder is TVShowViewHolder) {
            holder.onBind(tvShow)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setNewTVShows(tvShows: java.util.ArrayList<TVShow>) {
        items.clear()
        items.addAll(tvShows)
        notifyDataSetChanged()
    }

    inner class TVShowViewHolder(private var binding: ItemTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(tvShow: TVShow) {
            Glide.with(activity)
                .load(tvShow.image_thumbnail_path)
                .into(binding.ivMovie)

            binding.tvName.text = tvShow.name
            binding.tvType.text = tvShow.network

            // click the image
        }

    }
}