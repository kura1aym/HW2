package dev.androidbroadcast.news.data.model

import dev.androidbroadcast.newsapi.modules.SourceDTO
import java.util.Date

data class Article (
    val id: Long,
    val source: SourceDTO,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: Date,
    val content: String,
)

data class Source(
    val id: String,
    val name: String
)