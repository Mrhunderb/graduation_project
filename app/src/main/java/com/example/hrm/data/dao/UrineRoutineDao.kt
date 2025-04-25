package com.example.hrm.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hrm.data.entity.UrineRoutine

@Dao
interface UrineRoutineDao {
    @Insert
    suspend fun insertHealthRecordData(healthRecordData: UrineRoutine)

    @Query("SELECT * FROM urine_routine")
    suspend fun getAllHealthRecordData(): List<UrineRoutine>

    @Delete
    suspend fun deleteHealthRecordData(healthRecordData: UrineRoutine)

    @Update
    suspend fun updateHealthRecordData(healthRecordData: UrineRoutine)
}