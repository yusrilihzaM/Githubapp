package com.yusril.submission2_a3322966.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yusril.submission2_a3322966.databinding.ItemUserFavoriteBinding
import com.yusril.submission2_a3322966.model.UserFavorite

class FavoriteListAdapter(private val activity: Activity): RecyclerView.Adapter<FavoriteListAdapter.FavorieViewHolder>() {
    var listFavorites = ArrayList<UserFavorite>()
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listFavorites.clear()
            }
            this.listFavorites.addAll(listNotes)
            notifyDataSetChanged()
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