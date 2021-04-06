package com.yusril.consumerapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail(
    var name:String,
    var company:String,
    var location:String,
    var public_repos:String,
    var follower:String,
    var following:String
):Parcelable
