package com.example.hrm.component.showcard

import androidx.compose.runtime.Composable
import com.example.hrm.component.DetailsCard
import com.example.hrm.component.IndicatorRow
import com.example.hrm.component.UrineIndicatorRow
import com.example.hrm.db.entity.UrineRoutine

@Composable
fun UrineDetails(data: UrineRoutine?) {
    val urineNormalRanges = mapOf(
        "KET" to (0f..0f),        // 尿酮体（应为阴性）
        "URO" to (0.2f..1.0f),    // 尿胆原
        "BIL" to (0f..0f),        // 尿胆红素
        "BLD" to (0f..0f),        // 潜血
        "WBC" to (0f..0f),        // 白细胞
        "PH" to (4.6f..8.0f),     // 尿PH
        "NIT" to (0f..0f),        // 亚硝酸盐
        "GLU" to (0f..0f),        // 尿糖
        "VC" to (0f..15f),        // VC可有
        "SG" to (1.005f..1.030f), // 尿比重
        "PRO" to (0f..0.15f),     // 蛋白质
    )
    DetailsCard(
        title = "尿常规检查",
    ) {
        UrineIndicatorRow("KET", data?.ket)
        UrineIndicatorRow("URO", data?.uro)
        UrineIndicatorRow("BIL", data?.bil)
        UrineIndicatorRow("BLD", data?.bld)
        UrineIndicatorRow("WBC", data?.wbc)
        UrineIndicatorRow("PH", data?.ph)
        UrineIndicatorRow("NIT", data?.nit)
        UrineIndicatorRow("GLU", data?.glu)
        UrineIndicatorRow("VC", data?.vc)
        UrineIndicatorRow("SG", data?.sg)
        UrineIndicatorRow("PRO", data?.pro)
    }
}
