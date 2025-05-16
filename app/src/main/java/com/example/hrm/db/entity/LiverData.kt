package com.example.hrm.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "liver_data",
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
data class LiverData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    var date: Long = System.currentTimeMillis(), // 检查时间戳
    var sessionId: Long = 0, // 对应体检记录ID（外键）

    // 肝功能相关
    var ast: Float?, // 天冬氨酸氨基转移酶 (AST) (U/L)
    var alt: Float?, // 丙氨酸氨基转移酶 (ALT) (U/L)

    var note: String? = null
)