package com.example.apimovies

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.apimovies.data.Movie
import com.example.apimovies.presentation.FavoritesListScreenViewModelImpl
import com.example.apimovies.presentation.MainListScreenViewModelImpl
import com.example.apimovies.presentation.MovieDetailsViewModelImpl
import com.example.apimovies.presentation.compose.FavoritesListScreen
import com.example.apimovies.presentation.compose.MainListScreen
import com.example.apimovies.presentation.compose.MovieDetailsScreen
import com.example.apimovies.presentation.model.FavoritesListScreenViewModel
import com.example.apimovies.presentation.model.MainListScreenViewModel
import com.example.apimovies.presentation.model.MovieDetailsViewModel
import com.example.apimovies.ui.theme.APIMOVIESTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val windowInsetsController = window.insetsController
        windowInsetsController?.hide(WindowInsets.Type.navigationBars())
        windowInsetsController?.systemBarsBehavior =
            WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        setContent {
            APIMOVIESTheme {
                var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            bottomNavigationItems.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index
                                        navController.navigate(item.route)
                                    },
                                    label = { Text(text = item.title) },
                                    icon = {
                                        BadgedBox(
                                            badge = {
                                                if (item.badgeCount != null) {
                                                    Badge {
                                                        Text(
                                                            text = item.badgeCount.toString()
                                                        )
                                                    }
                                                }
                                            }
                                        ) {
                                            Icon(
                                                contentDescription = "",
                                                imageVector = if (index == selectedItemIndex) {
                                                    item.selectedIcon
                                                } else item.unselectedIcon
                                            )
                                        }
                                    },
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = MainList
                    ) {
                        composable<MainList> {
                            val viewModel: MainListScreenViewModel =
                                hiltViewModel<MainListScreenViewModelImpl>()
                            val list by viewModel.viewState

                            MainListScreen(
                                modifier = Modifier.padding(innerPadding),
                                list = list,
                                onItemClick = { movie ->
                                    navController.navigate(
                                        MovieDetails(
                                            id = movie.id,
                                            name = movie.name,
                                            alternativeName = movie.alternativeName,
                                            year = movie.year,
                                            genres = movie.genres,
                                            countries = movie.countries,
                                            poster = movie.poster,
                                            kpRating = movie.kpRating,
                                            description = movie.description,
                                        )
                                    )
                                },
                            )
                        }
                        composable<FavoritesList> {
                            val viewModel: FavoritesListScreenViewModel =
                                hiltViewModel<FavoritesListScreenViewModelImpl>()
                            val list by viewModel.viewState

                            FavoritesListScreen(
                                modifier = Modifier.padding(innerPadding),
                                list = list,
                                onMovieClick = { movie ->
                                    navController.navigate(
                                        MovieDetails(
                                            id = movie.id,
                                            name = movie.name,
                                            alternativeName = movie.alternativeName,
                                            year = movie.year,
                                            genres = movie.genres,
                                            countries = movie.countries,
                                            poster = movie.poster,
                                            kpRating = movie.kpRating,
                                            description = movie.description,
                                        )
                                    )
                                },
                            )
                        }
                        composable<MovieDetails> {
                            val args = it.toRoute<MovieDetails>()

                            val viewModel: MovieDetailsViewModel =
                                hiltViewModel<MovieDetailsViewModelImpl>()

                            MovieDetailsScreen(
                                modifier = Modifier.padding(innerPadding),
                                id = args.id,
                                name = args.name,
                                alternativeName = args.alternativeName,
                                year = args.year,
                                genres = args.genres,
                                countries = args.countries,
                                poster = args.poster,
                                kpRating = args.kpRating,
                                description = args.description,
                                addToFavorites = { movie ->
                                    viewModel.addToFavorite(movie)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

val list = persistentListOf(
    Movie.preview().copy(id = 1),
    Movie.preview().copy(id = 2),
    Movie.preview().copy(id = 3),
    Movie.preview().copy(id = 4),
    Movie.preview().copy(id = 5),
    Movie.preview().copy(id = 6),
    Movie.preview().copy(id = 7),
    Movie.preview().copy(id = 8),
)

val bottomNavigationItems = listOf(
    BottomNavigationItem(
        title = "Главное",
        route = MainList,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),
    BottomNavigationItem(
        title = "Избранное",
        route = FavoritesList,
        selectedIcon = Icons.Filled.FavoriteBorder,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        badgeCount = 2
    ), BottomNavigationItem(
        title = "Иное",
        route = MainList,
        selectedIcon = Icons.Filled.Build,
        unselectedIcon = Icons.Outlined.Build,
    )
)

@Serializable
object MainList

@Serializable
object FavoritesList

@Serializable
data class MovieDetails(
    val id: Long,
    val name: String,
    val alternativeName: String,
    val year: Long,
    val genres: List<String>,
    val countries: List<String>,
    val poster: String,
    val kpRating: Double,
    val description: String,
)

data class BottomNavigationItem(
    val title: String,
    val route: Any,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
)