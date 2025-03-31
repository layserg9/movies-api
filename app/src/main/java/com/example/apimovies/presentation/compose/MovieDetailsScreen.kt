package com.example.apimovies.presentation.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.apimovies.data.Movie
import com.example.apimovies.ui.theme.LabelsSecondaryLight
import com.example.apimovies.ui.theme.OnPrimaryLightLight

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    id: Long,
    name: String,
    alternativeName: String?,
    year: Long,
    genres: List<String>,
    countries: List<String>,
    poster: String,
    kpRating: Double?,
) {
    val movie = Movie(
        id = id,
        name = name,
        alternativeName = alternativeName,
        year = year,
        genres = genres,
        countries = countries,
        poster = poster,
        kpRating = kpRating
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = OnPrimaryLightLight),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            AsyncImage(
                modifier = Modifier.height(100.dp),
                model = movie.poster,
                contentDescription = ""
            )
            MovieInfo(movie = movie)
        }
        Text(
            text = "${movie.kpRating}",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(end = 16.dp)

        )
    }
}

@Composable
private fun MovieInfo(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    )
    {
        Text(text = movie.name?: "", style = MaterialTheme.typography.titleMedium)
        Text(
            text = "${movie.alternativeName}, ${movie.year}",
            style = MaterialTheme.typography.titleSmall
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val countries = movie.countries.joinToString { it }
            val genres = movie.genres.joinToString { it }

            Text(text = countries)
            Canvas(modifier = Modifier.size(2.dp)) {
                drawCircle(color = LabelsSecondaryLight)
            }
            Text(text = genres)
        }
    }
}