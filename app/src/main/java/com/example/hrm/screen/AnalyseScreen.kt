package com.example.hrm.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hrm.db.HealthViewModel
import com.example.hrm.db.entity.GeneralPhysical
import com.example.hrm.component.RowItem

@Composable
fun AnalyseScreen(
    navController: NavController,
    viewModel: HealthViewModel = viewModel()
) {
    val data by viewModel.latestRecord.collectAsState()
    var generalData by remember { mutableStateOf<GeneralPhysical?>(null) }

    LaunchedEffect(data) {
        data?.let { record ->
            viewModel.getGeneralDataById(record.id) {
                generalData = it
            }
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (data == null) {
            Text(
                text = "暂无数据",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        } else {
            GeneralPhysicalDetails(generalData)
        }
    }
}

@Composable
fun GeneralPhysicalDetails(data: GeneralPhysical?) {
    DetailsCard (
        title = "基础体检数据",
        content = {
            RowItem(label = "身高", value = "${data?.height ?: "N/A"} cm")
            RowItem(label = "体重", value = "${data?.weight ?: "N/A"} kg")
            RowItem(label = "BMI", value = "${data?.bmi ?: "N/A"}")

            if (data?.bmi != null) {
                val status = when {
                    data.bmi!! > 24.0 -> "偏胖"
                    data.bmi!! < 18.5 -> "偏瘦"
                    else -> "正常"
                }
                val color = when {
                    data.bmi!! > 24.0 -> Color.Red
                    data.bmi!! < 18.5 -> Color.Blue
                    else -> MaterialTheme.colorScheme.primary
                }
                RowItem(label = "BMI评估", value = status, color = color, bold = true)
            }

            Divider()

            RowItem(label = "血压收缩压", value = "${data?.systolicPressure ?: "N/A"} mmHg")
            RowItem(label = "血压舒张压", value = "${data?.diastolicPressure ?: "N/A"} mmHg")
            RowItem(label = "脉搏", value = "${data?.pulse ?: "N/A"} bpm")

            Divider()

            RowItem(label = "左眼视力", value = "${data?.leftEyeVision ?: "N/A"}")
            RowItem(label = "右眼视力", value = "${data?.rightEyeVision ?: "N/A"}")
        }
    )
}

@Composable
fun DetailsCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            content()
        }
    }
}
