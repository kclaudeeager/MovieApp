package com.amalitec.moviesapp.domain.repository

import com.amalitec.moviesapp.data.remote.dto.Latest
import com.amalitec.moviesapp.data.remote.dto.TopRatedOrFeatured
import com.amalitec.moviesapp.data.remote.dto.TvShow

interface RemoteMovieRepository {

 suspend fun getLatestMovies(): Latest

 suspend fun getFeaturedMovies() : TopRatedOrFeatured

 suspend fun getTopRatedMovies() : TopRatedOrFeatured

 suspend fun getTVShows() : TvShow


}