package com.example.hrm.screen.record

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hrm.R
import com.example.hrm.component.DatePickerField
import com.example.hrm.db.HealthViewModel
import com.example.hrm.db.entity.HealthRecord
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordSelectScreen(
    navController: NavController,
    id : Long,
    isModify : Boolean = false,
    viewModel: HealthViewModel = viewModel(),
) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    var date by remember { mutableLongStateOf(0) }
    var hostpital by remember { mutableStateOf("") }
    val formatter = remember {
        SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
    }

    var isLoading by remember { mutableStateOf(true) }
    var healthRecord by remember { mutableStateOf<HealthRecord?>(null) }

    LaunchedEffect(id) {
        viewModel.getRecordById(
            id,
            onComplete = {
                if (it != null) {
                    date = it.date
                    hostpital = it.hospital
                    healthRecord = it
                }
                isLoading = false
            }
        )
    }

    val items = listOf<Triple<Int, String, String>>(
        Triple(R.drawable.general, "常规检查", "general"),
        Triple(R.drawable.blood, "血常规", "blood"),
        Triple(R.drawable.urine, "尿常规", "urine"),
        Triple(R.drawable.liver, "肝功能", "liver"),
        Triple(R.drawable.ecg, "心电图", "ecg"),
        Triple(R.drawable.xray, "X光", "xray"),
    )

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("确认返回") },
            text = { Text("如果现在返回，当前添加的记录将被丢弃，确定要返回吗？") },
            confirmButton = {
                Button(onClick = {
                    showConfirmDialog = false
                    if (!isModify) {
                        viewModel.deleteRecord(id)
                    }
                    navController.navigate("home") {
                        popUpTo("home") {
                            inclusive = true
                        }
                    }
                }) {
                    Text("确认返回")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showConfirmDialog = false }) {
                    Text("取消")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isModify) {
                        Text("修改体检报告")
                    } else {
                        Text("添加体检报告")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = { showConfirmDialog = true }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { padding ->
            // 3行 × 2列 = 6项
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                for (rowIndex in 0 until 3) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        for (colIndex in 0 until 2) {
                            val index = rowIndex * 2 + colIndex
                            val item = items[index]
                            GridButton(
                                icon = painterResource(id = item.first),
                                label = item.second,
                                onClick = {
                                    var route = "add_" + item.third + "/$id"
                                    navController.navigate(route)
                                },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
                if (isModify) {
                    OutlinedTextField(
                        value = hostpital,
                        onValueChange = {
                            hostpital = it
                            healthRecord?.hospital = it },
                        label = { Text("请输入体检医院的名称") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("体检日期")
                        DatePickerField(
                            initialDate = formatter.format(Date(date)),
                            onDateSelected = {
                                healthRecord?.date = it.time
                            }
                        )
                    }
                }

                Button(
                    onClick = {
                        if (isModify) {
                            viewModel.updateRecord(healthRecord!!)
                        }
                        navController.navigate("home") {
                            popUpTo("home") {
                                inclusive = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("保存报告")
                }
            }
        }
    }
}

@Composable
fun GridButton(
    modifier: Modifier = Modifier,
    icon: Painter,
    label: String,
    isFinished: Boolean = false,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .aspectRatio(1f), // 保持按钮为正方形
        shape = RoundedCornerShape(16.dp),
        colors = if (isFinished) {
            ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        } else {
            ButtonDefaults.outlinedButtonColors()
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = icon,
                contentDescription = label,
                modifier = Modifier.size(45.dp),
                tint = if (isFinished)
                    MaterialTheme.colorScheme.primary
                else
                    LocalContentColor.current
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                style = if (isFinished)
                    MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                else
                    MaterialTheme.typography.bodyMedium
            )

            if (isFinished) {
                Spacer(modifier = Modifier.height(4.dp))
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "已完成",
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
