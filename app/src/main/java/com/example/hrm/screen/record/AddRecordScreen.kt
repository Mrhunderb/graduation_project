package com.example.hrm.screen.record

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hrm.component.DatePickerField
import com.example.hrm.db.HealthViewModel
import java.sql.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecordScreen(
    navController: NavController,
    viewModel: HealthViewModel = viewModel()
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("添加体检报告") },
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
    ) { padding -> Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var hospitalName by rememberSaveable { mutableStateOf("") }
            var date by rememberSaveable { mutableStateOf(java.util.Date(0)) }

            Text("请选择体检日期及地点", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.padding(16.dp))

            DatePickerField(
                onDateSelected = {
                    date = it
                }
            )

            Spacer(modifier = Modifier.padding(16.dp))

            OutlinedTextField(
                value = hospitalName,
                onValueChange = { hospitalName = it },
                label = { Text("请输入体检医院的名称") },
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Button(
                onClick = {
                    viewModel.addRecordLaunch(
                        date, hospitalName, onComplete = {
                            val id = it
                            navController.navigate("add_select/$id/false")
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("继续")
            }
        }
    }
}