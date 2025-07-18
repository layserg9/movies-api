package com.example.apimovies.presentation.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.apimovies.R
import com.example.apimovies.data.Movie
import com.example.apimovies.ui.theme.APIMOVIESTheme
import com.example.apimovies.ui.theme.Elevation08DpLight
import com.example.apimovies.ui.theme.LabelsSecondaryDark
import com.example.apimovies.ui.theme.LabelsSecondaryLight
import com.example.apimovies.ui.theme.OnPrimaryLightLight
import kotlinx.collections.immutable.ImmutableList

@Composable
fun FavoritesListScreen(
    modifier: Modifier = Modifier,
    list: ImmutableList<Movie>,
    onMovieClick: (Movie) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = Elevation08DpLight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 10.dp)

    ) {
        if (list.isEmpty()) {
            item {
                Text(
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .fillMaxWidth(),
                    text = "Список избранного пуст \uD83E\uDD37\u200D♂\uFE0F",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
        items(items = list, key = { it.id }) { movieItem ->
            MovieItem(
                movie = movieItem,
                onClick = onMovieClick
            )
        }
    }
}

@Composable
private fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: (Movie) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = OnPrimaryLightLight)
            .clickable { onClick(movie) },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(150.dp)
                    .aspectRatio(2f / 3f),
                model = movie.poster,
                contentDescription = ""
            )
            MovieInfo(movie = movie)
        }
        Text(
            text = if (movie.kpRating == 0.0) "" else "${movie.kpRating}",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(end = 16.dp)
                .wrapContentWidth()
        )
    }
}

@Composable
private fun MovieInfo(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    val text = listOf(movie.alternativeName, movie.year.takeIf { it != 0L }.toString())
        .filter { it.isNotBlank() }
        .joinToString(", ")

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    )
    {
        Text(
            text = movie.name,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = text,
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

@Composable
private fun MovieItemForPreview(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    Row(
        modifier = modifier
            .background(color = LabelsSecondaryDark)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.test_poster), contentDescription = "",
                modifier = Modifier.width(100.dp)
            )
            MovieInfo(movie = movie)
        }
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = "${movie.kpRating}",
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
@Preview
private fun MovieItemPreview() {
    APIMOVIESTheme {
        MovieItemForPreview(movie = Movie.preview())
    }
}