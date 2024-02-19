package com.example.zayatspexelsapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.zayatspexelsapp.data.database.dao.FavouritesDao
import com.example.zayatspexelsapp.data.models.Photo

@Database(
    entities = [Photo::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val dao: FavouritesDao
}