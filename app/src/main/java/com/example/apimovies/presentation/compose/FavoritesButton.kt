package com.example.apimovies.presentation.compose

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isFavorite) Color.Red else Color.LightGray,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    val scale by animateFloatAsState(
        targetValue = if (isFavorite) 1.2f else 1.0f,
        animationSpec = tween(durationMillis = 300), label = ""
    )

    Box(
        modifier = modifier
            .scale(scale)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onToggleFavorite() }
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(targetState = isFavorite, label = "FavoriteIcon") { favorite ->
            Icon(
                imageVector = if (favorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if (favorite) "In favorites" else "Add to favorites",
                tint = Color.White
            )
        }
    }
}

@Composable
fun ExplodingFavoriteButton(
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val scale = remember { Animatable(1f) }
    var showExplosion by remember { mutableStateOf(false) }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        ExplosionEffect(visible = showExplosion)

        IconButton(
            onClick = {
                onToggleFavorite()
                showExplosion = true
                scope.launch {
                    scale.animateTo(1.4f, animationSpec = tween(100))
                    scale.animateTo(1f, animationSpec = tween(100))
                    delay(300)
                    showExplosion = false
                }
            },
            modifier = Modifier.scale(scale.value)
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                tint = if (isFavorite) Color.Red else Color.Gray,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun ExplosionEffect(visible: Boolean) {
    val alpha = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(visible) {
        if (visible) {
            alpha.snapTo(1f)
            offsetY.snapTo(0f)
            alpha.animateTo(0f, tween(300))
            offsetY.animateTo(-30f, tween(300))
        }
    }

    if (visible) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier
                .offset(y = offsetY.value.dp)
                .alpha(alpha.value)
                .size(16.dp)
        )
    }
}