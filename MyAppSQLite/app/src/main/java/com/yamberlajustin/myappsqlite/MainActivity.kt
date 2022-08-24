package com.yamberlajustin.myappsqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //OperacionesSqLite()
        OperacionesRoomSqLite()
    }
    fun OperacionesRoomSqLite(){
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-users"
        ).allowMainThreadQueries().build()
        val userDao = db.userDao()
        userDao.deleteAll()
        userDao.insertAll(User(1,"Nombre1","Apellido1"))
        userDao.insertAll(User(2,"Nombre2","Apellido2"))
        userDao.insertAll(User(3,"Nombre3","Apellido3"))
        val users: List<User> = userDao.getAll()
        for (user in users){
            println("${user.firstName}-${user.lastName}")
        }
    }

    /*fun OperacionesSqLite(){
        FeedReaderDbHelper(this).deleteAllFeeds()
        FeedReaderDbHelper(this).insertFeed(Feed("My Title 1","My Subtitle 1"))
        FeedReaderDbHelper(this).insertFeed(Feed("My Title 2","My Subtitle 4"))
        FeedReaderDbHelper(this).insertFeed(Feed("My Title 3","My Subtitle 5"))
        FeedReaderDbHelper(this).insertFeed(Feed("My Title 4","My Subtitle 6"))
        FeedReaderDbHelper(this).updateFeed(Feed("My Title 1","My new Subtitle"))
        val feeds = FeedReaderDbHelper(this).readAllFeeds()
        for (feed in feeds){
            println("${feed.title}-${feed.subtitle}")
        }
    }*/
}