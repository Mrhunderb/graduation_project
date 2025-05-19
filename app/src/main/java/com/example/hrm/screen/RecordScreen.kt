package com.example.hrm.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hrm.component.RecordItem
import com.example.hrm.db.HealthViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(
    navController: NavController,
    viewModel: HealthViewModel = viewModel()
) {
    val records = viewModel.record.collectAsState()
    val formatter = remember {
        SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
    }

    var showDeleteDialog by remember { mutableStateOf(false) }
    var selectedRecord by remember { mutableStateOf<Long?>(null) }

    if (showDeleteDialog && selectedRecord != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("确认删除") },
            text = { Text("确定要删除这条记录吗？此操作不可撤销。") },
            confirmButton = {
                Button(onClick = {
                    selectedRecord?.let { viewModel.deleteRecord(it) }
                    showDeleteDialog = false
                    selectedRecord = null
                }) {
                    Text("确认删除")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = {
                    showDeleteDialog = false
                    selectedRecord = null
                }) {
                    Text("取消")
                }
            }
        )
    }
    if (records.value.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(26.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "暂无体检记录",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(records.value.size) { index ->
            val record = records.value[index]
            val date = formatter.format(record.date)
            RecordItem(
                record = date + "体检报告",
                hospital = record.hospital,
                onClick = {
                    navController.navigate("add_select/${record.id}/true")
                },
                onLongClick = {
                    selectedRecord = record.id // 假设 HealthRecord 实体有 id 字段
                    showDeleteDialog = true
                }
            )
        }
    }
}
