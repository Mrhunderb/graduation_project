package com.example.hrm.screen.record

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.hrm.db.HealthViewModel
import com.example.hrm.db.entity.CtScan
import com.example.hrm.db.entity.Ecg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddXrayScreen(
    navController: NavController,
    id: Long,
    viewModel: HealthViewModel = viewModel()
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var diagnosis by remember { mutableStateOf("") }
    var isChanged by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        selectedImageUri = it
        isChanged = true
    }

    var showBackConfirmDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var isModified by remember { mutableStateOf(false) }
    var data by remember { mutableStateOf<CtScan?>(null) }

    LaunchedEffect(id) {
        viewModel.getCtScanDataById(
            id,
            onComplete = {
                if (it != null) {
                    isModified = true
                    data = it
                    selectedImageUri = it.imagePath?.toUri()
                    diagnosis = it.result ?: ""
                }
                isLoading = false
            }
        )
    }

    if (showBackConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showBackConfirmDialog = false },
            title = { Text("确认返回") },
            text = { Text("如果现在返回，当前填写的数据将不会保存，确定要返回吗？") },
            confirmButton = {
                Button(onClick = {
                    showBackConfirmDialog = false
                    navController.popBackStack()
                }) {
                    Text("确认返回")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showBackConfirmDialog = false }) {
                    Text("取消")
                }
            }
        )
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("新增CT") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(height = 250.dp, width = 300.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.LightGray)
                        .clickable {
                            launcher.launch("image/*")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedImageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(selectedImageUri),
                            contentDescription = "CT 图像",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Text("点击选择 CT 图像")
                    }
                }


                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = diagnosis,
                    onValueChange = {
                        diagnosis = it
                    },
                    label = { Text("请输入医师诊断结果") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    singleLine = false,
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if (isModified) {
                            var path = if (isChanged) {
                                viewModel.saveImage(selectedImageUri!!)
                            } else {
                                data?.imagePath
                            }
                            var ct = data?.copy(
                                imagePath = path,
                                result = diagnosis
                            )
                            ct?.let { viewModel.updateCtScanData(it) }
                        } else {
                            var ct = CtScan(
                                sessionId = id,
                                date = System.currentTimeMillis(),
                                imagePath = viewModel.saveImage(selectedImageUri!!),
                                result = diagnosis
                            )
                            viewModel.addCtScanData(ct)
                        }
                        navController.popBackStack()
                    },
                    enabled = selectedImageUri != null
                ) {
                    Text("上传 CT 图像")
                }
            }
        }
    }
}