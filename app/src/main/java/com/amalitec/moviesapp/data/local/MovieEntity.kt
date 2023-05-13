package com.amalitec.moviesapp.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amalitec.moviesapp.domain.model.Movie
import com.amalitec.moviesapp.domain.util.MovieType

@Entity
data class MovieEntity(
    @PrimaryKey val id: Int? = null,
    val title:String,
    val description:String,
    val popularity:Double,
    val releaseDate:String,
    val posterPath:String,
    val backdropPath:String,
    val type: String
)
