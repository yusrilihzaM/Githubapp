package com.yusril.consumerapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Settings(
    val title:String,
    val ic:Int
):Parcelable
