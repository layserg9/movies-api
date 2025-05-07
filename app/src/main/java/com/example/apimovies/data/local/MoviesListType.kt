package com.example.apimovies.data.local

enum class MoviesListType {
//    NEW,
    POPULAR,
//    RECOMMENDED,
    TRENDING,
    ANIME;
    fun displayName(): String {
        return when (this) {
//            NEW -> "Новинки"
            POPULAR -> "Популярное"
            ANIME -> "Аниме"
            TRENDING -> "Ждем!"
//            RECOMMENDED -> "Рекомендуемое"
        }
    }
    fun getSlug(): String {
        return when (this) {
//            NEW -> "Новинки"
            POPULAR -> "popular-films"
            ANIME -> "!anime"
            TRENDING -> "planned-to-watch-films"
//            RECOMMENDED -> "Рекомендуемое"
        }
    }
}