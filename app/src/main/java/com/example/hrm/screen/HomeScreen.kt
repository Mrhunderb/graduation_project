package com.example.hrm.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hrm.backgrounds.movingStripesBackground
import com.example.hrm.component.sleep.AverageTimeAsleepCard
import com.example.hrm.component.sleep.AverageTimeInBedCard
import com.example.hrm.component.sleep.JetLaggedHeader
import com.example.hrm.component.sleep.JetLaggedSleepGraphCard
import com.example.hrm.component.sleep.WellnessCard
import com.example.hrm.data.JetLaggedHomeScreenViewModel
import com.example.hrm.heartrate.HeartRateCard
import com.example.hrm.ui.theme.JetLaggedTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: JetLaggedHomeScreenViewModel = viewModel(),
    onDrawerClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.movingStripesBackground(
                stripeColor = JetLaggedTheme.extraColors.header,
                backgroundColor = MaterialTheme.colorScheme.background,
            )
        ) {
            JetLaggedHeader(
                modifier = Modifier.fillMaxWidth(),
                onDrawerClicked = onDrawerClicked
            )
        }

        val uiState =
            viewModel.uiState.collectAsStateWithLifecycle()
        val insets = WindowInsets.safeDrawing.only(
            WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal
        )
        FlowRow(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(insets),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
            maxItemsInEachRow = 3
        ) {
            JetLaggedSleepGraphCard(uiState.value.sleepGraphData, Modifier.widthIn(max = 600.dp))
            AverageTimeInBedCard()
            AverageTimeAsleepCard()
            WellnessCard(
                wellnessData = uiState.value.wellnessData,
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .heightIn(min = 200.dp)
            )
            HeartRateCard(
                modifier = Modifier.widthIn(max = 400.dp, min = 200.dp),
                uiState.value.heartRateData
            )
        }
    }
}

