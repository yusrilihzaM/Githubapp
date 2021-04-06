package com.yusril.submission2_a3322966.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserFavorite(
        var id: Int = 0,
        var username: String?=null,
        var avatar: String?=null,
):Parcelable
