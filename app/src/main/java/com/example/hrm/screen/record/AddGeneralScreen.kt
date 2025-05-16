package com.example.hrm.screen.record

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hrm.db.HealthViewModel
import com.example.hrm.db.entity.GeneralPhysical

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGeneralScreen(
    navController: NavController,
    id: Long,
    viewModel: HealthViewModel = viewModel()
) {
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    val bmi = remember(height, weight) {
        val h = height.toFloatOrNull()
        val w = weight.toFloatOrNull()
        if (h != null && h > 0 && w != null) {
            w / ((h / 100) * (h / 100))
        } else null
    }

    var systolicPressure by remember { mutableStateOf("") }
    var diastolicPressure by remember { mutableStateOf("") }
    var pulse by remember { mutableStateOf("") }

    var leftEyeVision by remember { mutableStateOf("") }
    var rightEyeVision by remember { mutableStateOf("") }

    var internalMedicine by remember { mutableStateOf("") }
    var surgery by remember { mutableStateOf("") }
    var ent by remember { mutableStateOf("") }
    var dental by remember { mutableStateOf("") }
    var summary by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(true) }
    var isModified by remember { mutableStateOf(false) }
    var data by remember { mutableStateOf<GeneralPhysical?>(null) }

    LaunchedEffect(id) {
        viewModel.getGeneralDataById(
            id,
            onComplete = {
                if (it != null) {
                    isModified = true
                    data = it
                    height = it.height?.toString() ?: ""
                    weight = it.weight?.toString() ?: ""
                    systolicPressure = it.systolicPressure?.toString() ?: ""
                    diastolicPressure = it.diastolicPressure?.toString() ?: ""
                    pulse = it.pulse?.toString() ?: ""
                    leftEyeVision = it.leftEyeVision?.toString() ?: ""
                    rightEyeVision = it.rightEyeVision?.toString() ?: ""
                    internalMedicine = it.internalMedicine ?: ""
                    surgery = it.surgery ?: ""
                    ent = it.ent ?: ""
                    dental = it.dental ?: ""
                    summary = it.summary ?: ""
                }
                isLoading = false
            }
        )
    }


    var showBackConfirmDialog by remember { mutableStateOf(false) }
    var isSubmitting by remember { mutableStateOf(false) }

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
                    title = { Text("新增常规项目") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    navigationIcon = {
                        IconButton(onClick = { showBackConfirmDialog = true }) {
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
                Text("基础生理指标", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = height,
                    onValueChange = { height = it },
                    label = { Text("身高（cm）") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("体重（kg）") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Text(
                    "BMI: ${bmi?.let { String.format("%.2f", it) } ?: "—"}",
                    modifier = Modifier.padding(8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("心血管指标", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    OutlinedTextField(
                        value = systolicPressure,
                        onValueChange = { systolicPressure = it },
                        label = { Text("收缩压") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f).padding(end = 4.dp)
                    )

                    OutlinedTextField(
                        value = diastolicPressure,
                        onValueChange = { diastolicPressure = it },
                        label = { Text("舒张压") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f).padding(start = 4.dp)
                    )
                }

                OutlinedTextField(
                    value = pulse,
                    onValueChange = { pulse = it },
                    label = { Text("脉搏（次/分）") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("视力", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    OutlinedTextField(
                        value = leftEyeVision,
                        onValueChange = { leftEyeVision = it },
                        label = { Text("左眼视力") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f).padding(end = 4.dp)
                    )

                    OutlinedTextField(
                        value = rightEyeVision,
                        onValueChange = { rightEyeVision = it },
                        label = { Text("右眼视力") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f).padding(start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("科室检查结论", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = internalMedicine,
                    onValueChange = { internalMedicine = it },
                    label = { Text("内科") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )

                OutlinedTextField(
                    value = surgery,
                    onValueChange = { surgery = it },
                    label = { Text("外科") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )

                OutlinedTextField(
                    value = ent,
                    onValueChange = { ent = it },
                    label = { Text("耳鼻喉科") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )

                OutlinedTextField(
                    value = dental,
                    onValueChange = { dental = it },
                    label = { Text("口腔科") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )

                OutlinedTextField(
                    value = summary,
                    onValueChange = { summary = it },
                    label = { Text("总结建议") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        isSubmitting = true
                        var generalPhysical = GeneralPhysical(
                            sessionId = id,
                            height = height.toFloatOrNull(),
                            weight = weight.toFloatOrNull(),
                            bmi = bmi,
                            systolicPressure = systolicPressure.toIntOrNull(),
                            diastolicPressure = diastolicPressure.toIntOrNull(),
                            pulse = pulse.toIntOrNull(),
                            leftEyeVision = leftEyeVision.toFloatOrNull(),
                            rightEyeVision = rightEyeVision.toFloatOrNull(),
                            internalMedicine = internalMedicine.ifBlank { null },
                            surgery = surgery.ifBlank { null },
                            ent = ent.ifBlank { null },
                            dental = dental.ifBlank { null },
                            summary = summary.ifBlank { null }
                        )
                        if (isModified) {
                            generalPhysical = generalPhysical.copy(id = data?.id ?: 0)
                            viewModel.updateGeneralData(generalPhysical)
                        } else {
                            viewModel.addGeneralData(generalPhysical)
                        }
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isSubmitting
                ) {
                    Text(if (isSubmitting) "保存中..." else "保存")
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
