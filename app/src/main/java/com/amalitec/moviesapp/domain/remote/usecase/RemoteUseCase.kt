package com.amalitec.moviesapp.domain.remote.usecase

import com.amalitec.moviesapp.common.Resource
import com.amalitec.moviesapp.data.remote.dto.Result
import com.amalitec.moviesapp.data.remote.dto.toMovie
import com.amalitec.moviesapp.domain.model.Movie
import com.amalitec.moviesapp.domain.model.toMovieEntity
import com.amalitec.moviesapp.domain.repository.LocalMovieRepository
import com.amalitec.moviesapp.domain.repository.RemoteMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemoteUseCase @Inject constructor(
    private val remoteRepository: RemoteMovieRepository,
    private val localRepository: LocalMovieRepository
) {

    operator fun invoke(): Flow<Resource<List<Movie>>> {
        return combine(
            getTopRatedOrFeaturedMovies(),
            getTvShows(),
            getLatestMovies()
        ) { topRatedOrFeaturedMovies, tvShows, latestMovies ->
            // Combine the results of the three functions into a single list
            val movies = mutableListOf<Movie>()
            movies.addAll(topRatedOrFeaturedMovies.data ?: emptyList())
            movies.addAll(tvShows.data ?: emptyList())
            movies.addAll(latestMovies.data ?: emptyList())

            val error = listOf(topRatedOrFeaturedMovies, tvShows, latestMovies)
                .filterIsInstance<Resource.Error<String>>()
                .firstOrNull()

            if (error != null) {
                Resource.Error(error.message ?: "An unexpected error occurred")
            } else {
                Resource.Success(movies)
            }

        }
    }


    private fun getTopRatedOrFeaturedMovies(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading<List<Movie>>()) // Emit loading state
            val movies = mutableListOf<Movie>()
          remoteRepository.getFeaturedMovies().results.map { result: Result ->
                val movie = result.toMovie("featured")
                movies.add(movie)
                localRepository.insertMovie( movie.toMovieEntity())
            }
           remoteRepository.getTopRatedMovies().results.map { result: Result ->
                val movie = result.toMovie("top_rated")
                movies.add(movie)
                localRepository.insertMovie( movie.toMovieEntity())
            }
            emit(Resource.Success<List<Movie>>(movies))

        } catch (e: HttpException) {
            emitError<List<Movie>>(e.localizedMessage ?: "An unexpected error occurred")
        } catch (ex: IOException) {
            emitError<List<Movie>>("Couldn't reach server. Please check your internet connection.")
        }
    }

    private fun getTvShows(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading<List<Movie>>()) // Emit loading state
            val movies = mutableListOf<Movie>()
            val tvShows = remoteRepository.getTVShows().toMovie()
            movies.add(tvShows)
            localRepository.insertMovie(tvShows.toMovieEntity())
            emit(Resource.Success<List<Movie>>(movies)) // Emit success state with movies
        } catch (e: HttpException) {
            emitError<List<Movie>>(e.localizedMessage ?: "An unexpected error occurred")
        } catch (ex: IOException) {
            emitError<List<Movie>>("Couldn't reach server. Please check your internet connection.")
        }
    }

    private fun getLatestMovies(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading<List<Movie>>())
            val movies = mutableListOf<Movie>()
            val latestMovie = remoteRepository.getLatestMovies().toMovie()
            movies.add(latestMovie)
            localRepository.insertMovie(latestMovie.toMovieEntity())
            emit(Resource.Success<List<Movie>>(movies))
        } catch (e: HttpException) {
            emitError<List<Movie>>(e.localizedMessage ?: "An unexpected error occurred")
        } catch (ex: IOException) {
            emitError<List<Movie>>("Couldn't reach server. Please check your internet connection.")
        }
    }


    private suspend fun <T> FlowCollector<Resource<T>>.emitError(errorMessage: String) {
        emit(Resource.Error(errorMessage))
    }

}
