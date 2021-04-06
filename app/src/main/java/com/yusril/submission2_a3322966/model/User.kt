package com.yusril.submission2_a3322966.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var username: String,
    var avatar: String,
):Parcelable
