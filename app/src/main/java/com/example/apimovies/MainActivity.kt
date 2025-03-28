package com.example.apimovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.apimovies.data.MovieItem
import com.example.apimovies.presentation.compose.MainListScreen
import com.example.apimovies.presentation.compose.MovieDetailsScreen
import com.example.apimovies.ui.theme.APIMOVIESTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            APIMOVIESTheme {
                var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
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
                            MainListScreen(
                                modifier = Modifier.padding(innerPadding),
                                list = list,
                                onItemClick = { movieItem ->
                                    navController.navigate(
                                        MovieDetails(
                                            id = movieItem.id,
                                            name = movieItem.name,
                                            alternativeName = movieItem.alternativeName,
                                            year = movieItem.year,
                                            genres = movieItem.genres,
                                            countries = movieItem.countries,
                                            poster = movieItem.poster,
                                            kpRating = movieItem.kpRating
                                        )
                                    )
                                }
                            )
                        }
                        composable<MovieDetails> {
                            val args = it.toRoute<MovieDetails>()
                            MovieDetailsScreen(
                                modifier = Modifier.padding(innerPadding),
                                id = args.id,
                                name = args.name,
                                alternativeName = args.alternativeName,
                                year = args.year,
                                genres = args.genres,
                                countries = args.countries,
                                poster = args.poster,
                                kpRating = args.kpRating
                            )
                        }
                    }
                }
            }
        }
    }
}

val list = persistentListOf(
    MovieItem.preview().copy(id = 1),
    MovieItem.preview().copy(id = 2),
    MovieItem.preview().copy(id = 3),
    MovieItem.preview().copy(id = 4),
    MovieItem.preview().copy(id = 5),
    MovieItem.preview().copy(id = 6),
    MovieItem.preview().copy(id = 7),
    MovieItem.preview().copy(id = 8),
    MovieItem.preview().copy(id = 9),
    MovieItem.preview().copy(id = 11),
    MovieItem.preview().copy(id = 22),
    MovieItem.preview().copy(id = 33),
    MovieItem.preview().copy(id = 44),
    MovieItem.preview().copy(id = 55),
    MovieItem.preview().copy(id = 66),
    MovieItem.preview().copy(id = 77),
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
        route = MovieDetails(
            id = 123123123,
            name = "movieItem.name",
            alternativeName = "movieItem.alternativeName",
            year = 2025L,
            genres = listOf("horror, action"),
            countries = listOf("Russia, USA"),
            poster = "https://image.openmoviedb.com/kinopoisk-images/4303601/3f89baba-e34d-4526-be68-bbadf0494212/x1000",
            kpRating = 10.0f
        ),
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
data class MovieDetails(
    val id: Long,
    val name: String,
    val alternativeName: String?,
    val year: Long,
    val genres: List<String>,
    val countries: List<String>,
    val poster: String,
    val kpRating: Float,
)

data class BottomNavigationItem(
    val title: String,
    val route: Any,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
)