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
     suspend fun insert(bloodData: BloodData)

     @Query("SELECT * FROM blood_data")
     suspend fun getAll(): List<BloodData>

     @Delete
     suspend fun delete(bloodData: BloodData)

     @Update
     suspend fun update(bloodData: BloodData)
}