package com.yusril.submission2_a3322966.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.yusril.submission2_a3322966.R
import com.yusril.submission2_a3322966.model.Settings

class SettingsListAdapter internal constructor(private val context: Context) : BaseAdapter() {
    var settings = arrayListOf<Settings>()

    override fun getCount(): Int {
        return settings.size
    }

    override fun getItem(position: Int): Any {
        return settings[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView=convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_settings, parent, false)
        }
        val viewHolder = ViewHolder(itemView as View)
        val setting=getItem(position) as Settings
        viewHolder.bind(setting)
        return itemView
    }
    private inner class ViewHolder(view: View) {
        private val txtSetting: TextView = view.findViewById(R.id.tv_setting)
        private val imgIc: ImageView = view.findViewById(R.id.ic_setting)
        fun bind(settings: Settings) {
            txtSetting.text=settings.title
            imgIc.setImageResource(settings.ic)
        }
    }
}