package com.example.testapp2.features.Gallery.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.testapp2.core.util.paintAsyncImage
import com.example.testapp2.data.unsplash.UnsplashItem

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