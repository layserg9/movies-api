package com.example.apimovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
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