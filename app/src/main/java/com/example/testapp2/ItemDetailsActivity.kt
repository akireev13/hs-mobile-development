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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.testapp2.ui.theme.TestApp2Theme

class ItemDetailsActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val onClick = { resId: Int ->
            val intent = Intent(
                this@ItemDetailsActivity,
                FullScreenPhotoActivity::class.java
            )
            intent.putExtra("resId", resId)
            startActivity(intent)
        }
        val resId = intent.getIntExtra("resId", 0)
        setContent {
            TestApp2Theme {
                Scaffold { innerPadding ->
                    ItemDetailsScreen(resId, modifier = Modifier.padding(innerPadding), onClick)
                }
            }
        }
    }
}

@Composable
fun ItemDetailsScreen(resId: Int, modifier: Modifier = Modifier, onClick: (Int) -> Unit) {
    Column (modifier = modifier.fillMaxSize()) {
    Image(painter = painterResource(resId), contentDescription = null, modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = {onClick(resId)})
        , contentScale = ContentScale.FillWidth)
        Row(modifier = Modifier.padding(16.dp).fillMaxSize()) {
            Column(modifier = Modifier.weight(1f)) {
                CardItem("Item 1", "Description 1")
                CardItem("Item 2", "Description 2")
                CardItem("Item 3", "Description 3")

            }
            Column(modifier = Modifier.weight(1f)) {
                CardItem("Item 4", "Description 4")
                CardItem("Item 5", "Description 5")
                CardItem("Item 6", "Description 6")
            }
        }
        HorizontalDivider()
        Row (horizontalArrangement = Arrangement.SpaceEvenly) {
            CardItem("Item 7", "Description 7")
            CardItem("Item 8", "Description 8")
            CardItem("Item 9", "Description 9")

        }
    }
}

@Composable
fun CardItem(name: String, description: String) {
    Column {
        Text(text = name, fontWeight = FontWeight.Bold)
        Text(text = description)
    }
}