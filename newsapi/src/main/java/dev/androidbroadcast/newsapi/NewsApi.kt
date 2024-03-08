package dev.androidbroadcast.newsapi

import java.io.Serial

interface NewsApi {

    @GET("/everything")
    fun everything(
        @Query("q") query: String? = null,
        @Query("from") date: Date? = null,
        @Query("to") to: Date? = null,
        @Query("to") languages: String? = null,
        @Query("sortBy") sortBy: SortBy? = null,
    )
}

//revelancy, popularity, publishedAt
enum class SortBy {
    @SerialName("relevancy")
    RELEVANCY,

    @SerialName("popularity")
    POPULARITY

    @SerialName("publishedAt")
    PUBLISHED_AT
}