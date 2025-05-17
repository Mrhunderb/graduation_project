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