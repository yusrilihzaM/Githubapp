package com.yusril.submission2_a3322966.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yusril.submission2_a3322966.databinding.ItemUserFollowingBinding
import com.yusril.submission2_a3322966.model.UserFollowing

class FollowingListAdapter(private val listFollowing: ArrayList<UserFollowing>) : RecyclerView.Adapter<FollowingListAdapter.ListViewHolder>() {
    inner class ListViewHolder(private val binding: ItemUserFollowingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserFollowing){
            binding.tvUsernameFollowing.text=user.username
            Glide.with(itemView.context)
                .load(user.avatar)
                .apply(RequestOptions().override(155,155))
                .into(binding.imgPhotoFollowing)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding=ItemUserFollowingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollowing[position])
    }

    override fun getItemCount(): Int {
        return listFollowing.size
    }
}