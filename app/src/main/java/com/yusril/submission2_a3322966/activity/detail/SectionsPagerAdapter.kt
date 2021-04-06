package com.yusril.submission2_a3322966.activity.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }
    var username:String?=null
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            // kirim data ke Fragment melalui new instance sesuai video intruksi dicoding
            0 -> fragment = FollowerFragment.newInstance(username)
            1 -> fragment = FollowingFragment.newInstance(username)
        }
        return fragment as Fragment
    }
}