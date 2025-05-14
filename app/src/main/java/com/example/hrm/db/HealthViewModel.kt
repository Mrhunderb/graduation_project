package com.example.hrm.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrm.db.entity.BloodData
import com.example.hrm.db.entity.GeneralPhysical
import com.example.hrm.db.entity.HealthRecord
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

    private suspend fun getRecordById(id: Long): HealthRecord {
        return db.healthRecordDao().getById(id)
    }

    fun addGeneralData(generalData: GeneralPhysical) {
        viewModelScope.launch {
            generalData.date = getRecordById(generalData.sessionId).date
            db.generalPhysicalDao().insert(generalData)
        }
    }

    fun addBloodData(bloodData: BloodData) {
        viewModelScope.launch {
            bloodData.date = getRecordById(bloodData.sessionId).date
            db.bloodDataDao().insert(bloodData)
        }
    }
}