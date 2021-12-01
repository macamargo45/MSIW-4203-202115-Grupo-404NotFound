package com.example.vinilos.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.CollectorDetailsReviewItemBinding
import com.example.vinilos.models.Collector

class CollectorCommentsAdapter: RecyclerView.Adapter<CollectorCommentsAdapter.CollectorReviewViewHolder>() {
    class CollectorReviewViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val flowerTextView: TextView = itemView.findViewById(R.id.flower_text)
        private val flowerImageView: ImageView = itemView.findViewById(R.id.flower_image)
        private var review: Flower? = null

        fun bind(flower: Flower) {
            currentFlower = flower

            flowerTextView.text = flower.name
            if (flower.image != null) {
                flowerImageView.setImageResource(flower.image)
            } else {
                flowerImageView.setImageResource(R.drawable.rose)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorReviewViewHolder {
        val withDataBinding: CollectorDetailsReviewItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorReviewViewHolder.LAYOUT,
            parent,
            false)

        return CollectorReviewViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorListViewHolder, position: Int) {
        val collector = collectors[position]

        holder.viewDataBinding.also {
            it.collector = collector
        }
        holder.viewDataBinding.root.setOnClickListener {
            try {
                val action = CollectorsListFragmentDirections.actionCollectorsListFragmentToCollectorDetailsFragment(collector)
                holder.viewDataBinding.root.findNavController().navigate(action)
            }
            catch(e: Exception) {
                Log.println(Log.ERROR,"Error",e.message.toString())
            }
        }
    }

    override fun getItemCount(): Int {
        return collectors.size
    }
}
