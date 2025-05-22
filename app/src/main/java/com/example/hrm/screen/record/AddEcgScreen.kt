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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.example.hrm.db.HealthViewModel
import com.example.hrm.db.entity.Ecg
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEcgScreen(
    navController: NavController,
    id: Long,
    viewModel: HealthViewModel = viewModel()
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var diagnosis by remember { mutableStateOf("") }
    var heartRate by remember { mutableStateOf("") }
    var qrs by remember { mutableStateOf("") }
    var prInterval by remember { mutableStateOf("") }
    var qtInterval by remember { mutableStateOf("") }
    var qtcInterval by remember { mutableStateOf("") }
    var changePath by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        selectedImageUri = it
        changePath = true
    }

    var showBackConfirmDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var isModified by remember { mutableStateOf(false) }
    var data by remember { mutableStateOf<Ecg?>(null) }

    LaunchedEffect(id) {
        viewModel.getEcgDataById(
            id,
            onComplete = {
                if (it != null) {
                    isModified = true
                    data = it
                    heartRate = it.heartRate?.toString() ?: ""
                    qrs = it.qrs?.toString() ?: ""
                    prInterval = it.prInterval?.toString() ?: ""
                    qtInterval = it.qtInterval?.toString() ?: ""
                    qtcInterval = it.qtcInterval?.toString() ?: ""
                    diagnosis = it.result?.toString() ?: ""
                    selectedImageUri = it.imagePath?.toUri()
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
                    title = {
                        Text(text = if (isModified) "修改心电图" else "添加心电图")
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            showBackConfirmDialog = true
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
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
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
                            contentDescription = "心电图",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Text("点击选择心电图")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = heartRate,
                    onValueChange = {
                        heartRate = it
                    },
                    label = { Text("心率（bpm）") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                OutlinedTextField(
                    value = qrs,
                    onValueChange = {
                        qrs = it
                    },
                    label = { Text("QRS波群持续时间（ms）") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                OutlinedTextField(
                    value = prInterval,
                    onValueChange = {
                        prInterval = it
                    },
                    label = { Text("P-R间期（ms）") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = qtInterval,
                        onValueChange = {
                            qtInterval = it
                        },
                        label = { Text("qt（ms）") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                    )

                    Text("/")

                    OutlinedTextField(
                        value = qtcInterval,
                        onValueChange = {
                            qtcInterval = it
                        },
                        label = { Text("QTc（ms）") },
                        modifier = Modifier
                            .weight(1f)
                            .padding(16.dp)
                    )
                }

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
                            var path = if (changePath) {
                                viewModel.saveImage(selectedImageUri!!)
                            } else {
                                data?.imagePath
                            }
                            var ecg = data?.copy(
                                heartRate = heartRate.toIntOrNull(),
                                qrs = qrs.toIntOrNull(),
                                prInterval = prInterval.toIntOrNull(),
                                qtInterval = qtInterval.toIntOrNull(),
                                qtcInterval = qtcInterval.toIntOrNull(),
                                imagePath = path,
                                result = diagnosis
                            )
                            ecg?.let { viewModel.updateEcgData(it) }
                        } else {
                            var ecg = Ecg(
                                sessionId = id,
                                heartRate = heartRate.toIntOrNull(),
                                qrs = qrs.toIntOrNull(),
                                prInterval = prInterval.toIntOrNull(),
                                qtInterval = qtInterval.toIntOrNull(),
                                qtcInterval = qtcInterval.toIntOrNull(),
                                imagePath = viewModel.saveImage(selectedImageUri!!),
                                result = diagnosis
                            )
                            viewModel.addEcgData(ecg)
                        }
                        navController.popBackStack()
                    },
                    enabled = selectedImageUri != null
                ) {
                    Text("上传心电图")
                }
            }
        }
    }
}