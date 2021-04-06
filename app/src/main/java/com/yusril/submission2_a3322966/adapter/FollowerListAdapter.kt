package com.yusril.submission2_a3322966.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yusril.submission2_a3322966.databinding.ItemUserFollowerBinding
import com.yusril.submission2_a3322966.model.UserFollower

class FollowerListAdapter(private val listFollower: ArrayList<UserFollower>) : RecyclerView.Adapter<FollowerListAdapter.ListViewHolder>() {
    inner class ListViewHolder(private val binding: ItemUserFollowerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserFollower){
            binding.tvUsernameFollower.text=user.username
            Glide.with(itemView.context)
                .load(user.avatar)
                .apply(RequestOptions().override(155,155))
                .into(binding.imgPhotoFollower)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding=ItemUserFollowerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listFollower[position])
    }

    override fun getItemCount(): Int {
        return listFollower.size
    }
}