package com.example.apimovies.presentation.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.apimovies.R
import com.example.apimovies.data.MovieItem
import com.example.apimovies.ui.theme.APIMOVIESTheme
import com.example.apimovies.ui.theme.Elevation08DpLight
import com.example.apimovies.ui.theme.LabelsSecondaryDark
import com.example.apimovies.ui.theme.LabelsSecondaryLight
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MainListScreen(
    modifier: Modifier = Modifier,
    list: ImmutableList<MovieItem>,
    onClick: (MovieItem) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = Elevation08DpLight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 10.dp)

    ) {
        items(items = list, key = { it.id }) { movieItem ->
            MovieItem(
                movie = movieItem,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun MovieItem(
    modifier: Modifier = Modifier,
    movie: MovieItem,
    onClick: (MovieItem) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(movie) },
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
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
private fun MovieInfo(
    modifier: Modifier = Modifier,
    movie: MovieItem
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    )
    {
        Text(text = movie.name, style = MaterialTheme.typography.titleMedium)
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

@Composable
private fun MovieItemForPreview(
    modifier: Modifier = Modifier,
    movie: MovieItem
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
        MovieItemForPreview(movie = MovieItem.preview())
    }
}