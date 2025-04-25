package com.example.hrm.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hrm.data.entity.HealthRecord

@Dao
interface HealthRecordDao {
    @Insert
    suspend fun insertHealthRecordData(healthRecordData: HealthRecord)

    @Query("SELECT * FROM health_record")
    suspend fun getAllHealthRecordData(): List<HealthRecord>

    @Delete
    suspend fun deleteHealthRecordData(healthRecordData: HealthRecord)

    @Update
    suspend fun updateHealthRecordData(healthRecordData: HealthRecord)
}