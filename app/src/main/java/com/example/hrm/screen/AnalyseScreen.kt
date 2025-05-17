package com.example.hrm.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hrm.db.HealthViewModel
import com.example.hrm.db.entity.GeneralPhysical
import com.example.hrm.component.RowItem
import com.example.hrm.component.showcard.BloodDetails
import com.example.hrm.component.showcard.GeneralPhysicalDetails
import com.example.hrm.component.showcard.UrineDetails
import com.example.hrm.db.entity.BloodData
import com.example.hrm.db.entity.UrineRoutine

@Composable
fun AnalyseScreen(
    navController: NavController,
    viewModel: HealthViewModel = viewModel()
) {
    val data by viewModel.latestRecord.collectAsState()
    var generalData by remember { mutableStateOf<GeneralPhysical?>(null) }
    var bloodData by remember { mutableStateOf<BloodData?>(null) }
    var urineData by remember { mutableStateOf<UrineRoutine?>(null) }

    LaunchedEffect(data) {
        data?.let { record ->
            viewModel.getGeneralDataById(record.id) {
                generalData = it
            }
            viewModel.getBloodDataById(record.id) {
                bloodData = it
            }
            viewModel.getUrineDataById(record.id) {
                urineData = it
            }
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (data == null) {
            Text(
                text = "暂无数据",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        } else {
            GeneralPhysicalDetails(generalData)
            Spacer(modifier = Modifier.height(16.dp))
            BloodDetails(bloodData)
            Spacer(modifier = Modifier.height(16.dp))
            UrineDetails(urineData)
        }
    }
}

