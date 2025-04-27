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
    suspend fun insert(ctScanData: CtScan)

    @Query("SELECT * FROM ct_scan")
    suspend fun getAll(): List<CtScan>

    @Delete
    suspend fun delete(ctScanData: CtScan)

    @Update
    suspend fun update(ctScanData: CtScan)
}