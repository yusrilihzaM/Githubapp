package com.yusril.submission2_a3322966.activity.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yusril.submission2_a3322966.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.title = resources.getString(R.string.settings)
        supportFragmentManager.beginTransaction().add(R.id.setting_holder, SettingsPreferenceFragment()).commit()

    }

}