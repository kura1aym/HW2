package dev.androidbroadcast.newsapi

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.intellij.lang.annotations.Language
import retrofit2.http.GET
import retrofit2.http.Query
import androidx.annotation.IntRange
import java.util.Date

interface NewsApi {

    @GET("/everything")
    fun everything(
        @Query("q") query: String? = null,
        @Query("from") date: Date? = null,
        @Query("to") to: Date? = null,
        @Query("to") languages: List<Language>? = null,
        @Query("sortBy") sortBy: SortBy? = null,
        @Query("pageSize") @IntRange(from=0, to=100) pageSize: Int = 100,
        @Query("page") @IntRange(from=1)  page: Int = 1,
    )
}

@kotlinx.serialization.Serializable
enum class language {
    @SerialName("ar")
    AR,

    @SerialName("de")
    DE,

    @SerialName("en")
    EN,

    @SerialName("es")
    ES,

    @SerialName("fr")
    FR,

    @SerialName("he")
    HE,

    @SerialName("it")
    IT,

    @SerialName("nl")
    NL,

    @SerialName("no")
    NO,

    @SerialName("pt")
    PT,

    @SerialName("ru")
    RU,

    @SerialName("sv")
    SV,

    @SerialName("ud")
    UD,

    @SerialName("zh")
    ZH,
}

@Serializable
enum class SortBy {
    @SerialName("relevancy")
    RELEVANCY,

    @SerialName("popularity")
    POPULARITY,

    @SerialName("publishedAt")
    PUBLISHED_AT,
}
