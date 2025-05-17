package com.example.hrm.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
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
import com.example.hrm.db.entity.BloodData

@Composable
fun AnalyseScreen(
    navController: NavController,
    viewModel: HealthViewModel = viewModel()
) {
    val data by viewModel.latestRecord.collectAsState()
    var generalData by remember { mutableStateOf<GeneralPhysical?>(null) }
    var bloodData by remember { mutableStateOf<BloodData?>(null) }

    LaunchedEffect(data) {
        data?.let { record ->
            viewModel.getGeneralDataById(record.id) {
                generalData = it
            }
            viewModel.getBloodDataById(record.id) {
                bloodData = it
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
            Spacer(modifier = Modifier.height(16.dp))
            BloodRoutineDetails(bloodData)
        }
    }
}

@Composable
fun BloodRoutineDetails(data: BloodData?) {
        DetailsCard(
            title = "血常规数据",
            content = {
                Text("白细胞指标", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                IndicatorRow("白细胞 WBC", data?.wbc, "10⁹/L", 3.5f..9.5f)
                IndicatorRow("中性粒细胞 %", data?.granPercent, "%", 40f..75f)
                IndicatorRow("淋巴细胞 %", data?.lymPercent, "%", 20f..50f)
                IndicatorRow("单核细胞 %", data?.monoPercent, "%", 3f..10f)
                IndicatorRow("嗜酸性粒细胞 %", data?.eosPercent, "%", 0.4f..8.0f)
                IndicatorRow("嗜碱性粒细胞 %", data?.basoPercent, "%", 0f..1f)

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    color = LocalContentColor.current.copy(alpha = 0.2f)
                )
                Text("红细胞指标", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                IndicatorRow("红细胞 RBC", data?.rbc, "10¹²/L", 4.3f..5.8f)
                IndicatorRow("血红蛋白 Hb", data?.hb, "g/L", 130f..175f)
                IndicatorRow("红细胞比容 HCT", data?.hct, "%", 40f..50f)
                IndicatorRow("平均红细胞体积 MCV", data?.mcv, "fL", 82f..100f)

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    color = LocalContentColor.current.copy(alpha = 0.2f)
                )
                Text("血小板指标", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                IndicatorRow("血小板 PLT", data?.plt, "10⁹/L", 125f..350f)
                IndicatorRow("平均血小板体积 MPV", data?.mpv, "fL", 8.4f..12.7f)
                IndicatorRow("血小板压积 PCT", data?.pct, "", 0.17f..0.37f)
            }
        )
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
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = LocalContentColor.current.copy(alpha = 0.2f)
            )
            RowItem(label = "血压收缩压", value = "${data?.systolicPressure ?: "N/A"} mmHg")
            RowItem(label = "血压舒张压", value = "${data?.diastolicPressure ?: "N/A"} mmHg")
            RowItem(label = "脉搏", value = "${data?.pulse ?: "N/A"} bpm")

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = LocalContentColor.current.copy(alpha = 0.2f)
            )
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

@Composable
fun IndicatorRow(label: String, value: Float?, unit: String, normalRange: ClosedFloatingPointRange<Float>) {
    val (color, arrow) = when {
        value == null -> Color.Gray to ""
        value < normalRange.start -> Color.Blue to "↓"
        value > normalRange.endInclusive -> Color.Red to "↑"
        else -> Color.Unspecified to ""
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, modifier = Modifier.weight(1f))
        Text(
            text = if (value != null) "$value $unit $arrow" else "N/A",
            color = color,
            fontWeight = if (arrow.isNotEmpty()) FontWeight.Bold else FontWeight.Normal
        )
    }
}

