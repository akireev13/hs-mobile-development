package com.example.testapp2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.testapp2.data.SearchResponse
import com.example.testapp2.data.UnsplashApiProvider
import com.example.testapp2.data.UnsplashItem
import com.example.testapp2.data.cb.UnsplashResult
import com.example.testapp2.shared.IMAGE_INTENT_KEY
import com.example.testapp2.shared.helpers.paintAsyncImage
import com.example.testapp2.ui.theme.TestApp2Theme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity(), UnsplashResult {

    private val provider = UnsplashApiProvider()
    private lateinit var photos: MutableState<List<UnsplashItem>>
    private lateinit var refreshing: MutableState<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // initial fetch
        provider.fetchImages(this)

        setContent {
            photos = remember { mutableStateOf(emptyList()) }
            refreshing = remember { mutableStateOf(false) }
            val searchText = rememberSaveable { mutableStateOf("") }

            val onSearchAction: (String) -> Unit = { search ->
                val trimmed = search.trim()
                refreshing.value = true
                if (trimmed.isEmpty()) {
                    provider.fetchImages(this@MainActivity)
                } else {
                    provider.searchImages(this@MainActivity, trimmed)
                }
            }

            TestApp2Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Test App 2", textAlign = TextAlign.Center) },
                            navigationIcon = { Icon(Icons.Default.Home, contentDescription = null) }
                        )
                    }
                ) { innerPadding ->

                    PullToRefreshBox(
                        isRefreshing = refreshing.value,
                        onRefresh = {
                            refreshing.value = true
                            val trimmed = searchText.value.trim()
                            refreshing.value = true
                            if (trimmed.isEmpty()) {
                                provider.fetchImages(this@MainActivity)
                            } else {
                                provider.searchImages(this@MainActivity, trimmed)
                            }
                        },
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {

                        LazyColumn(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {

                            // search bar at the top
                            item {
                                OutlinedTextField(
                                    value = searchText.value,
                                    onValueChange = { searchText.value = it },
                                    placeholder = { Text("Search") },
                                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                                    label = { Text("Search") },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                                    keyboardActions = KeyboardActions { onSearchAction(searchText.value) }
                                )
                            }

                            // fetched images
                            items(photos.value) { photo ->
                                FetchedItem(photo) { image ->
                                    val intent = Intent(this@MainActivity, ItemDetailsActivity::class.java).apply {
                                        putExtra(IMAGE_INTENT_KEY, image)
                                    }
                                    startActivity(intent)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // UnsplashResult implementation -----------------------------------------------------------

    override fun onSuccessFetchImages(images: List<UnsplashItem>) {
        photos.value = images
        refreshing.value = false
    }

    override fun onFailFetchImages() {
        refreshing.value = false
        // TODO: user‑visible error handling
    }

    override fun onSuccessSearchImages(result: SearchResponse) {
        photos.value = result.results
        refreshing.value = false
    }

    override fun onFailSearchImages() {
        refreshing.value = false
        // TODO: user‑visible error handling
    }
}

// ---------------------------------------------------------------------------------------------
// Composable that renders a single photo card
// ---------------------------------------------------------------------------------------------

@Composable
fun FetchedItem(image: UnsplashItem, onClick: (UnsplashItem) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable { onClick(image) }
    ) {
        Image(
            painter = paintAsyncImage(LocalContext.current, image.urls?.regular),
            contentDescription = image.description,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        )
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            Text(text = image.description ?: "No description", fontWeight = FontWeight.Bold)
            Text(text = image.user?.name ?: "No name")
        }
    }
}
