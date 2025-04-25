package com.example.hrm.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hrm.data.entity.Electrocardiogram

@Dao
interface ElectrocardiogramDao {
    @Insert
    suspend fun insertElectrocardiogramData(ctScanData: Electrocardiogram)

    @Query("SELECT * FROM electrocardiogram")
    suspend fun getAllElectrocardiogramData(): List<Electrocardiogram>

    @Delete
    suspend fun deleteElectrocardiogramData(ctScanData: Electrocardiogram)

    @Update
    suspend fun updateElectrocardiogramData(ctScanData: Electrocardiogram)
}