package com.example.testapp2

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
import androidx.compose.ui.res.painterResource
import com.example.testapp2.shared.helpers.paintAsyncImage
import com.example.testapp2.ui.theme.TestApp2Theme

class FullScreenPhotoActivity : ComponentActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()
            val imageUrl = intent.getStringExtra("url")
            setContent {
                TestApp2Theme {
                    Scaffold { innerPadding ->
                        Image(
                            modifier = Modifier
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