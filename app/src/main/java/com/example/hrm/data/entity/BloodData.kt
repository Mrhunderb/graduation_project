package com.example.hrm.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "blood_data",
    foreignKeys = [
        ForeignKey(
            entity = HealthRecord::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class BloodData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: Long = System.currentTimeMillis(), // 检查时间戳
    val sessionId: Int = 0,                      // 对应体检记录ID（外键）

    // 白细胞相关
    val wbc: String,         // 白细胞计数 WBC (10^9/L)
    val granPercent: String, // 中性粒细胞百分比 GRAN%
    val lymPercent: String,  // 淋巴细胞百分比 LYM%
    val monoPercent: String, // 单核细胞百分比 Mono%
    val eosPercent: String,  // 嗜酸性粒细胞百分比 Eos%
    val basoPercent: String, // 嗜碱性粒细胞百分比 Baso%

    // 白细胞绝对值
    val granAbs: String,     // 中性粒细胞绝对值 GRAN# (10^9/L)
    val lymAbs: String,      // 淋巴细胞绝对值 LYM# (10^9/L)
    val monoAbs: String,     // 单核细胞绝对值 Mono# (10^9/L)
    val eosAbs: String,      // 嗜酸性粒细胞绝对值 Eos# (10^9/L)
    val basoAbs: String,     // 嗜碱性粒细胞绝对值 Baso# (10^9/L)

    // 红细胞相关
    val rbc: String,         // 红细胞计数 RBC (10^12/L)
    val hb: String,          // 血红蛋白 Hb (g/L)
    val hct: String,         // 红细胞比容 HCT (%)
    val mcv: String,         // 平均红细胞体积 MCV (fL)
    val mch: String,         // 平均血红蛋白含量 MCH (pg)
    val mchc: String,        // 平均血红蛋白浓度 MCHC (g/L)
    val rdwSd: String,       // 红细胞分布宽度-标准差 RDW-SD (fL)
    val rdwCv: String,       // 红细胞分布宽度-变异系数 RDW-CV (%)

    // 血小板相关
    val plt: String,         // 血小板计数 PLT (10^9/L)
    val mpv: String,         // 平均血小板体积 MPV (fL)
    val pct: String,         // 血小板压积 PCT
    val pdw: String,         // 血小板分布宽度 PDW (fL)
    val plcr: String,        // 大血小板比率 P-LCR

    val note: String? = null
)
