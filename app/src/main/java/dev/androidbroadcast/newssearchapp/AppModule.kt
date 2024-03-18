package dev.androidbroadcast.newssearchapp

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.androidbroadcast.news.database.NewsDatabase
import dev.androidbroadcast.newsapi.NewsApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return NewsApi(
           baseUrl = BuildConfig.NEWS_API_BASE_URL,
           apikey = BuildConfig.NEWS_API_KEY
        )
    }

    @Provides
    @Singleton
    fun provideNewsdatabase(@ApplicationContext context: Context): NewsDatabase {
        return NewsDatabase(context)
    }
}