package com.example.testapp2.features.ItemDetails

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.testapp2.core.IMAGE_INTENT_KEY
import com.example.testapp2.core.designsystem.TestApp2Theme
import com.example.testapp2.data.unsplash.UnsplashItem
import com.example.testapp2.features.FullScreenPhoto.FullScreenPhotoActivity
import com.example.testapp2.features.itemdetails.ui.ItemDetailsRoute

class ItemDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val onClickPhoto: (String?) -> Unit = { url ->
            Intent(this, FullScreenPhotoActivity::class.java).apply {
                putExtra("url", url)
                startActivity(this)
            }
        }

        val id = intent.getStringExtra("id")!!
        setContent {
            ItemDetailsRoute(id, onClickPhoto = onClickPhoto)
        }
    }
}

