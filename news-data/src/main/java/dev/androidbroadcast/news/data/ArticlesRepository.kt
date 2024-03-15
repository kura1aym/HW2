package dev.androidbroadcast.news.data

import dev.androidbroadcast.news.data.model.Article
import dev.androidbroadcast.news.database.NewsDatabase
import dev.androidbroadcast.news.database.models.ArticleDBO
import dev.androidbroadcast.newsapi.NewsApi
import dev.androidbroadcast.newsapi.modules.ArticleDTO
import dev.androidbroadcast.newsapi.modules.ResponseDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import java.io.IOException

class ArticlesRepository(
    private val database: NewsDatabase,
    private val api: NewsApi,
) {
    fun getAll(): Flow<RequestResult<List<Article>>> {
        val cashedAllArticles: Flow<RequestResult.Success<List<ArticleDBO>>> = gelAllFromDatabase()

        val remoteArticles: Flow<RequestResult<*>> = getAllFormServer()


        cashedAllArticles.map {

        }
        return cashedAllArticles.combine(remoteArticles){

        }
    }

    private fun getAllFormServer() = flow { emit(api.everything()) }
        .map { result ->
            if (result.isSuccess) {
                val response: ResponseDTO<ArticleDTO> = result.getOrThrow()
                RequestResult.Success(response.articles)
            } else {
                RequestResult.Error(null)
            }
        }
        .filterIsInstance<RequestResult.Success<List<ArticleDTO>?>>()
        .map {  requestResult: RequestResult.Success<List<ArticleDTO>?> ->
            requestResult.requireData()
                .map { articleDto -> articleDto.toArticleDbo()}
                .let {RequestResult.Success<List<ArticleDBO>?>(it)}
        }.onEach { requestResult ->
            database.articlesDao.insert(requestResult.requireData())
        }

    private fun gelAllFromDatabase(): Flow<RequestResult.Success<List<ArticleDBO>>> {
        return database.articlesDao
            .getAll()
            .map { RequestResult.Success(it)}
    }

    suspend fun search(query: String): Flow<Article> {
        api.everything()
        TODO("NOT IMPLEMENTED")
    }
}

//with(requestResult.data) {
//    if(requestResult.data != null) {
//        requestResult.data.map { articleDto -> articleDto.toArticleDbo()}
//    } else {
//        null
//    }
//}.let {RequestResult.Success<List<ArticleDBO>?>(it)}
//
//checkNotNull(requestResult.data).map { articleDto -> articleDto.toArticleDbo()}

sealed class RequestResult<E>(internal val data: E?) {
    class InProgress<E>(data: E?) : RequestResult<E>(data)
    class Success<E>(data: E?) : RequestResult<E>(data)
    class Error<E>(data: E?) : RequestResult<E>(data)
}

internal fun <T: Any> RequestResult<T?>.requireData(): T = checkNotNull(data)
