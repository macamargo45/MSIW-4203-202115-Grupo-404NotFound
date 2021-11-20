package com.example.vinilos.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.MusicianItemBinding
import com.example.vinilos.models.Musician
import com.example.vinilos.views.MusiciansListFragmentDirections
import com.squareup.picasso.Picasso

class MusiciansListAdapter : RecyclerView.Adapter<MusiciansListAdapter.MusicianListViewHolder>() {

    class MusicianListViewHolder(val viewDataBinding: MusicianItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.musician_item
        }
    }

    var musicians: List<Musician> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicianListViewHolder {
        val withDataBinding: MusicianItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MusicianListViewHolder.LAYOUT,
            parent,
            false
        )

        return MusicianListViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: MusicianListViewHolder, position: Int) {
        val musician = musicians[position]

        Picasso
            .get()
            .load(musician.image)
            .resize(100, 100)
            .into(holder.viewDataBinding.imageView)

        holder.viewDataBinding.also {
            it.musician = musician
        }
        holder.viewDataBinding.root.setOnClickListener {
            try {
                val action =
                    MusiciansListFragmentDirections.actionMusicianListFragmentToMusicianDetailsFragment(musicians[position])
                holder.viewDataBinding.root.findNavController().navigate(action)
            } catch (e: Exception) {
                Log.println(Log.ERROR, "Error", e.message.toString())
            }
        }
    }

    override fun getItemCount(): Int {
        return musicians.size
    }
}