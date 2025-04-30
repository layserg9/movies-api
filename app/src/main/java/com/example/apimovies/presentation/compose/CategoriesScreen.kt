package com.example.apimovies.presentation.compose

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.apimovies.data.Categories
import com.example.apimovies.ui.theme.Elevation08DpLight
import com.example.apimovies.ui.theme.OnPrimaryLightLight

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    list: List<Categories>,
    onItemClick: (Categories) -> Unit = {},
) {
    Log.d("TestTest", "$list")
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(color = Elevation08DpLight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 10.dp)

    ) {
        items(items = list, key = { it.id }) { categoryItem ->
            CategoryItem(
                category = categoryItem,
                onClick = onItemClick
            )
        }
    }
}

@Composable
private fun CategoryItem(
    modifier: Modifier = Modifier,
    category: Categories,
    onClick: (Categories) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = OnPrimaryLightLight)
            .clickable { onClick(category) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .height(150.dp)
                .aspectRatio(1f / 1f)
                .padding(25.dp),
            model = category.cover,
            contentDescription = "",
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.Start),
            text = category.name,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Start
        )
    }
}