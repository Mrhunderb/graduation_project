package com.example.hrm.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "general_physical",
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
data class GeneralPhysical(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    val sessionId: Long = 0,        // 外键，关联体检记录（可选）
    var date: Long = System.currentTimeMillis(), // 检查日期

    val height: Float?,                // 身高（cm）
    val weight: Float?,                // 体重（kg）
    val bmi: Float?,                   // 体质指数 BMI

    val systolicPressure: Int?,        // 收缩压（mmHg）
    val diastolicPressure: Int?,       // 舒张压（mmHg）
    val pulse: Int?,                   // 脉搏（次/分）

    val leftEyeVision: Float?,         // 左眼视力（如：1.0）
    val rightEyeVision: Float?,        // 右眼视力（如：1.0）

    val internalMedicine: String?,     // 内科结论
    val surgery: String?,              // 外科结论
    val ent: String?,                  // 耳鼻喉科
    val dental: String?,               // 口腔科

    val summary: String?               // 总结或建议（如：建议复查）
)
