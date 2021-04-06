package com.yusril.consumerapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserFavorite(
        var id: Int = 0,
        var username: String?=null,
        var avatar: String?=null,
):Parcelable
