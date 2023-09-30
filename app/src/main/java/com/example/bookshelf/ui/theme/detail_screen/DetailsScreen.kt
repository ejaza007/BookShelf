package com.example.bookshelf.ui.theme.detail_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.bookshelf.AppDestinations
import com.example.bookshelf.BookshelfNavHost
import com.example.bookshelf.R
import com.example.bookshelf.model.Book
import com.example.bookshelf.ui.theme.screens.components.ErrorScreen
import com.example.bookshelf.ui.theme.screens.components.LoadingScreen
import com.example.bookshelf.ui.theme.screens.components.MyTopAppBar


@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel,
    retryAction: () -> Unit,
) {
    val uiStateDet = viewModel.uiStateDetail.collectAsState().value

    when (uiStateDet) {

        is DetailsUiState.Loading -> {
            LoadingScreen()
        }
        is DetailsUiState.Error -> {
            ErrorScreen(
                retryAction = retryAction
            )
        }
        is DetailsUiState.Success -> {
            BookDetails(uiStateDet.bookItem)
        }
    }

}


@Composable
fun BookDetails(book: Book) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        shape = RoundedCornerShape(8.dp),
        //border = BorderStroke(2.dp, Color.Green),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Title: " + book.volumeInfo.title,
                style = MaterialTheme.typography.titleLarge
            )
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.volumeInfo.imageLinks?.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = book.volumeInfo.title,
                contentScale = ContentScale.FillWidth,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.book_subtitle, book.volumeInfo.subtitle),
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = stringResource(R.string.book_authors, book.volumeInfo.allAuthors()),
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.book_price, book.saleInfo.getPrice2),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "country: " + book.saleInfo.country,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "listPrice: " + book.saleInfo.getPrice2,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "description: " + book.volumeInfo.description,
                style = MaterialTheme.typography.bodyMedium
            )



        }
    }
}

