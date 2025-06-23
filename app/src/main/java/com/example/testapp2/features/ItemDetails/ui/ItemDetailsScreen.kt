package com.example.testapp2.features.itemdetails.ui

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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.testapp2.core.designsystem.TestApp2Theme
import com.example.testapp2.core.util.paintAsyncImage
import com.example.testapp2.data.unsplash.UnsplashDetailedItem
import com.example.testapp2.features.ItemDetails.ui.MetadataItem
import com.example.testapp2.features.ItemDetails.ui.StatItem
import com.example.itemdetails.ItemDetailsViewModel

@Composable
fun ItemDetailsRoute(
    id: String,
    onClickPhoto: (String?) -> Unit,
    viewModel: ItemDetailsViewModel = ItemDetailsViewModel()
) {
    val state by viewModel.state.observeAsState(ItemDetailsViewModel.UiState.Loading)

    LaunchedEffect(id) { viewModel.loadPhoto(id) }

    when (val ui = state) {
        is ItemDetailsViewModel.UiState.Success -> ItemDetailsScreen(ui.photo, onClickPhoto = onClickPhoto)
        ItemDetailsViewModel.UiState.Error -> ErrorScreen { viewModel.loadPhoto(id) }
        else -> LoadingScreen()
    }
}

@Composable
fun ItemDetailsScreen(
    imageData: UnsplashDetailedItem,
    onClickPhoto: (String?) -> Unit
) {
    TestApp2Theme {
        Scaffold { innerPadding ->

    Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
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
                    imageData.location?.city ?: "Unknown location",
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
            IconButton(onClick = { }) { Icon(Icons.Default.Add, contentDescription = null) }
            IconButton(onClick = { }) { Icon(Icons.Filled.FavoriteBorder, contentDescription = null) }
            IconButton(onClick = { }) { Icon(Icons.Filled.Star, contentDescription = null) }
        }

        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                MetadataItem("Camera", imageData.exif?.name ?: "-")
                Spacer(Modifier.height(8.dp))
                MetadataItem("Focal Length", imageData.exif?.focal_length ?: "-")
                Spacer(Modifier.height(8.dp))
                MetadataItem("ISO", imageData.exif?.iso?.toString() ?: "-")
            }
            Column(modifier = Modifier.weight(1f)) {
                MetadataItem("Aperture", "f/${imageData.exif?.aperture ?: "-"}")
                Spacer(Modifier.height(8.dp))
                MetadataItem("Shutter Speed", imageData.exif?.exposure_time ?: "-")
                Spacer(Modifier.height(8.dp))
                MetadataItem("Dimensions", "${imageData.width} × ${imageData.height}")
            }
        }

        HorizontalDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatItem("Views",  "-")
            StatItem("Downloads", imageData.downloads?.toString() ?: "-")
            StatItem("Likes", imageData.likes.toString())
        }

        HorizontalDivider()

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            imageData.tags?.take(10)?.forEach {
                AssistChip(onClick = { }, label = { Text(it.title ?: "Not provided") })
            }
        }
    }
        }
    }
}

@Composable
fun LoadingScreen() = Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    CircularProgressIndicator()
}

@Composable
fun ErrorScreen(onRetry: () -> Unit) = Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    Button(onClick = onRetry) { Text("Retry") }
}
