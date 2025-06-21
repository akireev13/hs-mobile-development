package com.example.testapp2.features

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.testapp2.core.IMAGE_INTENT_KEY
import com.example.testapp2.data.unsplash.UnsplashItem
import com.example.testapp2.features.Gallery.ui.GalleryScreen
import com.example.testapp2.features.Gallery.GalleryViewModel
import com.example.testapp2.features.ItemDetails.ItemDetailsActivity

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    val viewModel: GalleryViewModel by viewModels()

    val onImageClick: (UnsplashItem) -> Unit = { image ->
        val intent = Intent(this, ItemDetailsActivity::class.java).apply {
            putExtra("id", image.id)
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GalleryScreen(viewModel, onImageClick)
        }
    }
}

