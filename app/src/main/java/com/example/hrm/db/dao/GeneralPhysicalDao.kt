package com.example.hrm.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.hrm.db.entity.GeneralPhysical
import kotlinx.coroutines.flow.Flow

@Dao
interface GeneralPhysicalDao {
    @Insert
    suspend fun insert(generalPhysicalData: GeneralPhysical)

    @Query("SELECT * FROM general_physical")
    fun getAll(): Flow<List<GeneralPhysical>>

    @Query("SELECT * FROM general_physical WHERE sessionId = :id")
    suspend fun getById(id: Long): GeneralPhysical?

    @Delete
    suspend fun delete(generalPhysicalData: GeneralPhysical)

    @Update
    suspend fun update(generalPhysicalData: GeneralPhysical)
}