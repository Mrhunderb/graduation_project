package com.example.hrm.db.repository

import com.example.hrm.db.dao.BloodDataDao
import com.example.hrm.db.dao.CtScanDao
import com.example.hrm.db.dao.EcgDao
import com.example.hrm.db.dao.HealthRecordDao
import com.example.hrm.db.dao.UrineRoutineDao
import com.example.hrm.db.entity.HealthRecord

class HealthRepository(
    private val healthRecordDao: HealthRecordDao,
    private val bloodDataDao: BloodDataDao,
    private val urineRoutineDao: UrineRoutineDao,
    private val ctScanDao: CtScanDao,
    private val electrocardiogramDao: EcgDao
) {
    suspend fun insertHealthRecord(healthRecord: HealthRecord) {
        healthRecordDao.insert(healthRecord)
    }
}