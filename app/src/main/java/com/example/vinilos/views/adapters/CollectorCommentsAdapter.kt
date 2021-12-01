package com.example.vinilos.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.databinding.CollectorDetailsCommentItemBinding
import com.example.vinilos.models.Comment

class CollectorCommentsAdapter: RecyclerView.Adapter<CollectorCommentsAdapter.CollectorCommentViewHolder>() {
    class CollectorCommentViewHolder(val viewDataBinding: CollectorDetailsCommentItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_details_comment_item
        }
    }
    var comments :List<Comment> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorCommentViewHolder {
        val withDataBinding: CollectorDetailsCommentItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CollectorCommentViewHolder.LAYOUT,
            parent,
            false)

        return CollectorCommentViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: CollectorCommentViewHolder, position: Int) {
        val comment = comments[position]

        holder.viewDataBinding.also {
            it.comment = comment
        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }
}
