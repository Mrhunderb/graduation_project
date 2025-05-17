package com.example.hrm.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun RowItem(
    label: String,
    value: String,
    color: Color = LocalContentColor.current,
    bold: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = color,
                fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal
            )
        )
    }
}


@Composable
fun IndicatorRow(label: String, value: Float?, unit: String, normalRange: ClosedFloatingPointRange<Float>) {
    val (color, arrow) = when {
        value == null -> Color.Gray to ""
        value < normalRange.start -> Color.Blue to "↓"
        value > normalRange.endInclusive -> Color.Red to "↑"
        else -> Color.Unspecified to ""
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, modifier = Modifier.weight(1f))
        Text(
            text = if (value != null) "$value $unit $arrow" else "N/A",
            color = color,
            fontWeight = if (arrow.isNotEmpty()) FontWeight.Bold else FontWeight.Normal
        )
    }
}

data class UrineRange(
    val label: String,
    val unit: String = "",
    val normalRange: ClosedFloatingPointRange<Float>? = null,
    val abnormalIfNonZero: Boolean = false // 对于如亚硝酸盐、潜血等，只要不为0就算异常
)

@Composable
fun UrineIndicatorRow(key: String, value: Float?) {
    val urineRanges = mapOf(
        "KET" to UrineRange("尿酮体 KET", "mmol/L", 0f..0f, abnormalIfNonZero = true),
        "URO" to UrineRange("尿胆原 URO", "umol/L", 0.2f..1.0f),
        "BIL" to UrineRange("尿胆红素 BIL", "umol/L", 0f..0f, abnormalIfNonZero = true),
        "BLD" to UrineRange("尿潜血 BLD", "", 0f..0f, abnormalIfNonZero = true),
        "WBC" to UrineRange("尿白细胞 WBC", "", 0f..0f, abnormalIfNonZero = true),
        "PH" to UrineRange("酸碱度 PH", "", 4.6f..8.0f),
        "NIT" to UrineRange("亚硝酸盐NIT", "", 0f..0f, abnormalIfNonZero = true),
        "GLU" to UrineRange("尿葡萄糖 GLU", "mmol/L", 0f..0f, abnormalIfNonZero = true),
        "VC"  to UrineRange("VC", "mg/dL", 0f..15f),
        "SG"  to UrineRange("比重 SG", "", 1.005f..1.030f),
        "PRO" to UrineRange("尿蛋白质 PRO", "g/L", 0f..0.15f)
    )
    val info = urineRanges[key] ?: return

    val (color, arrow) = when {
        value == null -> Color.Gray to ""
        info.abnormalIfNonZero && value != 0f -> Color.Red to "↑"
        info.normalRange != null && value < info.normalRange.start -> Color.Blue to "↓"
        info.normalRange != null && value > info.normalRange.endInclusive -> Color.Red to "↑"
        else -> Color.Unspecified to ""
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = info.label, modifier = Modifier.weight(1f))
        Text(
            text = if (value != null) "$value ${info.unit} $arrow" else "N/A",
            color = color,
            fontWeight = if (arrow.isNotEmpty()) FontWeight.Bold else FontWeight.Normal
        )
    }
}