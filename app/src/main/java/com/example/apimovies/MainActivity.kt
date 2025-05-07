package com.example.apimovies

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.apimovies.presentation.MainListScreenViewModelImpl
import com.example.apimovies.presentation.CategoriesViewModelImpl
import com.example.apimovies.presentation.FavoritesListScreenViewModelImpl
import com.example.apimovies.presentation.MoviesListScreenViewModelImpl
import com.example.apimovies.presentation.MovieDetailsViewModelImpl
import com.example.apimovies.presentation.compose.AlternativeScreen
import com.example.apimovies.presentation.compose.CategoriesScreen
import com.example.apimovies.presentation.compose.FavoritesListScreen
import com.example.apimovies.presentation.compose.MoviesListScreen
import com.example.apimovies.presentation.compose.MovieDetailsScreen
import com.example.apimovies.presentation.model.MainListScreenViewModel
import com.example.apimovies.presentation.model.CategoriesViewModel
import com.example.apimovies.presentation.model.FavoritesListScreenViewModel
import com.example.apimovies.presentation.model.MoviesListScreenViewModel
import com.example.apimovies.presentation.model.MovieDetailsViewModel
import com.example.apimovies.ui.theme.APIMOVIESTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.map
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
                        startDestination = AltList
                    ) {
                        composable<AltList> {
                            val altListViewModel: MainListScreenViewModel =
                                hiltViewModel<MainListScreenViewModelImpl>()

                            val moviesMap by altListViewModel.moviesMapState
                            val categoriesList by altListViewModel.categoriesViewState

                            AlternativeScreen(
                                modifier = Modifier.padding(innerPadding),
                                moviesByCategory = moviesMap,
                                categoriesList = categoriesList,
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
                                            movieLength = movie.movieLength,
                                        )
                                    )
                                },
                                onCategoryClick = { slug ->
                                    navController.navigate(MoviesList(slug))
                                },
                                onShowMoreMoviesClick = { type ->
                                    navController.navigate(MoviesList(type.getSlug()))
                                },
                                onShowMoreCategoriesClick = { navController.navigate(Categories) }
                            )
                        }
                        composable<MoviesList> {
                            val viewModel: MoviesListScreenViewModel =
                                hiltViewModel<MoviesListScreenViewModelImpl>()
                            val args = it.toRoute<MoviesList>()

                            LaunchedEffect(args.categorySlug) {
                                viewModel.loadMoviesByCategory(args.categorySlug ?: "")
                            }

                            val list by viewModel.viewState
                            val moviesByCategory by viewModel.moviesByCategoryViewState
                            val listToShow =
                                if (args.categorySlug != null) moviesByCategory.toImmutableList() else list

                            MoviesListScreen(
                                modifier = Modifier.padding(innerPadding),
                                list = listToShow,
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
                                            movieLength = movie.movieLength,
                                        )
                                    )
                                }
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
                                            movieLength = movie.movieLength,
                                        )
                                    )
                                },
                            )
                        }
                        composable<MovieDetails> {
                            val args = it.toRoute<MovieDetails>()
                            val viewModel: MovieDetailsViewModel =
                                hiltViewModel<MovieDetailsViewModelImpl>()
                            val isFavorite by viewModel.favoriteIds
                                .map { it.contains(args.id) }
                                .collectAsState(initial = false)

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
                                movieLength = args.movieLength,
                                isFavorite = isFavorite,
                                onFavoriteClicked = { movie ->
                                    viewModel.onFavoriteClicked(movie)
                                }
                            )
                        }
                        composable<Categories> {
                            val viewModel: CategoriesViewModel =
                                hiltViewModel<CategoriesViewModelImpl>()
                            val list by viewModel.viewState

                            CategoriesScreen(
                                modifier = Modifier.padding(innerPadding),
                                list = list,
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
        route = AltList,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),
    BottomNavigationItem(
        title = "Избранное",
        route = FavoritesList,
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        badgeCount = 2
    ), BottomNavigationItem(
        title = "Категории",
        route = Categories,
        selectedIcon = Icons.Filled.Category,
        unselectedIcon = Icons.Outlined.Category,
    )
)

@Serializable
object AltList

@Serializable
data class MoviesList(val categorySlug: String? = null)

@Serializable
object FavoritesList

@Serializable
object Categories

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
    val movieLength: String,
)

data class BottomNavigationItem(
    val title: String,
    val route: Any,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
)