package com.example.vinilos.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.AlbumItemBinding
import com.example.vinilos.models.Album
import com.example.vinilos.views.AlbumFragmentDirections
import com.squareup.picasso.Picasso


class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    class AlbumViewHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
    }
    var albums :List<Album> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            AlbumViewHolder.LAYOUT,
            parent,
            false)

        return AlbumViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]

        Picasso
            .get()
            .load(album.cover)
            .resize(100, 100)
            .into(holder.viewDataBinding.imageView)
        
        holder.viewDataBinding.also {
            it.album = album
        }
        holder.viewDataBinding.root.setOnClickListener {
            try {
                val action = AlbumFragmentDirections.actionAlbumFragmentToAlbumDetailsFragment(album)
                holder.viewDataBinding.root.findNavController().navigate(action)
            }
            catch(e: Exception) {
               Log.println(Log.ERROR,"Error",e.message.toString())
            }
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }
}
