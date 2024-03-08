package dev.androidbroadcast.newsapi

import androidx.annotation.IntRange
import dev.androidbroadcast.newsapi.modules.Article
import dev.androidbroadcast.newsapi.modules.Response
import dev.androidbroadcast.newsapi.modules.SortBy
import org.intellij.lang.annotations.Language
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

interface NewsApi {

    @GET("/everything")
    suspend fun everything(
        @Query("q") query: String? = null,
        @Query("from") date: Date? = null,
        @Query("to") to: Date? = null,
        @Query("to") languages: List<Language>? = null,
        @Query("sortBy") sortBy: SortBy? = null,
        @Query("pageSize") @IntRange(from=0, to=100) pageSize: Int = 100,
        @Query("page") @IntRange(from=1)  page: Int = 1,
    ): Response<Article>
}

