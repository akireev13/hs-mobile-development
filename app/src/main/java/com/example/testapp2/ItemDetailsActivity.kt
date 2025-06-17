package com.example.testapp2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.example.testapp2.data.UnsplashItem
import com.example.testapp2.shared.IMAGE_INTENT_KEY
import com.example.testapp2.shared.helpers.paintAsyncImage
import com.example.testapp2.ui.theme.TestApp2Theme

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

        val imageData = intent.extras!!.get(IMAGE_INTENT_KEY) as UnsplashItem
        setContent {
            TestApp2Theme {
                Scaffold { innerPadding ->
                    ItemDetailsScreen(imageData, Modifier.padding(innerPadding), onClickPhoto)
                }
            }
        }
    }
}

@Composable
fun ItemDetailsScreen(
    imageData: UnsplashItem,
    modifier: Modifier = Modifier,
    onClickPhoto: (String?) -> Unit
) {

    Column(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clickable { onClickPhoto(imageData.urls?.regular) }
        ) {
            Image(
                painter = paintAsyncImage(LocalContext.current, imageData.urls?.regular),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(50)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Icon(Icons.Filled.Place, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface)
                Spacer(Modifier.width(4.dp))
                Text(
                    imageData.user?.location ?: "Unknown location",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                painter = paintAsyncImage(LocalContext.current, imageData.user?.profile_image?.medium),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                imageData.user?.name ?: "Unknown user",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(Modifier.weight(1f))
            IconButton(onClick = {  }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
            IconButton(onClick = {  }) {
                Icon(Icons.Filled.FavoriteBorder, contentDescription = null)
            }
            IconButton(onClick = {  }) {
                Icon(Icons.Filled.Star, contentDescription = null)
            }
        }

        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                MetadataItem("Camera", "NIKON D3200")
                Spacer(Modifier.height(8.dp))
                MetadataItem("Focal Length", "18.0 mm")
                Spacer(Modifier.height(8.dp))
                MetadataItem("ISO", "100")
            }
            Column(modifier = Modifier.weight(1f)) {
                MetadataItem("Aperture", "f/5.0")
                Spacer(Modifier.height(8.dp))
                MetadataItem("Shutter Speed", "1/125 s")
                Spacer(Modifier.height(8.dp))
                MetadataItem("Dimensions", "3906 × 4882")
            }
        }

        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem("Views", "666K" )
            StatItem("Downloads", "66 K")
            StatItem("Likes", imageData.likes.toString())
        }

        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AssistChip(onClick = {}, label = { Text("barcelona") })
            AssistChip(onClick = { }, label = { Text("spain") })
        }
    }
}

@Composable
fun MetadataItem(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium))
    }
}

@Composable
fun StatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
        Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
