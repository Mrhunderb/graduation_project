package com.example.hrm.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hrm.db.entity.HealthRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface HealthRecordDao {
    @Insert
    suspend fun insert(healthRecordData: HealthRecord): Long

    @Query("SELECT * FROM health_record")
    fun getAll(): Flow<List<HealthRecord>>

    @Delete
    suspend fun delete(healthRecordData: HealthRecord)

    @Update
    suspend fun update(healthRecordData: HealthRecord)
}