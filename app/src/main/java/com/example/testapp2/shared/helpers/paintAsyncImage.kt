package com.example.testapp2.shared.helpers

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest

@Composable
fun paintAsyncImage(context: Context, url: String?): Painter {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(url)
            .build()
    )
    return painter
}