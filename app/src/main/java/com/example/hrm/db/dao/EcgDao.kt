package com.example.hrm.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hrm.db.entity.Ecg
import kotlinx.coroutines.flow.Flow

@Dao
interface EcgDao {
    @Insert
    suspend fun insert(ctScanData: Ecg)

    @Query("SELECT * FROM ecg")
    fun getAll(): Flow<List<Ecg>>

    @Delete
    suspend fun delete(ctScanData: Ecg)

    @Update
    suspend fun update(ctScanData: Ecg)
}