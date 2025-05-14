package com.example.hrm.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ecg",
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
data class Ecg(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    val sessionId: Long? = null,        // 外键，关联体检记录（可选）
    val date: Long = System.currentTimeMillis(), // 检查时间

    val imagePath: String?,            // 心电图图像文件路径（本地路径或 URI）
    val result: String?,               // 检查结论（如：窦性心律，未见明显异常）
    val suggestion: String?            // 医生建议或备注
)
