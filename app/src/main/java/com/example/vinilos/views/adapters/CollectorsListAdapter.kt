package com.example.vinilos.views.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.CollectorListItemBinding
import com.example.vinilos.models.Collector
import com.example.vinilos.views.CollectorsListFragmentDirections

class CollectorsListAdapter : RecyclerView.Adapter<CollectorsListAdapter.CollectorListViewHolder>() {

    class CollectorListViewHolder(val viewDataBinding: CollectorListItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_list_item
        }
    }
    var collectors :List<Collector> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorListViewHolder {
        val withDataBinding: CollectorListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorListViewHolder.LAYOUT,
            parent,
            false)

        return CollectorListViewHolder(withDataBinding)
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