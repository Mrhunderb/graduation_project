package com.example.hrm.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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

    // Red blood cell related lists
    var rbcList by remember { mutableStateOf<List<BloodRecord>>(emptyList()) }
    var hbList by remember { mutableStateOf<List<BloodRecord>>(emptyList()) }
    var hctList by remember { mutableStateOf<List<BloodRecord>>(emptyList()) }
    var mcvList by remember { mutableStateOf<List<BloodRecord>>(emptyList()) }

    // White blood cell related lists
    var wbcList by remember { mutableStateOf<List<BloodRecord>>(emptyList()) }
    var lymphList by remember { mutableStateOf<List<BloodRecord>>(emptyList()) }
    var monoList by remember { mutableStateOf<List<BloodRecord>>(emptyList()) }

    // Platelet related lists
    var pltList by remember { mutableStateOf<List<BloodRecord>>(emptyList()) }
    var mpvList by remember { mutableStateOf<List<BloodRecord>>(emptyList()) }
    var pdwList by remember { mutableStateOf<List<BloodRecord>>(emptyList()) }

    val formatter = remember {
        SimpleDateFormat("yy-MM-dd", Locale.getDefault())
    }

    LaunchedEffect(data) {
        // Process red blood cell data
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

            // Process white blood cell data
            if (data[i].wbc != null) {
                wbcList = wbcList + BloodRecord(
                    date = formatter.format(data[i].date),
                    value = data[i].wbc!!.toFloat()
                )
            }
            if (data[i].lymPercent != null) {
                lymphList = lymphList + BloodRecord(
                    date = formatter.format(data[i].date),
                    value = data[i].lymPercent!!.toFloat()
                )
            }
            if (data[i].monoPercent != null) {
                monoList = monoList + BloodRecord(
                    date = formatter.format(data[i].date),
                    value = data[i].monoPercent!!.toFloat()
                )
            }

            // Process platelet data
            if (data[i].plt != null) {
                pltList = pltList + BloodRecord(
                    date = formatter.format(data[i].date),
                    value = data[i].plt!!.toFloat()
                )
            }
            if (data[i].mpv != null) {
                mpvList = mpvList + BloodRecord(
                    date = formatter.format(data[i].date),
                    value = data[i].mpv!!.toFloat()
                )
            }
            if (data[i].pdw != null) {
                pdwList = pdwList + BloodRecord(
                    date = formatter.format(data[i].date),
                    value = data[i].pdw!!.toFloat()
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
        var selectedCellType by remember { mutableStateOf("红细胞") }
        var expanded by remember { mutableStateOf(false) }

        // Cell type dropdown
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                OutlinedTextField(
                    value = selectedCellType,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("血细胞类型") },
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                contentDescription = "展开下拉菜单"
                            )
                        }
                    }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    listOf("红细胞", "白细胞", "血小板").forEach { cellType ->
                        DropdownMenuItem(
                            text = { Text(cellType) },
                            onClick = {
                                selectedCellType = cellType
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        val systemPrompt = "你是一个医学顾问，请提供关于血常规的趋势（日期和数值）给出一共两点：1.趋势的分析 2.尤其是生活上建议"
        var userInput = ""

        when (selectedCellType) {
            "红细胞" -> {
                val redBloodCellData = mapOf(
                    ChartType.RBC to rbcList,
                    ChartType.HB to hbList,
                    ChartType.HCT to hctList,
                    ChartType.MCV to mcvList
                )
                MultiLineChart(redBloodCellData, ChartType.RBC)
                userInput += rbcList.joinToString(", ") { "日期：${it.date}, rbc的值:${it.value}" }
                userInput += hbList.joinToString(", ") { "日期：${it.date}, hb的值:${it.value}" }
                userInput += hctList.joinToString(", ") { "日期：${it.date}, hct的值:${it.value}" }
                userInput += mcvList.joinToString(", ") { "日期：${it.date}, mcv的值:${it.value}" }
            }
            "白细胞" -> {
                val whiteBloodCellData = mapOf(
                    ChartType.WBC to wbcList,
                    ChartType.LYMPH to lymphList,
                    ChartType.MONO to monoList
                )
                MultiLineChart(whiteBloodCellData, ChartType.WBC)
                userInput += wbcList.joinToString(", ") { "日期：${it.date}, wbc的值:${it.value}" }
                userInput += lymphList.joinToString(", ") { "日期：${it.date}, lym的值:${it.value}" }
                userInput += monoList.joinToString(", ") { "日期：${it.date}, mono的值:${it.value}" }
            }
            "血小板" -> {
                val plateletData = mapOf(
                    ChartType.PLT to pltList,
                    ChartType.MPV to mpvList,
                    ChartType.PDW to pdwList
                )
                MultiLineChart(plateletData, ChartType.PLT)
                userInput += pltList.joinToString(", ") { "日期：${it.date}, plt的值:${it.value}" }
                userInput += mpvList.joinToString(", ") { "日期：${it.date}, mpv的值:${it.value}" }
                userInput += pdwList.joinToString(", ") { "日期：${it.date}, pdw的值:${it.value}" }
            }
        }

        var isLoading by remember { mutableStateOf(false) }

        Button(
            onClick = {
                isLoading = true
                viewModel.askAi(systemPrompt, userInput)
            },
            enabled = userInput.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("获取分析建议")
        }
        Spacer(modifier = Modifier.height(10.dp))
        MarkdownView(
            markdownText = response,
            isLoading = isLoading,
        )
    }
}
