package com.example.hrm.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hrm.db.entity.CtScan

@Dao
interface CtScanDao {
    @Insert
    suspend fun insertCtScanData(ctScanData: CtScan)

    @Query("SELECT * FROM ct_scan")
    suspend fun getAllCtScanData(): List<CtScan>

    @Delete
    suspend fun deleteCtScanData(ctScanData: CtScan)

    @Update
    suspend fun updateCtScanData(ctScanData: CtScan)
}