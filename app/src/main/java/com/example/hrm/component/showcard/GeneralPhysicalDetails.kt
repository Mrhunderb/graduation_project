package com.example.hrm.component.showcard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hrm.component.DetailsCard
import com.example.hrm.component.RowItem
import com.example.hrm.db.entity.GeneralPhysical

@Composable
fun GeneralPhysicalDetails(data: GeneralPhysical?) {
    DetailsCard (
        title = "基础体检数据",
        content = {
            RowItem(label = "身高", value = "${data?.height ?: "N/A"} cm")
            RowItem(label = "体重", value = "${data?.weight ?: "N/A"} kg")
            RowItem(label = "BMI", value = "${data?.bmi ?: "N/A"}")

            if (data?.bmi != null) {
                val status = when {
                    data.bmi!! > 24.0 -> "偏胖"
                    data.bmi!! < 18.5 -> "偏瘦"
                    else -> "正常"
                }
                val color = when {
                    data.bmi!! > 24.0 -> Color.Red
                    data.bmi!! < 18.5 -> Color.Blue
                    else -> MaterialTheme.colorScheme.primary
                }
                RowItem(label = "BMI评估", value = status, color = color, bold = true)
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = LocalContentColor.current.copy(alpha = 0.2f)
            )
            RowItem(label = "血压收缩压", value = "${data?.systolicPressure ?: "N/A"} mmHg")
            RowItem(label = "血压舒张压", value = "${data?.diastolicPressure ?: "N/A"} mmHg")
            RowItem(label = "脉搏", value = "${data?.pulse ?: "N/A"} bpm")

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = LocalContentColor.current.copy(alpha = 0.2f)
            )
            RowItem(label = "左眼视力", value = "${data?.leftEyeVision ?: "N/A"}")
            RowItem(label = "右眼视力", value = "${data?.rightEyeVision ?: "N/A"}")
        }
    )
}

