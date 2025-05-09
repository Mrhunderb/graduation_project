package com.example.hrm.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hrm.component.RecordItem
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordScreen(
    navController: NavController?
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(3) { index ->
            RecordItem(
                record = "202${index}年${index}1月${index}1日体检报告",
                hospital = "北京协和医院",
                onClick = {
                    navController?.navigate("add")
                },
            )
        }
    }
}