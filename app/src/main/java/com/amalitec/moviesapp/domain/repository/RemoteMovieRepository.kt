package com.amalitec.moviesapp.domain.repository

import com.amalitec.moviesapp.data.remote.dto.Featured
import com.amalitec.moviesapp.data.remote.dto.Latest
import com.amalitec.moviesapp.data.remote.dto.TopRated
import com.amalitec.moviesapp.data.remote.dto.TvShow

interface RemoteMovieRepository {

 suspend fun getLatestMovies(): Latest

 suspend fun getFeaturedMovies() : Featured

 suspend fun getTopRatedMovies() : TopRated

 suspend fun getTVShows() : TvShow


}