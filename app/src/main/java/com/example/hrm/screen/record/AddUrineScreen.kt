package com.example.hrm.screen.record

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUrineScreen(
    navController: NavController
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("新增尿常规") },
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
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text("请输入尿液检测数据", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))

            fields.forEach { (label, state) ->
                OutlinedTextField(
                    value = state.value,
                    onValueChange = { state.value = it },
                    label = { Text(label) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    try {
                        // TODO: Handle the data submission
                    } catch (e: NumberFormatException) {
                        Toast.makeText(context, "请确保所有输入为有效数字", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("提交")
            }
        }
    }
}
