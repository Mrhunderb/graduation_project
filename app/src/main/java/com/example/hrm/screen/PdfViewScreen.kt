package com.example.hrm.screen

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.github.barteksc.pdfviewer.PDFView
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PdfViewScreen(
    uri: Uri,
    navController: NavController,
) {
    // Create a launcher for sharing the generated PDF
    val shareLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { /* No action needed after sharing */ }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("健康报告PDF") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                        File(uri.path ?: "").delete()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            shareFile(uri, shareLauncher)
                        }
                    ) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = "分享"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        AndroidView(
            factory = { context ->
                PDFView(context, null).apply {
                    fromUri(uri)
                        .enableSwipe(true)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .spacing(10)
                        .load()
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

fun shareFile(uri: Uri, launcher: androidx.activity.result.ActivityResultLauncher<Intent>) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    launcher.launch(Intent.createChooser(intent, "分享健康报告"))
}