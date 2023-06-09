package com.amalitec.moviesapp.data.repository

import com.amalitec.moviesapp.BuildConfig
import com.amalitec.moviesapp.data.remote.MovieApi
import com.amalitec.moviesapp.data.remote.dto.Latest
import com.amalitec.moviesapp.data.remote.dto.TopRatedOrFeatured
import com.amalitec.moviesapp.data.remote.dto.TvShow
import com.amalitec.moviesapp.domain.repository.RemoteMovieRepository
import javax.inject.Inject

class RemoteMovieRepositoryImpl @Inject constructor(private val api: MovieApi) :
    RemoteMovieRepository {
   private val apiKey = BuildConfig.API_KEY

    override suspend fun getLatestMovies(): Latest {

       return api.getLatestMovies(apiKey)
    }

    override suspend fun getFeaturedMovies(): TopRatedOrFeatured {
        return api.getFeaturedMovies(apiKey)
    }

    override suspend fun getTopRatedMovies(): TopRatedOrFeatured {
        return api.getTopRatedMovies(apiKey)
    }


    override suspend fun getTVShows(): TvShow {
       return api.getLatestTvShows(apiKey)
    }
}