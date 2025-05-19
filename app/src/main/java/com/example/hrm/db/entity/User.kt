package com.example.hrm.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user",
)
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    var name: String = "",  // 姓名
    var age: Int = 0,       // 年龄
    var gender: String = "" // 性别
)
