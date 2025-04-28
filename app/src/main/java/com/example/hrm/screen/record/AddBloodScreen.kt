package com.example.hrm.screen.record

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.hrm.db.entity.BloodData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBloodScreen(
    onBack: () -> Unit,
) {
    val context = LocalContext.current

    var wbc by rememberSaveable { mutableStateOf("") }
    var granPercent by rememberSaveable { mutableStateOf("") }
    var lymPercent by rememberSaveable { mutableStateOf("") }
    var monoPercent by rememberSaveable { mutableStateOf("") }
    var eosPercent by rememberSaveable { mutableStateOf("") }
    var basoPercent by rememberSaveable { mutableStateOf("") }

    var rbc by rememberSaveable { mutableStateOf("") }
    var hb by rememberSaveable { mutableStateOf("") }
    var hct by rememberSaveable { mutableStateOf("") }

    var rdwSd by rememberSaveable { mutableStateOf("") }
    var rdwCv by rememberSaveable { mutableStateOf("") }

    var plt by rememberSaveable { mutableStateOf("") }
    var mpv by rememberSaveable { mutableStateOf("") }
    var pdw by rememberSaveable { mutableStateOf("") }
    var plcr by rememberSaveable { mutableStateOf("") }

    // --- 自动计算 ---
    val granAbs = wbc.toFloatOrNull()?.times(granPercent.toFloatOrNull() ?: 0f)?.div(100) ?: 0f
    val lymAbs = wbc.toFloatOrNull()?.times(lymPercent.toFloatOrNull() ?: 0f)?.div(100) ?: 0f
    val monoAbs = wbc.toFloatOrNull()?.times(monoPercent.toFloatOrNull() ?: 0f)?.div(100) ?: 0f
    val eosAbs = wbc.toFloatOrNull()?.times(eosPercent.toFloatOrNull() ?: 0f)?.div(100) ?: 0f
    val basoAbs = wbc.toFloatOrNull()?.times(basoPercent.toFloatOrNull() ?: 0f)?.div(100) ?: 0f

    val mcv = if (rbc.isNotEmpty() && hct.isNotEmpty()) {
        (hct.toFloatOrNull() ?: 0f) * 10 / (rbc.toFloatOrNull() ?: 1f)
    } else 0f

    val mch = if (rbc.isNotEmpty() && hb.isNotEmpty()) {
        (hb.toFloatOrNull() ?: 0f) * 10 / (rbc.toFloatOrNull() ?: 1f)
    } else 0f

    val mchc = if (hct.isNotEmpty() && hb.isNotEmpty()) {
        (hb.toFloatOrNull() ?: 0f) * 100 / (hct.toFloatOrNull() ?: 1f)
    } else 0f

    val pct = if (plt.isNotEmpty() && mpv.isNotEmpty()) {
        (plt.toFloatOrNull() ?: 0f) * (mpv.toFloatOrNull() ?: 0f) / 10000
    } else 0f

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("新增血常规") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // 白细胞部分
            Text("白细胞相关", style = MaterialTheme.typography.titleMedium)
            InputField(label = "白细胞计数 (WBC)", value = wbc) { wbc = it }
            InputField(label = "中性粒细胞百分比 (GRAN%)", value = granPercent) { granPercent = it }
            InputField(label = "淋巴细胞百分比 (LYM%)", value = lymPercent) { lymPercent = it }
            InputField(label = "单核细胞百分比 (Mono%)", value = monoPercent) { monoPercent = it }
            InputField(label = "嗜酸性粒细胞百分比 (Eos%)", value = eosPercent) { eosPercent = it }
            InputField(label = "嗜碱性粒细胞百分比 (Baso%)", value = basoPercent) { basoPercent = it }

            Spacer(modifier = Modifier.height(16.dp))

            Text("红细胞相关", style = MaterialTheme.typography.titleMedium)
            InputField(label = "红细胞计数 (RBC)", value = rbc) { rbc = it }
            InputField(label = "血红蛋白 (Hb)", value = hb) { hb = it }
            InputField(label = "红细胞比容 (HCT)", value = hct) { hct = it }
            InputField(label = "红细胞分布宽度-SD (RDW-SD)", value = rdwSd) { rdwSd = it }
            InputField(label = "红细胞分布宽度-CV (RDW-CV)", value = rdwCv) { rdwCv = it }

            Spacer(modifier = Modifier.height(16.dp))

            Text("血小板相关", style = MaterialTheme.typography.titleMedium)
            InputField(label = "血小板计数 (PLT)", value = plt) { plt = it }
            InputField(label = "平均血小板体积 (MPV)", value = mpv) { mpv = it }
            InputField(label = "血小板分布宽度 (PDW)", value = pdw) { pdw = it }
            InputField(label = "大血小板比率 (P-LCR)", value = plcr) { plcr = it }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val record = BloodData(
                        wbc = wbc,
                        granPercent = granPercent,
                        lymPercent = lymPercent,
                        monoPercent = monoPercent,
                        eosPercent = eosPercent,
                        basoPercent = basoPercent,
                        granAbs = granAbs.toString(),
                        lymAbs = lymAbs.toString(),
                        monoAbs = monoAbs.toString(),
                        eosAbs = eosAbs.toString(),
                        basoAbs = basoAbs.toString(),
                        rbc = rbc,
                        hb = hb,
                        hct = hct,
                        mcv = mcv.toString(),
                        mch = mch.toString(),
                        mchc = mchc.toString(),
                        rdwSd = rdwSd,
                        rdwCv = rdwCv,
                        plt = plt,
                        mpv = mpv,
                        pct = pct.toString(),
                        pdw = pdw,
                        plcr = plcr
                    )
//                    onSave(record)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("保存")
            }
        }
    }
}

@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}

