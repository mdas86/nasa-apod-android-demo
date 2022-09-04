package com.example.nasaapod.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PhotoEntity::class],
    version = 1
)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract val dao: FavoritesDao
}