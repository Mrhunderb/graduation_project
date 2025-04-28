package com.example.hrm.screen.record

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun AddBloodScreen(
    onSave: (BloodTestData) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var wbc by remember { mutableStateOf("") }
    var rbc by remember { mutableStateOf("") }
    var hb by remember { mutableStateOf("") }
    var plt by remember { mutableStateOf("") }
    var neut by remember { mutableStateOf("") }
    var lymph by remember { mutableStateOf("") }

    var wbcError by remember { mutableStateOf<String?>(null) }
    var rbcError by remember { mutableStateOf<String?>(null) }
    var hbError by remember { mutableStateOf<String?>(null) }
    var pltError by remember { mutableStateOf<String?>(null) }
    var neutError by remember { mutableStateOf<String?>(null) }
    var lymphError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "填写血常规数据", style = MaterialTheme.typography.headlineSmall)

            BloodTestField(
                value = wbc,
                onValueChange = { wbc = it; wbcError = null },
                label = "白细胞 (WBC, x10⁹/L)",
                error = wbcError
            )
            BloodTestField(
                value = rbc,
                onValueChange = { rbc = it; rbcError = null },
                label = "红细胞 (RBC, x10¹²/L)",
                error = rbcError
            )
            BloodTestField(
                value = hb,
                onValueChange = { hb = it; hbError = null },
                label = "血红蛋白 (Hb, g/L)",
                error = hbError
            )
            BloodTestField(
                value = plt,
                onValueChange = { plt = it; pltError = null },
                label = "血小板 (PLT, x10⁹/L)",
                error = pltError
            )
            BloodTestField(
                value = neut,
                onValueChange = { neut = it; neutError = null },
                label = "中性粒细胞百分比 (NEUT%)",
                error = neutError
            )
            BloodTestField(
                value = lymph,
                onValueChange = { lymph = it; lymphError = null },
                label = "淋巴细胞百分比 (LYMPH%)",
                error = lymphError
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    var hasError = false

                    fun validateField(value: String, setError: (String?) -> Unit): Boolean {
                        return when {
                            value.isBlank() -> {
                                setError("请填写")
                                false
                            }
                            value.toFloatOrNull() == null -> {
                                setError("请输入正确的数字")
                                false
                            }
                            else -> true
                        }
                    }

                    hasError = !validateField(wbc) { wbcError = it } || hasError
                    hasError = !validateField(rbc) { rbcError = it } || hasError
                    hasError = !validateField(hb) { hbError = it } || hasError
                    hasError = !validateField(plt) { pltError = it } || hasError
                    hasError = !validateField(neut) { neutError = it } || hasError
                    hasError = !validateField(lymph) { lymphError = it } || hasError

                    if (hasError) {
                        scope.launch {
                            snackbarHostState.showSnackbar("请修正错误后再保存")
                        }
                    } else {
                        // TODO
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("保存")
            }
        }
    }
}

@Composable
private fun BloodTestField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: String?
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = error != null,
        supportingText = { if (error != null) Text(text = error, color = MaterialTheme.colorScheme.error) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

data class BloodTestData(
    val wbc: Float,
    val rbc: Float,
    val hb: Float,
    val plt: Float,
    val neut: Float,
    val lymph: Float
)
