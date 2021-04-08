package com.yusril.submission2_a3322966.activity.detail

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayoutMediator
import com.like.LikeButton
import com.like.OnLikeListener
import com.yusril.submission2_a3322966.R
import com.yusril.submission2_a3322966.databinding.ActivityDetailUserBinding
import com.yusril.submission2_a3322966.db.DatabaseContract
import com.yusril.submission2_a3322966.db.DatabaseContract.CONTENT_URI
import com.yusril.submission2_a3322966.db.FavoriteHelper
import com.yusril.submission2_a3322966.db.MappingHelper
import com.yusril.submission2_a3322966.model.User
import com.yusril.submission2_a3322966.viewModel.UserDetailMainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailUserActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
        @StringRes
        private  val TAB_TITLES= intArrayOf(
            R.string.follower,
            R.string.following
        )
    }
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var userDetailMainModel: UserDetailMainModel
    private var shareData:String?=null
    private lateinit var uriWithId: Uri
    var name :String?=""
    var username :String?=""
    private var userAvatar :String?=""
    private lateinit var favoriteHelper: FavoriteHelper
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDetailMainModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            UserDetailMainModel::class.java
        )

        val user=intent.getParcelableExtra<User>(EXTRA_USER) as User
        Log.d(this@DetailUserActivity.toString(), user.toString())
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
        val sectionsPagerAdapter=SectionsPagerAdapter(this)
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
        favoriteHelper= FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()
        val values= ContentValues()
        values.put(DatabaseContract.FavoriteColumns.USERNAME,username)
        values.put(DatabaseContract.FavoriteColumns.URL_IMAGE,userAvatar)

        checkdata()// cek data apakah ada di db atau tidak
        binding.btnLove.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton) {
                contentResolver.insert(CONTENT_URI, values)
                Toast.makeText(this@DetailUserActivity, getString(R.string.fav_added), Toast.LENGTH_SHORT).show()
            }
            override fun unLiked(likeButton: LikeButton) {
//                favoriteHelper.deleteByUsername(username.toString())
                contentResolver.delete(uriWithId, null, null)
                Toast.makeText(this@DetailUserActivity, getString(R.string.fav_deleted), Toast.LENGTH_SHORT).show()

            }
        })
//
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent= Intent()
        intent.action= Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, shareData)
        intent.type="text/plain"
        startActivity(Intent.createChooser(intent, R.string.share.toString()))
        return super.onOptionsItemSelected(item)
    }
    private fun checkdata() {
        GlobalScope.launch(Dispatchers.Main) {
            val favoriteHelper= FavoriteHelper.getInstance(applicationContext)
            favoriteHelper.open()
            val defferedFavorite=async(Dispatchers.IO) {
                val cursor=favoriteHelper.getById(username.toString())
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val check=defferedFavorite.await()
            Log.d("data check", check.toString())
            if (check.size>0){
                binding.btnLove.isLiked = true
                Log.d("check>0", check.size.toString())
            }else{
                binding.btnLove.isLiked = false
                Log.d("check<0", check.size.toString())
            }
        }
    }
}