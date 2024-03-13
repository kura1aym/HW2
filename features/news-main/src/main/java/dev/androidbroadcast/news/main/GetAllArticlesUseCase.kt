package dev.androidbroadcast.news.main

import dev.androidbroadcast.news.data.ArticlesRepository
import dev.androidbroadcast.news.data.RequestResult
import dev.androidbroadcast.news.data.model.Article
import kotlinx.coroutines.flow.Flow

class GetAllArticlesUseCase(private val repository: ArticlesRepository) {
    operator fun invoke(): RequestResult<Flow<List<Article>>> {
        return repository.getAll()
    }
}