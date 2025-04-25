package com.example.hrm.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
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
    ]
)
data class GeneralPhysical(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val sessionId: Int? = null,        // 外键，关联体检记录（可选）
    val date: Long = System.currentTimeMillis(), // 检查日期

    val height: Float?,                // 身高（cm）
    val weight: Float?,                // 体重（kg）
    val bmi: Float?,                   // 体质指数 BMI

    val bloodPressure: String?,        // 血压，例如 "120/80"
    val pulse: Int?,                   // 脉搏（次/分）

    val internalMedicine: String?,     // 内科结论
    val surgery: String?,              // 外科结论
    val ent: String?,                  // 耳鼻喉科
    val ophthalmology: String?,        // 眼科
    val dental: String?,               // 口腔科

    val summary: String?               // 总结或建议（如：建议复查）
)
