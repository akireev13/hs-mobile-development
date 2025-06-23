package com.example.testapp2.features.Gallery.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.testapp2.core.designsystem.TestApp2Theme
import com.example.testapp2.data.unsplash.UnsplashItem
import com.example.testapp2.features.Gallery.GalleryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(onImageClick: (UnsplashItem) -> Unit,  viewModel: GalleryViewModel = GalleryViewModel()) {
    val photos = viewModel.photos.value
    val refreshing = viewModel.refreshing.value
    val search = rememberSaveable { mutableStateOf("") }

    fun onSearchAction(query: String) {
        val trimmed = query.trim()

        if (trimmed.isEmpty()) {
            viewModel.fetchImages()
        } else {
            viewModel.searchImages(trimmed)
        }

    }

    LaunchedEffect(Unit) {
        viewModel.fetchImages()
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
                isRefreshing = refreshing,
                onRefresh = {

                    onSearchAction(search.value)

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
                            value = search.value,
                            onValueChange = { search.value = it },
                            placeholder = { Text("Search") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                            label = { Text("Search") },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions { onSearchAction(search.value) }
                        )
                    }

                    // fetched images
                    items(photos) { photo ->
                        FetchedItem(photo, onImageClick)
                    }
                }
            }
        }
    }
}