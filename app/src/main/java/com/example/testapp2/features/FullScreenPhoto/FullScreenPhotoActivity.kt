package com.example.testapp2.features.FullScreenPhoto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.testapp2.core.designsystem.TestApp2Theme
import com.example.testapp2.core.util.paintAsyncImage

class FullScreenPhotoActivity : ComponentActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            val imageUrl = intent.getStringExtra("url")
            setContent {
                TestApp2Theme {
                    Scaffold { innerPadding ->
                        Image(
                            modifier = Modifier.Companion
                                .padding(innerPadding)
                                .fillMaxSize(),
                            painter = paintAsyncImage(LocalContext.current, imageUrl),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }