package com.amalitec.moviesapp.di
import android.app.Application
import androidx.room.Room
import com.amalitec.moviesapp.common.Constants.BASE_URL
import com.amalitec.moviesapp.data.local.MovieDatabase
import com.amalitec.moviesapp.data.remote.MovieApi
import com.amalitec.moviesapp.data.repository.LocalMovieRepositoryImpl
import com.amalitec.moviesapp.data.repository.RemoteMovieRepositoryImpl
import com.amalitec.moviesapp.domain.repository.LocalMovieRepository
import com.amalitec.moviesapp.domain.repository.RemoteMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideMovieApi(): MovieApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }
    @Provides
    @Singleton
    fun provideMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            MovieDatabase.DATABASE_NAME
        ).build()
    }
    @Provides
    @Singleton
    fun provideLocalMovieRepository(db: MovieDatabase): LocalMovieRepository {
        return LocalMovieRepositoryImpl(db.movieDto)
    }
    @Provides
    @Singleton
    fun provideRemoteMovieRepository(api: MovieApi): RemoteMovieRepository {
        return RemoteMovieRepositoryImpl(api)
    }

}