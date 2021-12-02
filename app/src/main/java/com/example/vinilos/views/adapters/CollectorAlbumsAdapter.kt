package com.example.vinilos.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.CollectorDetailsAlbumItemBinding
import com.example.vinilos.models.Album
import com.squareup.picasso.Picasso

class CollectorAlbumsAdapter: RecyclerView.Adapter<CollectorAlbumsAdapter.CollectorAlbumViewHolder>() {
    class CollectorAlbumViewHolder(val viewDataBinding: CollectorDetailsAlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_details_album_item
        }
    }
    var albums :List<Album> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorAlbumViewHolder {
        val withDataBinding: CollectorDetailsAlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorAlbumViewHolder.LAYOUT,
            parent,
            false)

        return CollectorAlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorAlbumViewHolder, position: Int) {
        val album = albums[position]

        Picasso
            .get()
            .load(album.cover)
            .resize(100, 100)
            .into(holder.viewDataBinding.imageView)

        holder.viewDataBinding.also {
            it.album = album
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }
}