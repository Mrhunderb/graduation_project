package com.example.hrm.screen.record

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hrm.db.HealthViewModel
import com.example.hrm.db.entity.BloodData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBloodScreen(
    navController: NavController,
    id: Long,
    viewModel: HealthViewModel = viewModel(),
) {
    var wbc by rememberSaveable { mutableStateOf("") }
    var granPercent by rememberSaveable { mutableStateOf("") }
    var lymPercent by rememberSaveable { mutableStateOf("") }
    var monoPercent by rememberSaveable { mutableStateOf("") }
    var eosPercent by rememberSaveable { mutableStateOf("") }
    var basoPercent by rememberSaveable { mutableStateOf("") }
    var granAbs by rememberSaveable { mutableStateOf("") }
    var lymAbs by rememberSaveable { mutableStateOf("") }
    var monoAbs by rememberSaveable { mutableStateOf("") }
    var eosAbs by rememberSaveable { mutableStateOf("") }
    var basoAbs by rememberSaveable { mutableStateOf("") }

    var rbc by rememberSaveable { mutableStateOf("") }
    var hb by rememberSaveable { mutableStateOf("") }
    var hct by rememberSaveable { mutableStateOf("") }
    var mcv by rememberSaveable { mutableStateOf("") }
    var mch by rememberSaveable { mutableStateOf("") }
    var mchc by rememberSaveable { mutableStateOf("") }

    var rdwSd by rememberSaveable { mutableStateOf("") }
    var rdwCv by rememberSaveable { mutableStateOf("") }

    var plt by rememberSaveable { mutableStateOf("") }
    var mpv by rememberSaveable { mutableStateOf("") }
    var pct by rememberSaveable { mutableStateOf("") }
    var pdw by rememberSaveable { mutableStateOf("") }
    var plcr by rememberSaveable { mutableStateOf("") }

    var showBackConfirmDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(id) {
        viewModel.getBloodDataById(
            id,
            onComplete = {
                if (it != null) {
                    wbc = it.wbc.toString()
                    granPercent = it.granPercent.toString()
                    lymPercent = it.lymPercent.toString()
                    monoPercent = it.monoPercent.toString()
                    eosPercent = it.eosPercent.toString()
                    basoPercent = it.basoPercent.toString()
                    granAbs = it.granAbs.toString()
                    lymAbs = it.lymAbs.toString()
                    monoAbs = it.monoAbs.toString()
                    eosAbs = it.eosAbs.toString()
                    basoAbs = it.basoAbs.toString()

                    rbc = it.rbc.toString()
                    hb = it.hb.toString()
                    hct = it.hct.toString()
                    mcv = it.mcv.toString()
                    mch = it.mch.toString()
                    mchc = it.mchc.toString()

                    rdwSd = it.rdwSd.toString()
                    rdwCv = it.rdwCv.toString()

                    plt = it.plt.toString()
                    mpv = it.mpv.toString()
                    pct = it.pct.toString()
                    pdw = it.pdw.toString()
                    plcr = it.plcr.toString()
                }
                isLoading = false
            }
        )
    }

    if (showBackConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showBackConfirmDialog = false },
            title = { Text("确认返回") },
            text = { Text("如果现在返回，当前填写的数据将不会保存，确定要返回吗？") },
            confirmButton = {
                Button(onClick = {
                    showBackConfirmDialog = false
                    navController.popBackStack()
                }) {
                    Text("确认返回")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showBackConfirmDialog = false }) {
                    Text("取消")
                }
            }
        )
    }

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("新增血常规") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
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
                InputField(label = "白细胞计数 (WBC) 单位 10^9L", value = wbc) { wbc = it }
                InputField(label = "中性粒细胞百分比 (GRAN%)", value = granPercent) {
                    granPercent = it
                }
                InputField(label = "淋巴细胞百分比 (LYM%)", value = lymPercent) { lymPercent = it }
                InputField(label = "单核细胞百分比 (Mono%)", value = monoPercent) {
                    monoPercent = it
                }
                InputField(label = "嗜酸性粒细胞百分比 (Eos%)", value = eosPercent) {
                    eosPercent = it
                }
                InputField(
                    label = "嗜碱性粒细胞百分比 (Baso%)",
                    value = basoPercent
                ) { basoPercent = it }
                InputField(
                    label = "中性粒细胞绝对值 (GRAN#) 单位 10^9L",
                    value = granAbs
                ) { granAbs = it }
                InputField(label = "淋巴细胞绝对值 (LYM#) 单位 10^9L", value = lymAbs) {
                    lymAbs = it
                }
                InputField(label = "单核细胞绝对值 (Mono#) 单位 10^9L", value = monoAbs) {
                    monoAbs = it
                }
                InputField(
                    label = "嗜酸性粒细胞绝对值 (Eos#) 单位 10^9L",
                    value = eosAbs
                ) { eosAbs = it }
                InputField(
                    label = "嗜碱性粒细胞绝对值 (Baso#) 单位 10^9L",
                    value = basoAbs
                ) { basoAbs = it }

                Spacer(modifier = Modifier.height(16.dp))

                Text("红细胞相关", style = MaterialTheme.typography.titleMedium)
                InputField(label = "红细胞计数 (RBC) 单位 10^9L", value = rbc) { rbc = it }
                InputField(label = "血红蛋白 (Hb) 单位 g/L", value = hb) { hb = it }
                InputField(label = "红细胞比容 (HCT) 单位 %", value = hct) { hct = it }
                InputField(label = "红细胞分布宽度-SD (RDW-SD) 单位 fL", value = rdwSd) {
                    rdwSd = it
                }
                InputField(label = "红细胞分布宽度-CV (RDW-CV) 单位 %", value = rdwCv) {
                    rdwCv = it
                }
                InputField(label = "平均红细胞体积 (MCV) 单位 fL", value = mcv) { mcv = it }
                InputField(label = "平均红细胞血红蛋白 (MCH) 单位 pg", value = mch) { mch = it }
                InputField(label = "平均红细胞血红蛋白浓度 (MCHC) 单位 g/L", value = mchc) {
                    mchc = it
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("血小板相关", style = MaterialTheme.typography.titleMedium)
                InputField(label = "血小板计数 (PLT) 单位 10^9L", value = plt) { plt = it }
                InputField(label = "平均血小板体积 (MPV) 单位 fL", value = mpv) { mpv = it }
                InputField(label = "血小板体积 (PCT) 单位 %", value = pct) { pct = it }
                InputField(label = "血小板分布宽度 (PDW) 单位 fL", value = pdw) { pdw = it }
                InputField(label = "大血小板比率 (P-LCR)", value = plcr) { plcr = it }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val record = BloodData(
                            sessionId = id,
                            wbc = wbc.toFloatOrNull(),
                            granPercent = granPercent.toFloatOrNull(),
                            lymPercent = lymPercent.toFloatOrNull(),
                            monoPercent = monoPercent.toFloatOrNull(),
                            eosPercent = eosPercent.toFloatOrNull(),
                            basoPercent = basoPercent.toFloatOrNull(),
                            granAbs = granAbs.toFloatOrNull(),
                            lymAbs = lymAbs.toFloatOrNull(),
                            monoAbs = monoAbs.toFloatOrNull(),
                            eosAbs = eosAbs.toFloatOrNull(),
                            basoAbs = basoAbs.toFloatOrNull(),
                            rbc = rbc.toFloatOrNull(),
                            hb = hb.toFloatOrNull(),
                            hct = hct.toFloatOrNull(),
                            mcv = mcv.toFloatOrNull(),
                            mch = mch.toFloatOrNull(),
                            mchc = mchc.toFloatOrNull(),
                            rdwSd = rdwSd.toFloatOrNull(),
                            rdwCv = rdwCv.toFloatOrNull(),
                            plt = plt.toFloatOrNull(),
                            mpv = mpv.toFloatOrNull(),
                            pct = pct.toFloatOrNull(),
                            pdw = pdw.toFloatOrNull(),
                            plcr = plcr.toFloatOrNull()
                        )
                        viewModel.addBloodData(record)
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("保存")
                }
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

