package com.amalitec.moviesapp.domain.local.usecase

import com.amalitec.moviesapp.data.local.MovieEntity
import com.amalitec.moviesapp.domain.repository.LocalMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieByType @Inject constructor(private val repository: LocalMovieRepository) {
    suspend operator fun invoke(type:String): Flow<List<MovieEntity>> {
        return repository.getMovieByType(type)
    }
}