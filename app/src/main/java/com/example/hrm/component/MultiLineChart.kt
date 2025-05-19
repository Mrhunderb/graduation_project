package com.example.hrm.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

@Composable
fun MultiLineChart(
    chartData: Map<ChartType, List<BloodRecord>>,
    modifier: Modifier = Modifier
) {
    var selectedType by remember { mutableStateOf(ChartType.RBC) }

    Column(modifier = modifier.padding(16.dp)) {
        TabRow(selectedTabIndex = selectedType.ordinal) {
            ChartType.entries.forEachIndexed { index, type ->
                Tab(
                    selected = selectedType.ordinal == index,
                    onClick = { selectedType = type },
                    text = { Text(type.name) } // Show concise name in tabs
                )
            }
        }

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
                val records = chartData[selectedType] ?: emptyList()
                if (records.isNotEmpty()) {
                    val entries = records.mapIndexed { index, record ->
                        Entry(index.toFloat(), record.value)
                    }
                    val dataSet = LineDataSet(entries, selectedType.label).apply {
                        color = selectedType.color.toArgb()
                        valueTextColor = Color.Black.toArgb()
                        lineWidth = 2f
                        setDrawCircles(true)
                        setDrawValues(true)
                        circleRadius = 4f
                        circleHoleRadius = 2f
                    }
                    val labels = records.map { it.date }
                    chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
                    chart.data = LineData(dataSet)
                } else {
                    chart.clear()
                }
                chart.invalidate()
            }
        )
    }
}

enum class ChartType(name: String, val label: String, val color: Color) {
    RBC("RBC", "红细胞计数 RBC (10¹²/L)", Color.Red),
    HB("Hb", "血红蛋白 Hb (g/L)", Color.Blue),
    HCT("HCT", "红细胞比容 HCT (%)", Color.Green),
    MCV("MCV", "平均红细胞体积 MCV (fl)", Color.Magenta)
}

data class BloodRecord(
    val date: String,
    val value: Float
)