package com.example.hrm.screen.record

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGeneralScreen(
    navController: NavController,
    id: Long
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

    var bloodPressure by remember { mutableStateOf("") }
    var pulse by remember { mutableStateOf("") }

    var internalMedicine by remember { mutableStateOf("") }
    var surgery by remember { mutableStateOf("") }
    var ent by remember { mutableStateOf("") }
    var ophthalmology by remember { mutableStateOf("") }
    var dental by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("新增常规项目") },
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
            OutlinedTextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("身高（cm）") })
            OutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("体重（kg）") })
            Text("BMI: ${bmi?.let { String.format("%.2f", it) } ?: "—"}",
                modifier = Modifier.padding(8.dp))

            OutlinedTextField(
                value = bloodPressure,
                onValueChange = { bloodPressure = it },
                label = { Text("血压（如 120/80）") })
            OutlinedTextField(
                value = pulse,
                onValueChange = { pulse = it },
                label = { Text("脉搏（次/分）") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = internalMedicine,
                onValueChange = { internalMedicine = it },
                label = { Text("内科") })
            OutlinedTextField(
                value = surgery,
                onValueChange = { surgery = it },
                label = { Text("外科") })
            OutlinedTextField(
                value = ent,
                onValueChange = { ent = it },
                label = { Text("耳鼻喉科") })
            OutlinedTextField(
                value = ophthalmology,
                onValueChange = { ophthalmology = it },
                label = { Text("眼科") })
            OutlinedTextField(
                value = dental,
                onValueChange = { dental = it },
                label = { Text("口腔科") })

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
            }) {
                Text("提交")
            }
        }
    }
}
