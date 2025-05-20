package com.example.hrm.db.dao

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

    @Query("SELECT * FROM health_record ORDER BY date DESC")
    fun getAll(): Flow<List<HealthRecord>>

    @Query("SELECT * FROM health_record WHERE id = :id")
    suspend fun getById(id: Long): HealthRecord?

    @Query("SELECT * FROM health_record ORDER BY date DESC LIMIT 1")
    fun getLatest(): Flow<HealthRecord?>

    @Query("SELECT * FROM health_record ORDER BY date DESC LIMIT 1")
    suspend fun getLatestRecord(): HealthRecord?

    @Delete
    suspend fun delete(healthRecordData: HealthRecord)

    @Query("DELETE FROM health_record WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(healthRecordData: HealthRecord)
}