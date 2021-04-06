package com.yusril.consumerapp.activity.favorite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.like.LikeButton
import com.like.OnLikeListener
import com.yusril.consumerapp.R
import com.yusril.consumerapp.databinding.ActivityDetailFavoriteBinding
import com.yusril.consumerapp.db.DatabaseContract
import com.yusril.consumerapp.db.DatabaseContract.CONTENT_URI
import com.yusril.consumerapp.model.UserFavorite
import com.yusril.consumerapp.viewModel.UserDetailMainModel

class DetailFavoriteActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
        @StringRes
        private  val TAB_TITLES= intArrayOf(
            R.string.follower,
            R.string.following
        )
    }
    private lateinit var binding: ActivityDetailFavoriteBinding
    private lateinit var userDetailMainModel: UserDetailMainModel
    private var shareData:String?=null
    private lateinit var uriWithId: Uri
    var name :String?=""
    var username :String?=""
    private var userAvatar :String?=""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_favorite)
        binding = ActivityDetailFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDetailMainModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UserDetailMainModel::class.java
        )

        val user=intent.getParcelableExtra<UserFavorite>(EXTRA_USER) as UserFavorite
        Log.d(this@DetailFavoriteActivity.toString(), user.toString())
        username=user.username
        userDetailMainModel.setUserDetail(username.toString())

        supportActionBar?.title= "@$username"
        shareData=user.username
        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + user.username)
        Log.d("uriWithId", uriWithId.toString())
        //bg detail// revisi dari reviewer
        Glide.with(this)
            .load(R.drawable.bg_detail)
            .into(binding.imgBgDetail)
        userAvatar=user.avatar
        Glide.with(this)
            .load(userAvatar)
            .apply(RequestOptions().override(155, 155))
            .into(binding.imgPhoto)

        userDetailMainModel.getUserDetail().observe(this, { useritem ->
            if (useritem != null) {
                val data = useritem[0]
                name = data.name
                var follower = data.follower
                var following = data.following
                var location = data.location
                var company = data.company
                //jika data kosong maka ganti -
                if (name == "null") {
                    name = "-"
                }
                if (follower == "null") {
                    follower = "-"
                }
                if (following == "null") {
                    following = "-"
                }
                if (location == "null") {
                    location = "-"
                }
                if (company == "null") {
                    company = "-"
                }
                binding.tvName.text = name
                binding.tvFollower.text = follower
                binding.tvFollowing.text = following
                binding.tvLocation.text = location
                binding.tvCompany.text = company
            }
        })

        // viewpager
        val sectionsPagerAdapter= SectionsPagerAdapter(this)
        sectionsPagerAdapter.username=user.username
        val viewPager=binding.viewPager
        viewPager.adapter=sectionsPagerAdapter//set adapter view pager

        // tablayout
        val tabs=binding.tabs
        // masukan tab dan viewpager
        TabLayoutMediator(tabs, viewPager){ tab, position->
            tab.text=resources.getString(TAB_TITLES[position])
        }.attach()//sisipkan

        // Favorite

        val values= ContentValues()
        values.put(DatabaseContract.FavoriteColumns.USERNAME,username)
        values.put(DatabaseContract.FavoriteColumns.URL_IMAGE,userAvatar)

        binding.btnLove.isLiked = true
        binding.btnLove.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {
                contentResolver.insert(CONTENT_URI, values)
                Toast.makeText(this@DetailFavoriteActivity, getString(R.string.fav_added), Toast.LENGTH_SHORT).show()
            }
            override fun unLiked(likeButton: LikeButton) {
                contentResolver.delete(uriWithId, null, null)
                Toast.makeText(this@DetailFavoriteActivity, getString(R.string.fav_deleted), Toast.LENGTH_SHORT).show()

            }
        })

    }

}