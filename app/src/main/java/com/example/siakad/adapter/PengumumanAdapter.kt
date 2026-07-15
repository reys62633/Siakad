package com.example.siakad.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.siakad.R
import com.example.siakad.model.Post

class PengumumanAdapter(
    private val onItemClick: (Post) -> Unit
) : ListAdapter<Post, PengumumanAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvItemTitle)
        val tvBody: TextView = itemView.findViewById(R.id.tvItemBody)
        val tvTag: TextView = itemView.findViewById(R.id.tvItemTag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pengumuman, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = getItem(position)
        // Capitalize first letter of title
        holder.tvTitle.text = post.title.replaceFirstChar { it.uppercase() }
        holder.tvBody.text = post.body
        holder.tvTag.text = "Pengumuman #${post.id}"
        holder.itemView.setOnClickListener { onItemClick(post) }
    }

    class DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
    }
}
