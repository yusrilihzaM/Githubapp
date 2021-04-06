package com.yusril.consumerapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserFollower(
    var username: String,
    var avatar: String,
):Parcelable
