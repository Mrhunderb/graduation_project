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

    var sessionId: Long = 0,        // 外键，关联体检记录（可选）
    var date: Long = System.currentTimeMillis(), // 检查日期

    var height: Float?,                // 身高（cm）
    var weight: Float?,                // 体重（kg）
    var bmi: Float?,                   // 体质指数 BMI

    var systolicPressure: Int?,        // 收缩压（mmHg）
    var diastolicPressure: Int?,       // 舒张压（mmHg）
    var pulse: Int?,                   // 脉搏（次/分）

    var leftEyeVision: Float?,         // 左眼视力（如：1.0）
    var rightEyeVision: Float?,        // 右眼视力（如：1.0）

    var internalMedicine: String?,     // 内科结论
    var surgery: String?,              // 外科结论
    var ent: String?,                  // 耳鼻喉科
    var dental: String?,               // 口腔科

    var summary: String?               // 总结或建议（如：建议复查）
)
