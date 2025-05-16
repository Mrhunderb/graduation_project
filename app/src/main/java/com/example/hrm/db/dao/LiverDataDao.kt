package com.example.hrm.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hrm.db.entity.LiverData
import kotlinx.coroutines.flow.Flow

@Dao
interface LiverDataDao {
    @Insert
    suspend fun insert(liverData: LiverData)

    @Query("SELECT * FROM liver_data")
    fun getAll(): Flow<List<LiverData>>

    @Query("SELECT * FROM liver_data WHERE id = :id")
    suspend fun getById(id: Long): LiverData?

    @Delete
    suspend fun delete(liverData: LiverData)

    @Update
    suspend fun update(liverData: LiverData)
}