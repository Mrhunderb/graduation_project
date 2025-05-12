package com.example.hrm.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hrm.db.entity.Ecg

@Dao
interface EcgDao {
    @Insert
    suspend fun insert(ctScanData: Ecg)

    @Query("SELECT * FROM ecg")
    suspend fun getAll(): List<Ecg>

    @Delete
    suspend fun delete(ctScanData: Ecg)

    @Update
    suspend fun update(ctScanData: Ecg)
}