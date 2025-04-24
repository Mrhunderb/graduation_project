package com.example.hrm.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hrm.component.RecordItem

@Composable
fun RecordScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(3) { index ->
            RecordItem(
                record = "202${index}年${index}月${index}日的体检报告",
                hospital = "北京协和医院",
                onClick = {
                    // show click
                },
            )
        }
    }
}