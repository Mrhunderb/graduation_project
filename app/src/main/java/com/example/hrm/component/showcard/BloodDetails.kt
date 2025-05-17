package com.example.hrm.component.showcard

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hrm.component.DetailsCard
import com.example.hrm.component.IndicatorRow
import com.example.hrm.db.entity.BloodData


@Composable
fun BloodDetails(data: BloodData?) {
    DetailsCard(
        title = "血常规数据",
        content = {
            Text("白细胞指标", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            IndicatorRow("白细胞 WBC", data?.wbc, "10⁹/L", 3.5f..9.5f)
            IndicatorRow("中性粒细胞 %", data?.granPercent, "%", 40f..75f)
            IndicatorRow("淋巴细胞 %", data?.lymPercent, "%", 20f..50f)
            IndicatorRow("单核细胞 %", data?.monoPercent, "%", 3f..10f)
            IndicatorRow("嗜酸性粒细胞 %", data?.eosPercent, "%", 0.4f..8.0f)
            IndicatorRow("嗜碱性粒细胞 %", data?.basoPercent, "%", 0f..1f)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = LocalContentColor.current.copy(alpha = 0.2f)
            )
            Text("红细胞指标", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            IndicatorRow("红细胞 RBC", data?.rbc, "10¹²/L", 4.3f..5.8f)
            IndicatorRow("血红蛋白 Hb", data?.hb, "g/L", 130f..175f)
            IndicatorRow("红细胞比容 HCT", data?.hct, "%", 40f..50f)
            IndicatorRow("平均红细胞体积 MCV", data?.mcv, "fL", 82f..100f)

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = LocalContentColor.current.copy(alpha = 0.2f)
            )
            Text("血小板指标", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            IndicatorRow("血小板 PLT", data?.plt, "10⁹/L", 125f..350f)
            IndicatorRow("平均血小板体积 MPV", data?.mpv, "fL", 8.4f..12.7f)
            IndicatorRow("血小板压积 PCT", data?.pct, "", 0.17f..0.37f)
        }
    )
}

