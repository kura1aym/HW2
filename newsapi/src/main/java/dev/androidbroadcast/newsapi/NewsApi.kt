package dev.androidbroadcast.newsapi

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