package com.example.apimovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.apimovies.data.MovieItem
import com.example.apimovies.presentation.compose.MainListScreen
import com.example.apimovies.ui.theme.APIMOVIESTheme
import kotlinx.collections.immutable.persistentListOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            APIMOVIESTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainListScreen(
                        modifier = Modifier.padding(innerPadding),
                        list = list
                    )
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