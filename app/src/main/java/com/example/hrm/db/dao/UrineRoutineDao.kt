package com.example.hrm.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hrm.db.entity.UrineRoutine
import kotlinx.coroutines.flow.Flow

@Dao
interface UrineRoutineDao {
    @Insert
    suspend fun insert(healthRecordData: UrineRoutine)

    @Query("SELECT * FROM urine_routine")
    fun getAll(): Flow<List<UrineRoutine>>

    @Delete
    suspend fun delete(healthRecordData: UrineRoutine)

    @Update
    suspend fun update(healthRecordData: UrineRoutine)
}