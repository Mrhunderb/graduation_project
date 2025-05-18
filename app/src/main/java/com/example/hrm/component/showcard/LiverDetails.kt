package com.example.hrm.component.showcard

import androidx.compose.runtime.Composable
import com.example.hrm.component.DetailsCard
import com.example.hrm.component.IndicatorRow
import com.example.hrm.db.entity.LiverData

@Composable
fun LiverDetails(data: LiverData?) {
    DetailsCard(
        title = "肝功能检查",
    ) {
        IndicatorRow(label = "谷丙转氨酶 AST", value = data?.ast, unit = "U/L", 0f..40f)
        IndicatorRow(label = "谷草转氨酶 ALT", value = data?.alt, unit = "U/L", 0f..45f)
    }
}