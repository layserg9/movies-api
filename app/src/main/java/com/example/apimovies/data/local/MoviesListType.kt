package com.example.apimovies.data.local

enum class MoviesListType {
    GREAT,
    POPULAR,
    PROGRAMMERS,
    TRENDING,
    ZOMBIE;
    fun displayName(): String {
        return when (this) {
            GREAT -> "100 великих фильмов XXI века"
            POPULAR -> "Популярное"
            ZOMBIE -> "ЗОМБИ!!!"
            TRENDING -> "Ждем!"
            PROGRAMMERS -> "Фильмы и сериалы про программистов"
        }
    }
    fun getSlug(): String {
        return when (this) {
            GREAT -> "100_greatest_movies_XXI"
            POPULAR -> "popular-films"
            ZOMBIE -> "theme_zombie"
            TRENDING -> "planned-to-watch-films"
            PROGRAMMERS -> "about_programmers"
        }
    }
}