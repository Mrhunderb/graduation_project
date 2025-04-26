package com.example.hrm.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "urine_routine",
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
data class UrineRoutine(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val date: Long = System.currentTimeMillis(), // 检查时间（时间戳）
    val sessionId: Int = 0,                      // 对应体检记录ID（外键）

    val ket: String,      // 尿酮体（KET）
    val uro: String,      // 尿胆原（URO）
    val bil: String,      // 尿胆红素（BIL）
    val bld: String,      // 尿潜血（BLD）
    val wbc: String,      // 尿白细胞（WBC）
    val ph: String,       // 酸碱度（PH）
    val nit: String,      // 亚硝酸盐（NIT）
    val glu: String,      // 尿葡萄糖（GLU）
    val vc: String,       // VC（维生素C）
    val sg: String,       // 比重（SG）
    val pro: String,      // 尿蛋白质（PRO）

    val note: String? = null                  // 小结（比如“未见明显异常”）
)
