package com.example.hrm.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hrm.db.entity.CtScan
import kotlinx.coroutines.flow.Flow

@Dao
interface CtScanDao {
    @Insert
    suspend fun insert(ctScanData: CtScan)

    @Query("SELECT * FROM ct_scan")
    fun getAll(): Flow<List<CtScan>>

    @Delete
    suspend fun delete(ctScanData: CtScan)

    @Update
    suspend fun update(ctScanData: CtScan)
}