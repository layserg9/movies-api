package com.example.apimovies.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.apimovies.data.Category
import com.example.apimovies.data.Movie
import com.example.apimovies.data.local.MoviesListType
import com.example.apimovies.ui.theme.TintsOrangeDark

@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier,
    moviesByCategory: Map<MoviesListType, List<Movie>>,
    categoriesList: List<Category>,
    onMovieClick: (Movie) -> Unit = {},
    onCategoryClick: (slug: String) -> Unit = {},
    onShowMoreMoviesClick: (MoviesListType) -> Unit = {},
    onShowMoreCategoriesClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 25.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        moviesByCategory.forEach { (type, list) ->
            if (list.isNotEmpty()) {
                item {
                    ListMovies(
                        list = list,
                        title = type.displayName(),
                        onMovieClick = onMovieClick,
                        onShowMoreClick = { onShowMoreMoviesClick(type) }
                    )
                }
                item { Spacer(modifier = Modifier.height(10.dp)) }
            }
        }

        item {
            CategoriesList(
                onCategoryClick = onCategoryClick,
                onShowMoreClick = onShowMoreCategoriesClick,
                list = categoriesList,
            )
        }
    }
}

@Composable
private fun ListMovies(
    modifier: Modifier = Modifier,
    list: List<Movie>,
    title: String,
    onMovieClick: (Movie) -> Unit = {},
    onShowMoreClick: () -> Unit = {},
) {
    Column(modifier = modifier.height(280.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f),
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable { onShowMoreClick() },
                text = "смотреть все",
                color = TintsOrangeDark,
                style = MaterialTheme.typography.titleSmall,
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top,
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) {
            items(items = list, key = { it.id }) { movieItem ->
                MovieItem(
                    movie = movieItem,
                    onClick = onMovieClick
                )
            }
        }
    }
}

@Composable
private fun CategoriesList(
    modifier: Modifier = Modifier,
    list: List<Category>,
    onCategoryClick: (slug: String) -> Unit = {},
    onShowMoreClick: () -> Unit = {},
) {
    Column(modifier = modifier.height(250.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "Категории",
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable { onShowMoreClick() },
                text = "смотреть все",
                color = TintsOrangeDark,
                style = MaterialTheme.typography.titleSmall,
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top,
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) {
            items(items = list, key = { it.id }) { categoryItem ->
                CategoryItem(
                    category = categoryItem,
                    onClick = { onCategoryClick(it.slug) },
                )
            }
        }
    }
}

@Composable
private fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: (Movie) -> Unit
) {
    Column(
        modifier = modifier
            .width(100.dp)
            .clickable { onClick(movie) },
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            model = movie.poster,
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = movie.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun CategoryItem(
    modifier: Modifier = Modifier,
    category: Category,
    onClick: (Category) -> Unit
) {
    Column(
        modifier = modifier
            .width(150.dp)
            .clickable { onClick(category) },
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            model = category.cover,
            contentDescription = "",
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = category.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}