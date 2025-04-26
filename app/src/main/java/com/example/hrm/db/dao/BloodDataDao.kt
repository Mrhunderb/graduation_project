package com.example.hrm.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hrm.db.entity.BloodData

@Dao
interface BloodDataDao {
     @Insert
     suspend fun insertBloodData(bloodData: BloodData)

     @Query("SELECT * FROM blood_data")
     suspend fun getAllBloodData(): List<BloodData>

     @Delete
     suspend fun deleteBloodData(bloodData: BloodData)

     @Update
     suspend fun updateBloodData(bloodData: BloodData)
}