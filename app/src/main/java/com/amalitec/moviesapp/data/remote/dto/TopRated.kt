package com.amalitec.moviesapp.data.remote.dto
data class TopRated(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)