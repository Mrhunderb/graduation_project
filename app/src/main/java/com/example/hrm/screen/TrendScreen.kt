package com.example.hrm.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.aallam.openai.api.chat.ChatCompletionChunk
import com.aallam.openai.api.chat.ChatCompletionRequest
import com.aallam.openai.api.chat.ChatMessage
import com.aallam.openai.api.chat.ChatRole
import com.aallam.openai.api.chat.StreamOptions
import com.aallam.openai.api.http.Timeout
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIHost
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration.Companion.seconds

@Composable
fun TrendScreen() {
    val sampleData = listOf(
        BloodRecord("2025-01", 4.3f),
        BloodRecord("2025-02", 4.5f),
        BloodRecord("2025-03", 4.7f),
        BloodRecord("2025-04", 4.6f),
        BloodRecord("2025-05", 4.4f),
        BloodRecord("2025-06", 4.8f),
        BloodRecord("2025-07", 4.9f),
        BloodRecord("2025-08", 4.2f),
        BloodRecord("2025-09", 4.1f),
        BloodRecord("2025-10", 4.3f),
    )

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("红细胞计数变化曲线", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        RbcLineChart(sampleData)
    }
}

data class BloodRecord(
    val date: String,
    val rbc: Float
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
                axisLeft.setDrawGridLines(true)
            }
        },
        update = { chart ->
            val entries = records.mapIndexed { index, record ->
                Entry(index.toFloat(), record.rbc)
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
    var responseText by remember { mutableStateOf("") }
    LaunchedEffect(true) {
        val openai = OpenAI(
            host = OpenAIHost("https://api.deepseek.com/v1/"),
            token = "sk-b0f00d21928c48c895db9bdce139c705",
            timeout = Timeout(socket = 60.seconds),
        )
        val chatCompletionRequest = ChatCompletionRequest(
            model = ModelId("deepseek-chat"),
            messages = listOf(
                ChatMessage(
                    role = ChatRole.System,
                    content = "告诉我关于红细胞计数的变化趋势的原因，以及给出一般性的建议比如在生活中应该注意什么或饮食方面应该注意什么"
                ),
                ChatMessage(
                    role = ChatRole.User,
                    content = "先降低红细胞计数，然后升高，最后又降低。"
                )
            ),
        )
        val completions: Flow<ChatCompletionChunk> = openai.chatCompletions(chatCompletionRequest)
        completions.collect { chunk ->
            val message = chunk.choices.first().delta?.content
            if (message != null) {
                responseText += message
            }
        }
    }
    Text(
        text = responseText,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(20.dp)
    )
}

