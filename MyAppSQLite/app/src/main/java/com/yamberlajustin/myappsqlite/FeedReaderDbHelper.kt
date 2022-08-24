package com.yamberlajustin.myappsqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class FeedReaderDbHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    object FeedEntry : BaseColumns {
        const val TABLE_NAME = "entry"
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_SUBTITLE = "subtitle"
    }
    companion object{
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FeedReader.db"
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${FeedEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${FeedEntry.COLUMN_NAME_TITLE} TEXT," +
                    "${FeedEntry.COLUMN_NAME_SUBTITLE} TEXT)"
        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${FeedEntry.TABLE_NAME}"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    fun insertFeed(feed : Feed) : Long?{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(FeedEntry.COLUMN_NAME_TITLE, feed.title)
            put(FeedEntry.COLUMN_NAME_SUBTITLE, feed.subtitle)
        }
        var newRowId = db?.insert(FeedEntry.TABLE_NAME, null, values)
        db.close()
        return newRowId
    }
    fun readAllFeeds() : List<Feed>{
        val db = this.readableDatabase
        val projection = arrayOf(BaseColumns._ID, FeedEntry.COLUMN_NAME_TITLE, FeedEntry.COLUMN_NAME_SUBTITLE)
        val sortOrder = "${FeedEntry.COLUMN_NAME_SUBTITLE} DESC"
        val cursor = db.query(
            FeedEntry.TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,           // The columns for the WHERE clause
            null,        // The values for the WHERE clause
            null,           // don't group the rows
            null,            // don't filter by row groups
            sortOrder              // The sort order
        )
        val items = mutableListOf<Feed>()
        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val itemTitle = getString(getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TITLE))
                val itemSubTitle = getString(getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_SUBTITLE))
                items.add(Feed(itemTitle,itemSubTitle))
            }
        }
        cursor.close()
        db.close()
        return items
    }
    fun updateFeed(feed: Feed) : Int{
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(FeedEntry.COLUMN_NAME_SUBTITLE, feed.subtitle)
        }
        val selection = "${FeedEntry.COLUMN_NAME_TITLE} = ?"
        val selectionArgs = arrayOf(feed.title) //"MyOldTitle")
        val count = db.update(
            FeedEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs)
        db.close()
        return count
    }
    fun deleteAllFeeds(){
        val db = this.writableDatabase
        val deletedRows = db.delete(FeedEntry.TABLE_NAME, null, null)
    }
}