package com.example.hrm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hrm.db.dao.BloodDataDao
import com.example.hrm.db.dao.CtScanDao
import com.example.hrm.db.dao.EcgDao
import com.example.hrm.db.dao.GeneralPhysicalDao
import com.example.hrm.db.dao.HealthRecordDao
import com.example.hrm.db.dao.LiverDataDao
import com.example.hrm.db.dao.UrineRoutineDao
import com.example.hrm.db.entity.BloodData
import com.example.hrm.db.entity.CtScan
import com.example.hrm.db.entity.Ecg
import com.example.hrm.db.entity.GeneralPhysical
import com.example.hrm.db.entity.HealthRecord
import com.example.hrm.db.entity.LiverData
import com.example.hrm.db.entity.UrineRoutine

@Database(
    entities = [
        HealthRecord::class,
        BloodData::class,
        UrineRoutine::class,
        GeneralPhysical::class,
        Ecg::class,
        CtScan::class,
        LiverData::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun healthRecordDao(): HealthRecordDao
    abstract fun bloodDataDao(): BloodDataDao
    abstract fun urineRoutineDao(): UrineRoutineDao
    abstract fun generalPhysicalDao(): GeneralPhysicalDao
    abstract fun ctScanDao(): CtScanDao
    abstract fun electrocardiogramDao(): EcgDao
    abstract fun liverDataDao(): LiverDataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "health_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
