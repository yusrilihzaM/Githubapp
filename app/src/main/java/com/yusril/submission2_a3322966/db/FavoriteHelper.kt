package com.yusril.submission2_a3322966.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.yusril.submission2_a3322966.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.yusril.submission2_a3322966.db.DatabaseContract.FavoriteColumns.Companion._ID
import com.yusril.submission2_a3322966.db.DatabaseContract.FavoriteColumns.Companion.USERNAME
import java.sql.SQLException

class FavoriteHelper(context: Context) {
    companion object{
        private const val DATABASE_TABLE=TABLE_NAME
        private lateinit var dataBaseHelper: DatabaseHelper

        private lateinit var database: SQLiteDatabase
        private var INSTANCE: FavoriteHelper? = null
        fun getInstance(context: Context): FavoriteHelper =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: FavoriteHelper(context)
                }
    }

    init {
        dataBaseHelper= DatabaseHelper(context)
    }
    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }
    fun close() {
        dataBaseHelper.close()
        if (database.isOpen)
            database.close()
    }
    fun getAll(): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                "$_ID ASC"
        )
    }
    fun getById(username: String): Cursor {
        return database.query(
                DATABASE_TABLE,
                null,
                "$USERNAME = ?", arrayOf(username),
                null,
                null,
                null,
                null)
    }
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }
    fun deleteByUsername(username: String): Int {
        return database.delete(DATABASE_TABLE, "$USERNAME = '$username'", null)
    }
}