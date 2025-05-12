package com.example.hrm.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
    navController: NavController?,
    viewModel: HealthViewModel = viewModel()
) {
    val records = viewModel.record.collectAsState()
    val formatter = remember {
        SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
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
                onClick = {}
            )
        }
    }
}