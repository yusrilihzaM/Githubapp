package com.yusril.submission2_a3322966.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide
import com.yusril.submission2_a3322966.R
import com.yusril.submission2_a3322966.databinding.ActivitySplashScreenBinding

class SplashScreen : Activity() {
    private lateinit var binding: ActivitySplashScreenBinding
    companion object{
        const val TIME:Long=3000
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({ moveActivity() },TIME)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this)
                .load(R.drawable.ic_github_)
                .into(binding.icGithub)
    }

    private fun moveActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}