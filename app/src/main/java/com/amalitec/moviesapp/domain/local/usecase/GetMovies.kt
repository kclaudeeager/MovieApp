package com.amalitec.moviesapp.domain.local.usecase

import com.amalitec.moviesapp.data.local.MovieEntity
import com.amalitec.moviesapp.domain.repository.LocalMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovies  @Inject constructor(private val repository: LocalMovieRepository) {
    suspend operator fun invoke() : Flow<List<MovieEntity>> {
        return repository.getMovies().map { movies ->
            val featuredMovies = movies.filter { it.type == "featured" }.take(1).toMutableList()
            val otherMovies = movies.filter { it.type != "featured" }
            featuredMovies.addAll(otherMovies)
            featuredMovies
        }
    }
}

