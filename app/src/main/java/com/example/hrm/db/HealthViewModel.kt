package com.example.hrm.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrm.db.entity.BloodData
import com.example.hrm.db.entity.GeneralPhysical
import com.example.hrm.db.entity.HealthRecord
import com.example.hrm.db.entity.LiverData
import com.example.hrm.db.entity.UrineRoutine
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date

class HealthViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val healthDao = db.healthRecordDao()

    val record : StateFlow<List<HealthRecord>> = healthDao.getAll()
        .stateIn(
            viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    suspend fun addRecord(time: Date, hospital: String): Long {
        return db.healthRecordDao().insert(
            HealthRecord(
                date = time.time,
                hospital = hospital
            )
        )
    }

    fun addRecordLaunch(time: Date, hospital: String, onComplete : (Long) -> Unit) {
        viewModelScope.launch {
            val id = addRecord(time, hospital)
            onComplete(id)
        }
    }

    fun deleteRecord(id: Long) {
        viewModelScope.launch {
            healthDao.deleteById(id)
        }
    }

    suspend fun getRecordById(id: Long): HealthRecord? {
        return db.healthRecordDao().getById(id)
    }

    fun getRecordById(id: Long, onComplete: (HealthRecord?) -> Unit) {
        viewModelScope.launch {
            val record = db.healthRecordDao().getById(id)
            onComplete(record)
        }
    }

    fun updateRecord(healthRecord: HealthRecord) {
        viewModelScope.launch {
            db.healthRecordDao().update(healthRecord)
        }
    }

    fun addGeneralData(generalData: GeneralPhysical) {
        viewModelScope.launch {
            generalData.date = getRecordById(generalData.sessionId)?.date!!
            db.generalPhysicalDao().insert(generalData)
        }
    }

    fun updateGeneralData(generalData: GeneralPhysical) {
        viewModelScope.launch {
            db.generalPhysicalDao().update(generalData)
        }
    }


    fun getGeneralDataById(id: Long, onComplete: (GeneralPhysical?) -> Unit) {
        viewModelScope.launch {
            val record = db.generalPhysicalDao().getById(id)
            onComplete(record)
        }
    }

    fun addBloodData(bloodData: BloodData) {
        viewModelScope.launch {
            bloodData.date = getRecordById(bloodData.sessionId)?.date!!
            db.bloodDataDao().insert(bloodData)
        }
    }
    fun updateBloodData(bloodData: BloodData) {
        viewModelScope.launch {
            db.bloodDataDao().update(bloodData)
        }
    }

    fun deleteBloodData(sessionId: Long) {
        viewModelScope.launch {
            db.bloodDataDao().deleteById(sessionId)
        }
    }

    fun getBloodDataById(id: Long, onComplete: (BloodData?) -> Unit) {
        viewModelScope.launch {
            val record = db.bloodDataDao().getById(id)
            onComplete(record)
        }
    }

    fun addUrineData(urineData: UrineRoutine) {
        viewModelScope.launch {
            urineData.date = getRecordById(urineData.sessionId)?.date!!
            db.urineRoutineDao().insert(urineData)
        }
    }

    fun updateUrineData(urineData: UrineRoutine) {
        viewModelScope.launch {
            db.urineRoutineDao().update(urineData)
        }
    }

    fun getUrineDataById(id: Long, onComplete: (UrineRoutine?) -> Unit) {
        viewModelScope.launch {
            val record = db.urineRoutineDao().getById(id)
            onComplete(record)
        }
    }

    fun addLiverData(liverData: LiverData) {
        viewModelScope.launch {
            liverData.date = getRecordById(liverData.sessionId)?.date!!
            db.liverDataDao().insert(liverData)
        }
    }

    fun updateLiverData(liverData: LiverData) {
        viewModelScope.launch {
            db.liverDataDao().update(liverData)
        }
    }

    fun getLiverDataById(id: Long, onComplete: (LiverData?) -> Unit) {
        viewModelScope.launch {
            val record = db.liverDataDao().getById(id)
            onComplete(record)
        }
    }
}