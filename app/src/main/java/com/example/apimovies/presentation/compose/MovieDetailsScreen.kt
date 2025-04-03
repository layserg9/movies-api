package com.example.apimovies.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.apimovies.data.Movie
import com.example.apimovies.ui.theme.APIMOVIESTheme

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    id: Long,
    name: String,
    alternativeName: String,
    year: Long,
    genres: List<String>,
    countries: List<String>,
    poster: String,
    kpRating: Double,
    description: String,
) {
    val movie = Movie(
        id = id,
        name = name,
        alternativeName = alternativeName,
        year = year,
        genres = genres,
        countries = countries,
        poster = poster,
        kpRating = kpRating,
        description = description,
    )

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = movie.poster,
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )
        Text(text = movie.name, style = MaterialTheme.typography.titleMedium)
        Text(
            text = "${movie.alternativeName}, ${movie.year}",
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = movie.description,
            style = MaterialTheme.typography.titleSmall
        )
        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
@Preview
fun MovieDetailsScreenPreview(){
    APIMOVIESTheme {
        MovieDetailsScreen(
            id = 1234L,
            name = "Title",
            alternativeName = "Alternative title",
            year = 2025L,
            genres = listOf("action", "horror"),
            countries = listOf("USA", "Mexico"),
            poster = "https://image.openmoviedb.com/kinopoisk-images/4303601/3f89baba-e34d-4526-be68-bbadf0494212/x1000",
            kpRating = 7.5,
            description = "Описание фильма"
        )
    }
}