package com.example.testapp2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testapp2.data.UnsplashApiProvider
import com.example.testapp2.data.UnsplashItem
import com.example.testapp2.data.cb.UnsplashResult
import com.example.testapp2.ui.theme.TestApp2Theme

class MainActivity : ComponentActivity(), UnsplashResult {

    lateinit var photos :MutableState<List<UnsplashItem>>

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val provider = UnsplashApiProvider()
        provider.fetchImages(this)

        setContent {

            photos = remember { mutableStateOf(emptyList()) }

            TestApp2Theme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text("Test App 2", textAlign = TextAlign.Center)
                                          },
                            navigationIcon = { Icon(Icons.Default.Home, contentDescription = null) })
                    }

                ) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),

                        ) {

                        val onClick = { resId: Int ->
                            val intent = Intent(
                                this@MainActivity,
                                ItemDetailsActivity::class.java
                            )
                            intent.putExtra("resId", resId)
                            startActivity(intent)
                        }
                        item {
                            val search = remember { mutableStateOf("") }

                            OutlinedTextField(
                                value = search.value,
                                onValueChange = { value: String -> search.value = value},
                                placeholder = { Text("Search")},
                                leadingIcon = { Icon(Icons.Default.Search,
                                    contentDescription = null) },
                                label = { Text("Search") },
                            modifier = Modifier.fillMaxWidth()
                            )
                        }
                        items(photos.value) { photo ->
                             FetchedItem(photo)
                        }
                    }
                }
            }

        }
    }

    override fun onSuccess(images: List<UnsplashItem>) {
        this.photos.value = images
    }

    override fun onFail() {
        TODO("Not yet implemented")
    }
}

@Composable
fun CardItem(drawableId: Int, onClick: (Int) -> Unit) {
    Card(shape = RoundedCornerShape(16.dp),
        modifier = Modifier.clickable(onClick = {
            onClick(drawableId)
        })
    ) {
    Image(
        painter = painterResource(drawableId),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
    )
    }
}

@Composable
fun FetchedItem(image: UnsplashItem) {
    Card(shape = RoundedCornerShape(16.dp)
        , modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)

        )
     {
        Column (verticalArrangement = Arrangement.Bottom, modifier = Modifier.padding(16.dp).fillMaxHeight()) {
            Text(text = image.description ?: "No description", fontWeight = FontWeight.Bold)
            Text(text = image.user?.name ?: "No name")
        }
     }
}