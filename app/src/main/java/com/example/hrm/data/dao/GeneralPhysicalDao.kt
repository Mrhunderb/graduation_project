package com.example.hrm.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hrm.data.entity.GeneralPhysical

@Dao
interface GeneralPhysicalDao {
    @Insert
    suspend fun insertGeneralPhysicalData(generalPhysicalData: GeneralPhysical)

    @Query("SELECT * FROM general_physical")
    suspend fun getAllGeneralPhysicalData(): List<GeneralPhysical>

    @Delete
    suspend fun deleteGeneralPhysicalData(generalPhysicalData: GeneralPhysical)

    @Update
    suspend fun updateGeneralPhysicalData(generalPhysicalData: GeneralPhysical)
}