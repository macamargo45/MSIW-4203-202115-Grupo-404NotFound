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
import com.example.vinilos.views.MusicianDetailsFragmentDirections
import com.squareup.picasso.Picasso


class MusicianAlbumsAdapter : RecyclerView.Adapter<MusicianAlbumsAdapter.MusicianAlbumsHolder>() {

    class MusicianAlbumsHolder(val viewDataBinding: AlbumItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.album_item
        }
    }

    var musicianAlbums: List<Album> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicianAlbumsHolder {
        val withDataBinding: AlbumItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MusicianAlbumsHolder.LAYOUT,
            parent,
            false
        )

        return MusicianAlbumsHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: MusicianAlbumsHolder, position: Int) {
        val album = musicianAlbums[position]

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
                val action =
                    MusicianDetailsFragmentDirections.actionMusicianDetailFragmentToAlbumDetailsFragment(album)
                holder.viewDataBinding.root.findNavController().navigate(action)

            } catch (e: Exception) {
                Log.println(Log.ERROR, "Error", e.message.toString())
            }
        }
    }

    override fun getItemCount(): Int {
        return musicianAlbums.size
    }
}