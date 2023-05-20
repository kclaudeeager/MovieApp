package com.amalitec.moviesapp.domain.local.usecase

import com.amalitec.moviesapp.data.local.MovieEntity
import com.amalitec.moviesapp.domain.repository.LocalMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovies  @Inject constructor(private val repository: LocalMovieRepository) {
   suspend operator fun invoke(): Flow<List<MovieEntity>> {
        return repository.getMovies()
    }
}

