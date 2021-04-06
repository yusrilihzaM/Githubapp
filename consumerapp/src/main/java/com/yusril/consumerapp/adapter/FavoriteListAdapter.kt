package com.yusril.consumerapp.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yusril.consumerapp.R
import com.yusril.consumerapp.databinding.ItemUserFavoriteBinding
import com.yusril.consumerapp.databinding.ItemUserMainBinding
import com.yusril.consumerapp.model.User
import com.yusril.consumerapp.model.UserFavorite

class FavoriteListAdapter(private val activity: Activity): RecyclerView.Adapter<FavoriteListAdapter.FavorieViewHolder>() {
    var listFavorites = ArrayList<UserFavorite>()
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listFavorites.clear()
            }
            this.listFavorites.addAll(listNotes)
            notifyDataSetChanged()
        }
    fun addItem(userFavorite: UserFavorite) {
        this.listFavorites.add(userFavorite)
        notifyItemInserted(this.listFavorites.size - 1)
    }
    fun updateItem(position: Int, userFavorite: UserFavorite) {
        this.listFavorites[position] = userFavorite
        notifyItemChanged(position, userFavorite)
    }
    fun removeItem(position: Int) {
        this.listFavorites.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listFavorites.size)
    }
    inner class FavorieViewHolder(private val binding: ItemUserFavoriteBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(userFavorite: UserFavorite){
            binding.tvUsername.text=userFavorite.username
            Glide.with(itemView.context)
                .load(userFavorite.avatar)
                .apply(RequestOptions().override(155,155))
                .into(binding.imgPhoto)
            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(userFavorite) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavorieViewHolder {
        val binding=ItemUserFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavorieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavorieViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }

    override fun getItemCount(): Int {
        return listFavorites.size
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: UserFavorite)
    }
    private var onItemClickCallback: FavoriteListAdapter.OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: FavoriteListAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}