package com.amalitec.moviesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MovieDatabase: RoomDatabase() {

    abstract val movieDto: MovieDto

    companion object {
        const val DATABASE_NAME = "movies_db"
    }
}