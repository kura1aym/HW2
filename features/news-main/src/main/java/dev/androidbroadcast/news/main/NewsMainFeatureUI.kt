package dev.androidbroadcast.news.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NewsMainScreen() {
    NewsMainScreen(viewModel = viewModel())
}


@Composable
internal fun NewsMainScreen(viewModel: NewsMainViewModel) {
    val state by viewModel.state.collectAsState()
    when(val currentState = state) {
        is State.Success -> Articles(currentState.articles)
        is State.Error -> ArticlesWithError(currentState.articles)
        is State.Loading -> ArticlesDuringUpdate(currentState.articles)
        State.None -> NewsEmpty()
    }

}

@Composable
internal fun ArticlesWithError(articles: List<ArticleUI>?) {
    Column {
        Box(
            Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.error),
            contentAlignment = Alignment.Center)
        {
            Text(text = "Error during update", color = MaterialTheme.colorScheme.onError)
        }
        if (articles != null) {
            Articles(articles = articles)
        }
    }
}

@Composable
@Preview
internal fun ArticlesDuringUpdate(
    @PreviewParameter(ArticlePreviewProvider::class, limit = 1) articles: List<ArticleUI>?,
) {
   Column {
       Box(
           Modifier
               .padding(8.dp)
               .fillMaxWidth(),
           contentAlignment = Alignment.Center) {
           CircularProgressIndicator()
       }
       if (articles != null) {
           Articles(articles = articles)
       }
   }

}

@Composable
internal fun NewsEmpty() {
    Box(contentAlignment = Alignment.Center) {
        Text("No news")
    }
}

@Preview
@Composable
private fun Articles(
    @PreviewParameter(ArticlePreviewProvider::class, limit = 1) articles: List<ArticleUI>
) {
    LazyColumn {
        items(articles) {article ->
            key(article.id) {
                Article(article)
            }
        }
    }
}

@Preview
@Composable
internal fun Article(
    @PreviewParameter(ArticlesPreviewProvider::class) article: ArticleUI
) {
    Column(modifier = Modifier.size(8.dp)) {
        Text(text = article.title ?: "NO TITLE", style = MaterialTheme.typography.headlineMedium, maxLines = 1)

        Spacer(modifier = Modifier.size(4.dp))
        Text(text = article.description, style = MaterialTheme.typography.bodyMedium, maxLines = 3)

    }
}

private class ArticlePreviewProvider : PreviewParameterProvider<ArticleUI> {
    override val values = sequenceOf(
        ArticleUI(
            1,
            "Android Studio Iguana is Stable!",
            "New stable version on Android IDE has been release",
            imageUrl = null,
            url = "",
        ),
        ArticleUI(
            2,
            "Gemini 1.5 Release",
            "Upgraded version of Google AI is available",
            imageUrl = null,
            url = "",
        ),
        ArticleUI(
            3,
            "Shape animation (10 min)",
            "How to use shape transform animations",
            imageUrl = null,
            url = "",
        ),
    )
}

private class ArticlesPreviewProvider : PreviewParameterProvider<List<ArticleUI>> {

    private val articleProvider = ArticlePreviewProvider()

    override val values = sequenceOf(
        articleProvider.values
            .toList()
    )

}
