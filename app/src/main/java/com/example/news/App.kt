package com.example.news

import android.app.Application
import androidx.room.Room
import com.example.news.room.AppDatabase

class App : Application() {
    companion object {

        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }

}