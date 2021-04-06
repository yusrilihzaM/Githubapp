package com.yusril.submission2_a3322966.db

import android.database.Cursor
import com.yusril.submission2_a3322966.model.UserFavorite

object MappingHelper {

    fun mapCursorToArrayList(favoriteCursor: Cursor?): ArrayList<UserFavorite> {
        val favoritesList = ArrayList<UserFavorite>()
        favoriteCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
                val urlImage = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.URL_IMAGE))
                favoritesList.add(UserFavorite(id, username, urlImage))
            }
        }
        return favoritesList

    }
    fun mapCursorToObject(favoriteCursor: Cursor?): UserFavorite {
        var userFavorite = UserFavorite()
        favoriteCursor?.apply {
            moveToFirst()
            val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns._ID))
            val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
            val urlImage = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.URL_IMAGE))
            userFavorite = UserFavorite(id, username, urlImage)
        }
        return userFavorite
    }

}