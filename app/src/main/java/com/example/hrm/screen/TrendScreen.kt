package com.example.hrm.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hrm.component.MarkdownView
import com.example.hrm.db.HealthViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TrendScreen(
    navController: NavController,
    viewModel: HealthViewModel = viewModel(),
) {

    val data by viewModel.bloodRecord.collectAsState()
    val response by viewModel.responseText.collectAsState()
    var rbcList by remember { mutableStateOf<List<BloodRecord>>(emptyList()) }
    val formatter = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    LaunchedEffect(data) {
        for (i in data.indices) {
            if (data[i].rbc != null) {
                rbcList = rbcList + BloodRecord(
                    date = formatter.format(data[i].date),
                    value = data[i].rbc!!.toFloat()
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("红细胞计数变化曲线", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        RbcLineChart(rbcList)
        val systemPrompt = "你是一个医学顾问，请提供关于血常规的趋势（日期和数值）给出一共两点：1.趋势的分析 2.尤其是生活上建议"
        val userInput = rbcList.joinToString(", ") { "日期：${it.date}, rbc的值:${it.value}" }
        var isLoading by remember { mutableStateOf(false) }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                isLoading = true
                viewModel.askAi(systemPrompt, userInput)
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("获取分析建议")
        }
        Spacer(modifier = Modifier.height(16.dp))
        MarkdownView(
            markdownText = response,
            isLoading = isLoading,
        )
    }
}

data class BloodRecord(
    val date: String,
    val value: Float
)

@Composable
fun RbcLineChart(records: List<BloodRecord>) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = { context ->
            LineChart(context).apply {
                description.isEnabled = false
                legend.isEnabled = true
                axisRight.isEnabled = false
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.granularity = 1f
                xAxis.setDrawGridLines(false)
                axisLeft.setDrawGridLines(false)
            }
        },
        update = { chart ->
            val entries = records.mapIndexed { index, record ->
                Entry(index.toFloat(), record.value)
            }

            val dataSet = LineDataSet(entries, "RBC (10^12/L)").apply {
                color = Color.Red.toArgb()
                valueTextColor = Color.Black.toArgb()
                lineWidth = 2f
                setDrawCircles(true)
                setDrawValues(true)
                circleRadius = 4f
                circleHoleRadius = 2f
            }

            chart.data = LineData(dataSet)

            val labels = records.map { it.date }
            chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)

            chart.invalidate()
        }
    )
}

