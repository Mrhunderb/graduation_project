package com.example.hrm.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "health_record")
data class HealthRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: Long = System.currentTimeMillis(), // 体检时间（用时间戳）
    val hospital: String,                        // 医院名（可选）
    val note: String? = null                     // 备注，比如“年度体检”或“复查”
)