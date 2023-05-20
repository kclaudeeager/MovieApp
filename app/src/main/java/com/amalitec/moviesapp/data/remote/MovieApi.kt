package com.amalitec.moviesapp.data.remote
import com.amalitec.moviesapp.data.remote.dto.Latest
import com.amalitec.moviesapp.data.remote.dto.TopRatedOrFeatured
import com.amalitec.moviesapp.data.remote.dto.TvShow
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

     @GET("movie/latest")
    suspend fun getLatestMovies(@Query("api_key") api_key:String):Latest

    @GET("movie/now_playing")
    suspend fun getFeaturedMovies(@Query("api_key") api_key:String):TopRatedOrFeatured

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") api_key:String): TopRatedOrFeatured

    @GET("tv/latest")
    suspend fun getLatestTvShows(@Query("api_key") api_key:String):TvShow

}
