package com.yusril.submission2_a3322966.activity.favorite

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusril.submission2_a3322966.R
import com.yusril.submission2_a3322966.adapter.FavoriteListAdapter
import com.yusril.submission2_a3322966.databinding.ActivityFavoriteBinding
import com.yusril.submission2_a3322966.db.DatabaseContract
import com.yusril.submission2_a3322966.db.MappingHelper
import com.yusril.submission2_a3322966.model.UserFavorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteListAdapter: FavoriteListAdapter
    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title= getString(R.string.favorites)
        binding.rvListFav.layoutManager= LinearLayoutManager(this)
        binding.rvListFav.setHasFixedSize(true)
        favoriteListAdapter= FavoriteListAdapter(this)
        binding.rvListFav.adapter=favoriteListAdapter

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                loadNotesAsync()
            }
        }
        contentResolver.registerContentObserver(DatabaseContract.CONTENT_URI, true, myObserver)
        if (savedInstanceState == null) {
            // proses ambil data
            loadNotesAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<UserFavorite>(EXTRA_STATE)
            if (list != null) {
                favoriteListAdapter.listFavorites = list
            }
        }
    }

    private fun loadNotesAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBar.visibility= View.VISIBLE
            val defferedFavorite=async(Dispatchers.IO) {
                val cursor = contentResolver.query(DatabaseContract.CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            binding.progressBar.visibility= View.INVISIBLE
            val favorites=defferedFavorite.await()
            if (favorites.size>0){
                favoriteListAdapter.listFavorites=favorites
            }else{
                favoriteListAdapter.listFavorites= ArrayList()
                binding.tvMessage.visibility= View.VISIBLE
                binding.tvMessage.text=getString(R.string.fav_none)
                Toast.makeText(this@FavoriteActivity, getString(R.string.fav_none), Toast.LENGTH_SHORT).show()
            }
            favoriteListAdapter.setOnItemClickCallback(object : FavoriteListAdapter.OnItemClickCallback{
                override fun onItemClicked(data: UserFavorite) {
                    Toast.makeText(this@FavoriteActivity, data.username, Toast.LENGTH_SHORT).show()
                    val intent= Intent(this@FavoriteActivity, DetailFavoriteActivity::class.java)
                    intent.putExtra(DetailFavoriteActivity.EXTRA_USER,data)
                    startActivity(intent)
                }

            })
        }
    }
}