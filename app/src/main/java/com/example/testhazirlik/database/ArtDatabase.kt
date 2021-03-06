package com.example.testhazirlik.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.testhazirlik.model.Art

@Database(entities = [Art::class],version = 1)
abstract class ArtDatabase : RoomDatabase(){

    abstract fun artDao() : ArtDao


}