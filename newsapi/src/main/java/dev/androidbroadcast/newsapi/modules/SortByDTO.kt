package dev.androidbroadcast.newsapi.modules

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SortByDTO {
    @SerialName("relevancy")
    RELEVANCY,

    @SerialName("popularity")
    POPULARITY,

    @SerialName("publishedAt")
    PUBLISHED_AT,
}