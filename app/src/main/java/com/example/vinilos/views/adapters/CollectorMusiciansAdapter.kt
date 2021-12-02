package com.example.vinilos.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.CollectorDetailsMusicianItemBinding
import com.example.vinilos.models.Musician
import com.squareup.picasso.Picasso

class CollectorMusiciansAdapter: RecyclerView.Adapter<CollectorMusiciansAdapter.CollectorPerformerViewHolder>() {
    class CollectorPerformerViewHolder(val viewDataBinding: CollectorDetailsMusicianItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_details_musician_item
        }
    }
    var musicians :List<Musician> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorPerformerViewHolder {
        val withDataBinding: CollectorDetailsMusicianItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorPerformerViewHolder.LAYOUT,
            parent,
            false)

        return CollectorPerformerViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorPerformerViewHolder, position: Int) {
        val musician = musicians[position]

        Picasso
            .get()
            .load(musician.image)
            .resize(100, 100)
            .into(holder.viewDataBinding.imageView)

        holder.viewDataBinding.also {
            it.musician = musician
        }
    }

    override fun getItemCount(): Int {
        return musicians.size
    }
}