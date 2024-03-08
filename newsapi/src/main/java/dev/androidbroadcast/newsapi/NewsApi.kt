package dev.androidbroadcast.newsapi

import androidx.annotation.IntRange
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.androidbroadcast.newsapi.modules.Article
import dev.androidbroadcast.newsapi.modules.Response
import dev.androidbroadcast.newsapi.modules.Language
import dev.androidbroadcast.newsapi.modules.SortBy
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

interface NewsApi {

    @GET("/everything")
    suspend fun everything(
        @Query("q") query: String? = null,
        @Query("from") date: Date? = null,
        @Query("to") to: Date? = null,
        @Query("languages") languages: List<Language>? = null,
        @Query("sortBy") sortBy: SortBy? = null,
        @Query("pageSize") @IntRange(from=0, to=100) pageSize: Int = 100,
        @Query("page") @IntRange(from=1)  page: Int = 1,
    ): Response<Article>
}

fun NewsApi(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
    json: Json = Json,
): NewsApi {
    return retrofit(baseUrl, okHttpClient, json).create()
}

private fun retrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient?,
    json: Json,
): Retrofit {
    val jsonConverterFactory = json.asConverterFactory(MediaType.get("application/json"))
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverterFactory)
        .run {
            if (okHttpClient != null) client(okHttpClient) else this
        }
        .build()
}
