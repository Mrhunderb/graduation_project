package com.example.hrm.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hrm.db.entity.BloodData
import kotlinx.coroutines.flow.Flow

@Dao
interface BloodDataDao {
     @Insert
     suspend fun insert(bloodData: BloodData)

     @Query("SELECT * FROM blood_data ORDER BY date")
     fun getAll(): Flow<List<BloodData>>

     @Query("SELECT * FROM blood_data WHERE sessionId = :id")
     suspend fun getById(id: Long): BloodData?

     @Delete
     suspend fun delete(bloodData: BloodData)

     @Query("DELETE FROM blood_data WHERE sessionId = :id")
     suspend fun deleteById(id: Long)

     @Update
     suspend fun update(bloodData: BloodData)
}