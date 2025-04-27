package com.example.hrm.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hrm.db.entity.HealthRecord

@Dao
interface HealthRecordDao {
    @Insert
    suspend fun insert(healthRecordData: HealthRecord)

    @Query("SELECT * FROM health_record")
    suspend fun getAll(): List<HealthRecord>

    @Delete
    suspend fun delete(healthRecordData: HealthRecord)

    @Update
    suspend fun update(healthRecordData: HealthRecord)
}