package com.example.hrm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ct_scan")
data class CtScan(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val sessionId: Int? = null,        // 外键，关联体检记录（可选）
    val date: Long = System.currentTimeMillis(), // 检查时间

    val imagePath: String?,            // CT 图像文件路径
    val result: String?,               // 检查结论（如：胸部 CT 未见明显异常）
    val suggestion: String?            // 医生建议或备注
)
