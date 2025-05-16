package com.example.hrm.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "ct_scan",
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
data class CtScan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    var sessionId: Long = 0,          // 外键，关联体检记录（可选）
    var date: Long = System.currentTimeMillis(), // 检查时间

    var imagePath: String?,            // CT 图像文件路径
    var result: String?,               // 检查结论（如：胸部 CT 未见明显异常）
)
