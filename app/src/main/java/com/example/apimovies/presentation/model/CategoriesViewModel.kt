package com.example.apimovies.presentation.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import com.example.apimovies.data.Category

@Stable
interface CategoriesViewModel {
    @get:Composable
    val viewState: State<List<Category>>
}