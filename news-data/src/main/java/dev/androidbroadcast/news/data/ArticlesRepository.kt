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

    private fun getAllFormServer(): Flow<RequestResult<*>> {
      return flow { emit(api.everything()) }
          .map { result ->
              if (result.isSuccess) {
                  val response: ResponseDTO<ArticleDTO> = result.getOrThrow()
                  RequestResult.Success(response.articles)
              } else {
                  RequestResult.Error(null)
              }
          }
          .onEach { requestResult ->
              if (requestResult is RequestResult.Success) {
                  saveNetResponseToCache(checkNotNull(requestResult.data))
              }
          }
    }

    private suspend fun saveNetResponseToCache(data: List<ArticleDTO>) {
        val dbos = data.map { articleDto -> articleDto.toArticleDbo()  }
        database.articlesDao.insert(dbos)
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

sealed class RequestResult<E>(internal val data: E) {
    class InProgress<E>(data: E) : RequestResult<E>(data)
    class Success<E>(data: E) : RequestResult<E>(data)
    class Error<E>(data: E) : RequestResult<E>(data)
}

internal fun <T: Any> RequestResult<T?>.requireData(): T = checkNotNull(data)

internal fun <I, O> RequestResult<I>.map(mapper: (I) -> O): RequestResult<O> {
    val outData = mapper(data)
    return when(this) {
        is RequestResult.Success -> RequestResult.Success(outData)
        is RequestResult.Error -> RequestResult.Error(outData)
        is RequestResult.InProgress -> RequestResult.InProgress(outData)
    }
}
