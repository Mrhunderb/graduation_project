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
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    var date: Long = System.currentTimeMillis(), // 检查时间（时间戳）
    val sessionId: Long = 0,                      // 对应体检记录ID（外键）

    var ket: Float?,      // 尿酮体（KET）
    var uro: Float?,      // 尿胆原（URO）
    var bil: Float?,      // 尿胆红素（BIL）
    var bld: Float?,      // 尿潜血（BLD）
    var wbc: Float?,      // 尿白细胞（WBC）
    var ph: Float?,       // 酸碱度（PH）
    var nit: Float?,      // 亚硝酸盐（NIT）
    var glu: Float?,      // 尿葡萄糖（GLU）
    var vc: Float?,       // VC（维生素C）
    var sg: Float?,       // 比重（SG）
    var pro: Float?,      // 尿蛋白质（PRO）

    val note: String? = null                  // 小结（比如“未见明显异常”）
)
