package com.example.news.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.news.models.News

@Database(entities = [News::class], version = 1)
abstract  class AppDatabase :RoomDatabase(){
    abstract fun newsDao():NewsDao
}