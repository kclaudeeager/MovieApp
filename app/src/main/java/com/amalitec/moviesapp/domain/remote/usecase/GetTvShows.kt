package com.amalitec.moviesapp.domain.remote.usecase

import com.amalitec.moviesapp.common.Resource
import com.amalitec.moviesapp.data.remote.dto.toMovie
import com.amalitec.moviesapp.domain.model.Movie
import com.amalitec.moviesapp.domain.repository.RemoteMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTvShows @Inject constructor(
    private val repository: RemoteMovieRepository
    ) {
    operator fun invoke(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading<List<Movie>>())
            val movies = mutableListOf<Movie>()
            val tvShows=repository.getTVShows().toMovie()
            movies.add(tvShows)
            emit(Resource.Success<List<Movie>>(movies))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Movie>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (ex: IOException) {
            emit(Resource.Error<List<Movie>>("Couldn't reach server please check your internet connection"))
        }
    }
}