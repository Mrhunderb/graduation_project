package com.example.hrm.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.rememberAsyncImagePainter

@Composable
fun ZoomableImageViewer(imageUri: String?) {
    var showDialog by remember { mutableStateOf(false) }

    Image(
        painter = rememberAsyncImagePainter(imageUri),
        contentDescription = "点击放大",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { showDialog = true },
        contentScale = ContentScale.Crop
    )

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                var scale by remember { mutableFloatStateOf(1f) }
                var offset by remember { mutableStateOf(Offset.Zero) }

                val state = rememberTransformableState { zoomChange, offsetChange, _ ->
                    scale *= zoomChange
                    offset += offsetChange
                }

                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = "全屏图片",
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            scaleX = scale.coerceIn(1f, 5f),
                            scaleY = scale.coerceIn(1f, 5f),
                            translationX = offset.x,
                            translationY = offset.y
                        )
                        .transformable(state)
                        .clickable { showDialog = false },
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}
