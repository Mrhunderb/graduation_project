package com.example.hrm.screen.record

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hrm.component.PositiveNegativeDropdown
import com.example.hrm.db.HealthViewModel
import com.example.hrm.db.entity.UrineRoutine

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUrineScreen(
    navController: NavController,
    id: Long,
    viewModel: HealthViewModel = viewModel()
) {
    val context = LocalContext.current
    val ket = remember { mutableStateOf("") }
    val uro = remember { mutableStateOf("") }
    val bil = remember { mutableStateOf("") }
    val bld = remember { mutableStateOf("") }
    val wbc = remember { mutableStateOf("") }
    val ph = remember { mutableStateOf("") }
    val nit = remember { mutableStateOf("") }
    val glu = remember { mutableStateOf("") }
    val vc = remember { mutableStateOf("") }
    val sg = remember { mutableStateOf("") }
    val pro = remember { mutableStateOf("") }

    val fields = listOf(
        "尿酮体 (KET)" to ket,
        "尿胆原 (URO)" to uro,
        "尿胆红素 (BIL)" to bil,
        "尿潜血 (BLD)" to bld,
        "尿白细胞 (WBC)" to wbc,
        "酸碱度 (PH)" to ph,
        "亚硝酸盐 (NIT)" to nit,
        "尿葡萄糖 (GLU)" to glu,
        "维生素C (VC)" to vc,
        "比重 (SG)" to sg,
        "尿蛋白 (PRO)" to pro,
    )

    var isLoading by remember { mutableStateOf(true) }
    var isModified by remember { mutableStateOf(false) }
    var isSubmitting by remember { mutableStateOf(false) }
    var showBackConfirmDialog by remember { mutableStateOf(false) }
    var data = remember { mutableStateOf<UrineRoutine?>(null) }

    LaunchedEffect(id) {
        viewModel.getUrineDataById(
            id,
            onComplete = {
                if (it != null) {
                    isModified = true
                    data.value = it
                    ket.value = it.ket.toString()
                    uro.value = it.uro.toString()
                    bil.value = it.bil.toString()
                    bld.value = it.bld.toString()
                    wbc.value = it.wbc.toString()
                    ph.value = it.ph.toString()
                    nit.value = it.nit.toString()
                    glu.value = it.glu.toString()
                    vc.value = it.vc.toString()
                    sg.value = it.sg.toString()
                    pro.value = it.pro.toString()
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
                    title = { Text("新增尿常规") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            showBackConfirmDialog = true
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
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text("请输入尿液检测数据", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))

                fields.forEach { (label, state) ->
                    when(label) {
                        "酸碱度 (PH)" -> {
                            OutlinedTextField(
                                value = state.value,
                                onValueChange = { state.value = it },
                                label = { Text(label) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                            )
                        }
                        "比重 (SG)" -> {
                            OutlinedTextField(
                                value = state.value,
                                onValueChange = { state.value = it },
                                label = { Text(label) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                            )
                        }
                        else -> {
                            PositiveNegativeDropdown(
                                label = label,
                                selectedValue = state.value,
                            ) {
                                when (it) {
                                    "阳性" -> ket.value = "1"
                                    "阴性" -> ket.value = "0"
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        try {
                            var urineData = UrineRoutine(
                                sessionId = id,
                                ket = ket.value.toFloatOrNull(),
                                uro = uro.value.toFloatOrNull(),
                                bil = bil.value.toFloatOrNull(),
                                bld = bld.value.toFloatOrNull(),
                                wbc = wbc.value.toFloatOrNull(),
                                ph = ph.value.toFloatOrNull(),
                                nit = nit.value.toFloatOrNull(),
                                glu = glu.value.toFloatOrNull(),
                                vc = vc.value.toFloatOrNull(),
                                sg = sg.value.toFloatOrNull(),
                                pro = pro.value.toFloatOrNull()
                            )
                            if (isModified) {
                                urineData = data.value!!.copy(
                                    ket = ket.value.toFloatOrNull(),
                                    uro = uro.value.toFloatOrNull(),
                                    bil = bil.value.toFloatOrNull(),
                                    bld = bld.value.toFloatOrNull(),
                                    wbc = wbc.value.toFloatOrNull(),
                                    ph = ph.value.toFloatOrNull(),
                                    nit = nit.value.toFloatOrNull(),
                                    glu = glu.value.toFloatOrNull(),
                                    vc = vc.value.toFloatOrNull(),
                                    sg = sg.value.toFloatOrNull(),
                                    pro = pro.value.toFloatOrNull()
                                )
                                viewModel.updateUrineData(urineData)
                            } else {
                                viewModel.addUrineData(urineData)
                            }
                            navController.popBackStack()
                        } catch (e: NumberFormatException) {
                            Toast.makeText(context, "请确保所有输入为有效数字", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isSubmitting
                ) {
                    Text(if (isSubmitting) "提交中..." else "提交")
                }
            }
        }
    }
}
