package dev.androidbroadcast.newsapi

import androidx.annotation.IntRange
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import dev.androidbroadcast.newsapi.modules.ArticleDTO
import dev.androidbroadcast.newsapi.modules.ResponseDTO
import dev.androidbroadcast.newsapi.modules.LanguageDTO
import dev.androidbroadcast.newsapi.modules.SortByDTO
import dev.androidbroadcast.newsapi.utils.NewsApiKeyInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.Date

interface NewsApi {

    @GET("/everything")
    suspend fun everything(
        @Header("X-Api-Key") apikey: String,
        @Query("q") query: String? = null,
        @Query("from") date: Date? = null,
        @Query("to") to: Date? = null,
        @Query("languages") languages: List<LanguageDTO>? = null,
        @Query("sortBy") sortBy: SortByDTO? = null,
        @Query("pageSize") @IntRange(from=0, to=100) pageSize: Int = 100,
        @Query("page") @IntRange(from=1)  page: Int = 1,
    ): Result<ResponseDTO<ArticleDTO>>
}

fun NewsApi(
    baseUrl: String,
    apikey: String,
    okHttpClient: OkHttpClient? = null,
    json: Json = Json,
): NewsApi {
    return retrofit(baseUrl, apikey, okHttpClient, json).create()
}

private fun retrofit(
    baseUrl: String,
    apikey: String,
    okHttpClient: OkHttpClient?,
    json: Json,
): Retrofit {
    val jsonConverterFactory = json.asConverterFactory(MediaType.get("application/json"))

    val modifiedOkHttpClient =(okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
        .addInterceptor(NewsApiKeyInterceptor(apikey))
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(modifiedOkHttpClient)
        .build()
}
