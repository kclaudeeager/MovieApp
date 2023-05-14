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
fun MovieEntity.toMovie(): Movie {
    val movieType = when (type) {
        "latest" -> MovieType.Latest
        "top_rated" -> MovieType.TopRated
        "featured" -> MovieType.Featured
        "tv_show" -> MovieType.TvShow
        else -> throw IllegalArgumentException("Invalid movie type: $type")
    }
    return Movie(id!!, title, description, popularity, releaseDate, posterPath, backdropPath, movieType)
}
