package com.yusril.submission2_a3322966.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yusril.submission2_a3322966.R
import com.yusril.submission2_a3322966.databinding.ItemUserMainBinding
import com.yusril.submission2_a3322966.model.User
import de.hdodenhof.circleimageview.CircleImageView

class UserListAdapter(private val listUser: ArrayList<User>) : RecyclerView.Adapter<UserListAdapter.ListViewHolder>(){

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    fun setData(listUser: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(listUser)
        notifyDataSetChanged()
    }
    inner class ListViewHolder(private val binding: ItemUserMainBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User){
            binding.tvUsername.text=user.username
            Glide.with(itemView.context)
                .load(user.avatar)
                .apply(RequestOptions().override(155,155))
                .into(binding.imgPhoto)
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(user) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding=ItemUserMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

}