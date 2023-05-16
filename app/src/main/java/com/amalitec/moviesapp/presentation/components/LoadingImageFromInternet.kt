package com.amalitec.moviesapp.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun LoadingImageFromInternet(
    url: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    var imageUrl=""
    imageUrl = if (url!="null"){
        "https://image.tmdb.org/t/p/w300$url"
    }
    else{
        "https://image.tmdb.org/t/p/w300/iJQIbOPm81fPEGKt5BPuZmfnA54.jpg"
    }
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )

}