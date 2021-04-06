package com.yusril.submission2_a3322966.activity

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.provider.Settings.ACTION_LOCALE_SETTINGS
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yusril.submission2_a3322966.R
import com.yusril.submission2_a3322966.adapter.SettingsListAdapter
import com.yusril.submission2_a3322966.databinding.ActivitySettingsBinding
import com.yusril.submission2_a3322966.model.Settings

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    // menyimmpan data
    private lateinit var dataTitle: Array<String>
    private lateinit var dataIc: TypedArray
    private lateinit var settingsListAdapter: SettingsListAdapter
    private var settings = arrayListOf<Settings>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = resources.getString(R.string.settings)
        val listView=binding.lvSettings
        settingsListAdapter= SettingsListAdapter(this)
        listView.adapter=settingsListAdapter
        prepareData()
        addItem()

        listView.onItemClickListener=AdapterView.OnItemClickListener { _, _, position, _ ->
            Toast.makeText(this@SettingsActivity, settings[position].title, Toast.LENGTH_SHORT).show()
            when (position) {
                0 -> {
                    // inten untuk localization
                    startActivity(Intent(ACTION_LOCALE_SETTINGS))
                }
            }
        }
    }
    private fun prepareData() {
        //ambil data
        dataTitle = resources.getStringArray(R.array.data_settings)
        dataIc = resources.obtainTypedArray(R.array.data_ic_settings)
    }
    private fun addItem() {
        for (position in dataTitle.indices) {
            val set = Settings(
                dataTitle[position],
                dataIc.getResourceId(position, -1),
            )
            settings.add(set)
        }
        settingsListAdapter.settings = settings
    }
}