package com.yusril.submission2_a3322966.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusril.submission2_a3322966.R
import com.yusril.submission2_a3322966.viewModel.MainViewModel
import com.yusril.submission2_a3322966.activity.detail.DetailUserActivity
import com.yusril.submission2_a3322966.activity.favorite.FavoriteActivity
import com.yusril.submission2_a3322966.adapter.UserListAdapter
import com.yusril.submission2_a3322966.databinding.ActivityMainBinding
import com.yusril.submission2_a3322966.model.User

class MainActivity : AppCompatActivity() {
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel= ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        binding.rvList.setHasFixedSize(true)
        binding.rvList.layoutManager= LinearLayoutManager(this)

        // ambil data dr api via viewmodel
        mainViewModel.setUser()
        showLoading(true)

        //ambil hasilnya terus tampilkan
        mainViewModel.getUser().observe(this,{userItems->
            userListAdapter= UserListAdapter(userItems)
            showLoading(false)
            binding.rvList.adapter=userListAdapter
            userListAdapter.setOnItemClickCallback(object :UserListAdapter.OnItemClickCallback{
                override fun onItemClicked(data: User) {
                    Toast.makeText(this@MainActivity, data.username, Toast.LENGTH_SHORT).show()
                    val intent=Intent(this@MainActivity, DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.EXTRA_USER,data)
                    startActivity(intent)
                }

            })
        })

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater=menuInflater
        inflater.inflate(R.menu.main_menu,menu)

        val searchManager=getSystemService(Context.SEARCH_SERVICE) as SearchManager
        //Untuk mengambil komponen searchview yang sebelumnya sudah di inflate, kita menggunakan fungsi berikut
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        //berguna untuk memberikan hint pada pengguna tentang query search apa yang telah dimasukkan.
        searchView.queryHint = resources.getString(R.string.search_hint)// ini hint
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            // gunaakan function ini ketika search seleasi atau ok
            //Metode onQueryTextSubmit() akan dipanggil saat Submit ditekan.
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                mainViewModel.searchUser(query)
                showLoading(true)
                return true
            }
            // gunakan function ini untuk merespon tiap perubahan huruf pada searchView
            //onQueryTextChange() akan dipanggil setiap kali user memasukkan atau mengubah atau selesai query yang ada pada inputan searchview.
            override fun onQueryTextChange(newText: String?): Boolean {
                mainViewModel.setUser()
                showLoading(true)
                return true
            }
        }
        )
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            R.id.favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                true
            }
            else -> true
        }
    }
}