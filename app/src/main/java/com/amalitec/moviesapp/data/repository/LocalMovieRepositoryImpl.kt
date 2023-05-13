package com.amalitec.moviesapp.data.repository

import com.amalitec.moviesapp.data.local.MovieDto
import com.amalitec.moviesapp.data.local.MovieEntity
import com.amalitec.moviesapp.domain.repository.LocalMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalMovieRepositoryImpl @Inject constructor(private val movieDto: MovieDto) : LocalMovieRepository {

    override fun getMovies(): Flow<List<MovieEntity>> {
       return movieDto.getMovies()
    }

    override suspend fun getMovieById(id: Int): MovieEntity? {
       return movieDto.getMovieById(id)
    }

    override suspend fun getMovieByType(type: String): Flow<List<MovieEntity>> {
      return movieDto.getMovieByType(type)
    }

    override suspend fun insertMovie(movieEntity: MovieEntity) {
      movieDto.insertMovie(movieEntity)
    }

    override suspend fun deleteMovie(movieEntity: MovieEntity) {
      movieDto.deleteMovie(movieEntity)
    }
}