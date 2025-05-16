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

    var sessionId: Long = 0,          // 外键，关联体检记录（可选）
    var date: Long = System.currentTimeMillis(), // 检查时间

    var heartRate: Int?,               // 心率（单位：次/分钟）
    var qrs: Int?,                     // QRS 波群持续时间（单位：毫秒）
    var prInterval: Int?,              // PR 间期（单位：毫秒）
    var qtInterval: Int?,              // QT 间期（单位：毫秒）
    var qtcInterval: Int?,             // QTc 间期（单位：毫秒）
    var imagePath: String?,            // 心电图图像文件路径（本地路径或 URI）
    var result: String?,               // 检查结论（如：窦性心律，未见明显异常）
)
