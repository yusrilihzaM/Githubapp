package com.yusril.consumerapp.db

import android.net.Uri
import android.provider.BaseColumns
import com.yusril.consumerapp.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME

object DatabaseContract {
    //content provider
    const val AUTHORITY = "com.yusril.submission2_a3322966"
    const val SCHEME = "content"
    internal class FavoriteColumns: BaseColumns {
        companion object{
            const val TABLE_NAME="favorite"
            const val _ID="_id"
            const val USERNAME="username"
            const val URL_IMAGE="url_image"
        }
    }
    val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath(TABLE_NAME)
        .build()
}