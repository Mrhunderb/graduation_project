package com.example.hrm.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
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
    ],
    indices = [Index(value = ["sessionId"])]
)
data class BloodData(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var date: Long = System.currentTimeMillis(), // 检查时间戳
    val sessionId: Long = 0,                      // 对应体检记录ID（外键）

    // 白细胞相关
    val wbc: Float?,         // 白细胞计数 WBC (10^9/L)
    val granPercent: Float?, // 中性粒细胞百分比 GRAN%
    val lymPercent: Float?,  // 淋巴细胞百分比 LYM%
    val monoPercent: Float?, // 单核细胞百分比 Mono%
    val eosPercent: Float?,  // 嗜酸性粒细胞百分比 Eos%
    val basoPercent: Float?, // 嗜碱性粒细胞百分比 Baso%

    // 白细胞绝对值
    val granAbs: Float?,     // 中性粒细胞绝对值 GRAN# (10^9/L)
    val lymAbs: Float?,      // 淋巴细胞绝对值 LYM# (10^9/L)
    val monoAbs: Float?,     // 单核细胞绝对值 Mono# (10^9/L)
    val eosAbs: Float?,      // 嗜酸性粒细胞绝对值 Eos# (10^9/L)
    val basoAbs: Float?,     // 嗜碱性粒细胞绝对值 Baso# (10^9/L)

    // 红细胞相关
    val rbc: Float?,         // 红细胞计数 RBC (10^12/L)
    val hb: Float?,          // 血红蛋白 Hb (g/L)
    val hct: Float?,         // 红细胞比容 HCT (%)
    val mcv: Float?,         // 平均红细胞体积 MCV (fL)
    val mch: Float?,         // 平均血红蛋白含量 MCH (pg)
    val mchc: Float?,        // 平均血红蛋白浓度 MCHC (g/L)
    val rdwSd: Float?,       // 红细胞分布宽度-标准差 RDW-SD (fL)
    val rdwCv: Float?,       // 红细胞分布宽度-变异系数 RDW-CV (%)

    // 血小板相关
    val plt: Float?,         // 血小板计数 PLT (10^9/L)
    val mpv: Float?,         // 平均血小板体积 MPV (fL)
    val pct: Float?,         // 血小板压积 PCT
    val pdw: Float?,         // 血小板分布宽度 PDW (fL)
    val plcr: Float?,        // 大血小板比率 P-LCR

    val note: String? = null
)
