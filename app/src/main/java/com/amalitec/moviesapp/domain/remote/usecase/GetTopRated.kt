package com.amalitec.moviesapp.domain.remote.usecase

import com.amalitec.moviesapp.common.Resource
import com.amalitec.moviesapp.data.remote.dto.Result
import com.amalitec.moviesapp.data.remote.dto.toMovie
import com.amalitec.moviesapp.domain.model.Movie
import com.amalitec.moviesapp.domain.repository.RemoteMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTopRated @Inject constructor(
    private val repository: RemoteMovieRepository
    ) {
    operator fun invoke(): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading<List<Movie>>())
            val featuredMovies = mutableListOf<Movie>()
            repository.getTopRatedMovies().results.forEach { result: Result ->
                featuredMovies.add(result.toMovie("topRated"))
            }
            emit(Resource.Success<List<Movie>>(featuredMovies))

        } catch (e: HttpException) {
            emit(Resource.Error<List<Movie>>(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (ex: IOException) {
            emit(Resource.Error<List<Movie>>("Couldn't reach server please check your internet connection"))
        }
    }

}