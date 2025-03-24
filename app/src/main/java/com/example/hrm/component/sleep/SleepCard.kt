package com.example.hrm.component.sleep

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.unit.sp
import com.example.hrm.backgrounds.BubbleBackground
import com.example.hrm.ui.theme.SmallHeadingStyle

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hrm.R
import com.example.hrm.backgrounds.FadingCircleBackground
import com.example.hrm.data.WellnessData
import com.example.hrm.ui.theme.HeadingStyle
import com.example.hrm.ui.theme.JetLaggedTheme


@Composable
fun BasicInformationalCard(
    modifier: Modifier = Modifier,
    borderColor: Color,
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(24.dp)
    Card(
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = JetLaggedTheme.extraColors.cardBackground
        ),
        modifier = modifier
            .padding(8.dp),
        border = BorderStroke(2.dp, borderColor)
    ) {
        Box {
            content()
        }
    }
}


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun TwoLineInfoCard(
    borderColor: Color,
    firstLineText: String,
    secondLineText: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    BasicInformationalCard(
        borderColor = borderColor,
        modifier = modifier.size(200.dp)
    ) {
        BubbleBackground(
            modifier = Modifier.fillMaxSize(),
            numberBubbles = 3, bubbleColor = borderColor.copy(0.25f)
        )
        BoxWithConstraints(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            if (maxWidth > 400.dp) {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(CenterStart)
                ) {
                    Icon(
                        icon, contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .align(CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        modifier = Modifier
                            .align(CenterVertically)
                            .wrapContentSize()
                    ) {
                        Text(
                            firstLineText,
                            style = SmallHeadingStyle
                        )
                        Text(
                            secondLineText,
                            style = HeadingStyle,
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Center)
                ) {
                    Icon(
                        icon, contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .align(CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(modifier = Modifier.align(CenterHorizontally)) {
                        Text(
                            firstLineText,
                            style = SmallHeadingStyle,
                            modifier = Modifier.align(CenterHorizontally)
                        )
                        Text(
                            secondLineText,
                            style = HeadingStyle,
                            modifier = Modifier.align(CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreenCardHeading(text: String) {
    Text(
        text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        textAlign = TextAlign.Center,
        style = HeadingStyle
    )
}


@Preview
@Preview(widthDp = 500, name = "larger screen")
@Composable
fun AverageTimeInBedCard(modifier: Modifier = Modifier) {
    TwoLineInfoCard(
        borderColor = JetLaggedTheme.extraColors.bed,
        firstLineText = stringResource(R.string.ave_time_in_bed_heading),
        secondLineText = "8h42min",
        icon = Icons.Default.Star,
        modifier = modifier
            .wrapContentWidth()
            .heightIn(min = 156.dp)
    )
}

@Preview
@Preview(widthDp = 500, name = "larger screen")
@Composable
fun AverageTimeAsleepCard(modifier: Modifier = Modifier) {
    TwoLineInfoCard(
        borderColor = JetLaggedTheme.extraColors.sleep,
        firstLineText = stringResource(R.string.heart_rate_heading),
        secondLineText = "7h42min",
        icon = Icons.Default.Phone,
        modifier = modifier
            .wrapContentWidth()
            .heightIn(min = 156.dp)
    )
}

@Composable
fun WellnessBubble(
    titleText: String,
    countText: String,
    metric: String,
    modifier: Modifier = Modifier,
    bubbleColor: Color = JetLaggedTheme.extraColors.wellness
) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .sizeIn(maxHeight = 100.dp)
            .aspectRatio(1f)
            .drawBehind {
                drawCircle(bubbleColor)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text(titleText, fontSize = 12.sp)
        Text(countText, fontSize = 36.sp)
        Text(metric, fontSize = 12.sp)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun WellnessCard(
    modifier: Modifier = Modifier,
    wellnessData: WellnessData = WellnessData(0, 0, 0)
) {
    BasicInformationalCard(
        borderColor = JetLaggedTheme.extraColors.wellness,
        modifier = modifier
            .widthIn(max = 400.dp)
            .heightIn(min = 200.dp)
    ) {
        FadingCircleBackground(36.dp, JetLaggedTheme.extraColors.wellness.copy(0.25f))
        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            HomeScreenCardHeading(text = stringResource(R.string.wellness_heading))
            FlowRow(
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                WellnessBubble(
                    titleText = stringResource(R.string.snoring_heading),
                    countText = wellnessData.snoring.toString(),
                    metric = "min"
                )
                WellnessBubble(
                    titleText = stringResource(R.string.coughing_heading),
                    countText = wellnessData.coughing.toString(),
                    metric = "times"
                )
                WellnessBubble(
                    titleText = stringResource(R.string.respiration_heading),
                    countText = wellnessData.respiration.toString(),
                    metric = "rpm"
                )
            }
        }
    }
}

