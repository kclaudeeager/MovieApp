package com.amalitec.moviesapp.domain.repository

import com.amalitec.moviesapp.data.local.MovieEntity
import kotlinx.coroutines.flow.Flow

interface LocalMovieRepository {

    fun getMovies(): Flow<List<MovieEntity>>

    suspend fun getMovieById(id: Int): MovieEntity?

    suspend fun getMovieByType(type: String): Flow<List<MovieEntity>>

    suspend fun insertMovie(movieEntity: MovieEntity)

    suspend fun deleteMovie(movieEntity: MovieEntity)
}