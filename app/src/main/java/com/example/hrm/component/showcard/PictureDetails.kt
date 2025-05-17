package com.example.hrm.component.showcard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.hrm.component.DetailsCard
import com.example.hrm.component.RowItem
import com.example.hrm.db.entity.CtScan
import com.example.hrm.db.entity.Ecg

@Composable
fun PictureDetail(data: Any?) {
    if (data == null) return
    when (data) {
        is Ecg? -> {
            DetailsCard(
                title = "心电图",
            ) {
                Image(
                    painter = rememberAsyncImagePainter(data?.imagePath),
                    contentDescription = "心电图图",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
                RowItem(label = "诊断结果", value = data?.result ?: "无")
            }
        }
        is CtScan? ->  {
            DetailsCard(
                title = "胸部X光",
            ) {
                Image(
                    painter = rememberAsyncImagePainter(data.imagePath),
                    contentDescription = "胸部X光图",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
                RowItem(label = "诊断结果", value = data.result ?: "无")
            }
        }
    }
}