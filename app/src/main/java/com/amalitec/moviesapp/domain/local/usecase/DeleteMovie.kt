package com.amalitec.moviesapp.domain.local.usecase

import com.amalitec.moviesapp.data.local.MovieEntity
import com.amalitec.moviesapp.domain.repository.LocalMovieRepository
import javax.inject.Inject

class DeleteMovie @Inject constructor(private val repository: LocalMovieRepository) {
    suspend operator fun invoke(movieEntity: MovieEntity){
        repository.deleteMovie(movieEntity)
    }
}