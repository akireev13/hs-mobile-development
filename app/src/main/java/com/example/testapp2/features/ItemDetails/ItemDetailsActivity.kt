package com.example.testapp2.features.ItemDetails

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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

