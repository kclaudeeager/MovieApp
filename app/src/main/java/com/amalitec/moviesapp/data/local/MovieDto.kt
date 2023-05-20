package com.amalitec.moviesapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDto {

    @Query("SELECT * FROM movie")
     fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Query("SELECT * FROM movie WHERE type = :type")
     fun getMovieByType(type: String):Flow<List<MovieEntity>>

    @Upsert
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Delete
    suspend fun deleteMovie(movieEntity: MovieEntity)
}