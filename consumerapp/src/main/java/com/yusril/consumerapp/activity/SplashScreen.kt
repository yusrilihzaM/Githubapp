package com.yusril.consumerapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide
import com.yusril.consumerapp.R
import com.yusril.consumerapp.activity.favorite.FavoriteActivity
import com.yusril.consumerapp.databinding.ActivitySplashScreenBinding

class SplashScreen : Activity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({ moveActivity() },3000)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this)
                .load(R.drawable.ic_github_)
                .into(binding.icGithub)
    }

    private fun moveActivity() {
        startActivity(Intent(this, FavoriteActivity::class.java))
        finish()
    }
}