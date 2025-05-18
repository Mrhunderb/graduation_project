package com.example.hrm.screen

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.unit.dp
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.*

@Composable
fun TrendScreen() {
    val entries = listOf(
        Entry(0f, 60f),
        Entry(1f, 62f),
        Entry(2f, 61.5f),
        Entry(3f, 63f),
        Entry(4f, 64f),
        Entry(5f, 62.8f)
    )

    val dataSet = LineDataSet(entries, "体重记录 (kg)").apply {
        color = AndroidColor.BLUE
        valueTextColor = AndroidColor.BLACK
        lineWidth = 2f
        circleRadius = 4f
        setCircleColor(AndroidColor.BLUE)
        setDrawValues(true)
        mode = LineDataSet.Mode.CUBIC_BEZIER // 平滑曲线
    }

    val lineData = LineData(dataSet)

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp),
        factory = { context ->
            LineChart(context).apply {
                this.data = lineData
                this.setTouchEnabled(true)
                this.setPinchZoom(true)
                this.axisRight.isEnabled = false
                this.xAxis.granularity = 1f
                this.description = Description().apply {
                    text = "体重随时间变化"
                    textColor = AndroidColor.DKGRAY
                    textSize = 12f
                }
                this.invalidate() // refresh
            }
        }
    )
}

