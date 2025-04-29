package com.example.apimovies.presentation.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.apimovies.data.Movie
import com.example.apimovies.ui.theme.APIMOVIESTheme
import kotlinx.coroutines.delay

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
    movieLength: String,
    onFavoriteClicked: (Movie) -> Unit = {},
    isFavorite: Boolean,
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
        movieLength = movieLength
    )

    val animationState = remember { mutableStateOf(AnimationState()) }

    LaunchedEffect(Unit) {
        delay(300)
        animationState.value = animationState.value.copy(titleVisible = true)
        delay(100)
        animationState.value = animationState.value.copy(row2Visible = true)
        delay(100)
        animationState.value = animationState.value.copy(row3Visible = true)
        delay(100)
        animationState.value = animationState.value.copy(row4Visible = true)
    }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = movie.poster,
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )

        MovieInfo(
            movie = movie,
            animationState = animationState,
        )
        ExplodingFavoriteButton(
            isFavorite = isFavorite,
            onToggleFavorite = { onFavoriteClicked(movie) },
            modifier = Modifier.size(32.dp)
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = movie.description,
            style = MaterialTheme.typography.titleSmall
        )

        Spacer(modifier = Modifier.height(25.dp))
    }
}

@Composable
private fun MovieInfo(
    modifier: Modifier = Modifier,
    animationState: MutableState<AnimationState>,
    movie: Movie,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        AnimatedVisibility(
            visible = animationState.value.titleVisible,
            enter = slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(500))
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = movie.name,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
            )
        }

        AnimatedVisibility(
            visible = animationState.value.row2Visible,
            enter = slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(500))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${movie.kpRating}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = movie.alternativeName,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        AnimatedVisibility(
            visible = animationState.value.row3Visible,
            enter = slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(500))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${movie.year}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "${movie.genres}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

        AnimatedVisibility(
            visible = animationState.value.row4Visible,
            enter = slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(500))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "${movie.countries}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = movie.movieLength,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

data class AnimationState(
    var titleVisible: Boolean = false,
    var row2Visible: Boolean = false,
    var row3Visible: Boolean = false,
    var row4Visible: Boolean = false,
)

@Composable
@Preview
fun MovieDetailsScreenPreview() {
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
            description = "Описание фильма",
            movieLength = "1 ч 35 мин",
            isFavorite = true,
        )
    }
}