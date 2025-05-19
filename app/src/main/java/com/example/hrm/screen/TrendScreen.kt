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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hrm.component.BloodRecord
import com.example.hrm.component.ChartType
import com.example.hrm.component.MarkdownView
import com.example.hrm.component.MultiLineChart
import com.example.hrm.db.HealthViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TrendScreen(
    viewModel: HealthViewModel = viewModel(),
) {

    val data by viewModel.bloodRecord.collectAsState()
    val response by viewModel.responseText.collectAsState()
    var rbcList by remember { mutableStateOf<List<BloodRecord>>(emptyList()) }
    var hbList by remember { mutableStateOf<List<BloodRecord>>(emptyList()) }
    var hctList by remember { mutableStateOf<List<BloodRecord>>(emptyList()) }
    var mcvList by remember { mutableStateOf<List<BloodRecord>>(emptyList()) }

    val formatter = remember {
        SimpleDateFormat("yy-MM-dd", Locale.getDefault())
    }

    LaunchedEffect(data) {
        for (i in data.indices) {
            if (data[i].rbc != null) {
                rbcList = rbcList + BloodRecord(
                    date = formatter.format(data[i].date),
                    value = data[i].rbc!!.toFloat()
                )
            }
            if (data[i].hb != null) {
                hbList = hbList + BloodRecord(
                    date = formatter.format(data[i].date),
                    value = data[i].hb!!.toFloat()
                )
            }
            if (data[i].hct != null) {
                hctList = hctList + BloodRecord(
                    date = formatter.format(data[i].date),
                    value = data[i].hct!!.toFloat()
                )
            }
            if (data[i].mcv != null) {
                mcvList = mcvList + BloodRecord(
                    date = formatter.format(data[i].date),
                    value = data[i].mcv!!.toFloat()
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
        var chartData = mapOf(
                ChartType.RBC to rbcList,
                ChartType.HB to hbList,
                ChartType.HCT to hctList,
                ChartType.MCV to mcvList
        )
        Text("血常规变化曲线", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        MultiLineChart(chartData)
        val systemPrompt = "你是一个医学顾问，请提供关于血常规的趋势（日期和数值）给出一共两点：1.趋势的分析 2.尤其是生活上建议"
        var userInput = rbcList.joinToString(", ") { "日期：${it.date}, rbc的值:${it.value}" }
        userInput += hbList.joinToString(", ") { "日期：${it.date}, hb的值:${it.value}" }
        userInput += hctList.joinToString(", ") { "日期：${it.date}, hct的值:${it.value}" }
        userInput += mcvList.joinToString(", ") { "日期：${it.date}, mcv的值:${it.value}" }
        var isLoading by remember { mutableStateOf(false) }

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
