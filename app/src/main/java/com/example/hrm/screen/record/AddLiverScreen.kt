package com.example.hrm.screen.record

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hrm.db.HealthViewModel
import com.example.hrm.db.entity.LiverData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLiverScreen(
    navController: NavController,
    id: Long,
    viewModel: HealthViewModel = viewModel()
) {
    var ast by remember { mutableStateOf("") }
    var alt by remember { mutableStateOf("") }

    var field = listOf(
        "谷丙转氨酶 (AST)" to ast,
        "谷草转氨酶 (ALT)" to alt,
    )
    var showBackConfirmDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }
    var isSubmitting by remember { mutableStateOf(false) }
    var isModified by remember { mutableStateOf(false) }
    var data = remember { mutableStateOf<LiverData?>(null) }

    LaunchedEffect(id) {
        viewModel.getLiverDataById(
            id,
            onComplete = {
                if (it != null) {
                    isModified = true
                    data.value = it
                    ast = it.ast?.toString() ?: ""
                    alt = it.alt?.toString() ?: ""
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
                        Text(if (isModified) "修改肝功能检查" else "添加肝功能检查")
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
                    .padding(padding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("肝功能检查", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.padding(16.dp))
                field.forEach { (label, value) ->
                    OutlinedTextField(
                        value = value,
                        onValueChange = {
                            when (label) {
                                "谷丙转氨酶 (AST)" -> ast = it
                                "谷草转氨酶 (ALT)" -> alt = it
                            }
                        },
                        label = { Text(label) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
                Spacer(modifier = Modifier.padding(16.dp))
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        isSubmitting = true
                        var liverData = LiverData(
                            sessionId = id,
                            ast = ast.toFloatOrNull(),
                            alt = alt.toFloatOrNull()
                        )
                        if (isModified) {
                            liverData = data.value!!.copy(
                                ast = ast.toFloatOrNull(),
                                alt = alt.toFloatOrNull()
                            )
                            viewModel.updateLiverData(liverData)
                        } else {
                            viewModel.addLiverData(liverData)
                        }
                        navController.popBackStack()
                    },
                    enabled = !isSubmitting
                ) {
                    Text("提交")
                }
            }
        }
    }
}