package com.example.hrm.db

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hrm.db.entity.BloodData
import com.example.hrm.db.entity.CtScan
import com.example.hrm.db.entity.Ecg
import com.example.hrm.db.entity.GeneralPhysical
import com.example.hrm.db.entity.HealthRecord
import com.example.hrm.db.entity.LiverData
import com.example.hrm.db.entity.UrineRoutine
import com.example.hrm.db.entity.User
import com.example.hrm.service.AiChatService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
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

    val bloodRecord : StateFlow<List<BloodData>> = db.bloodDataDao().getAll()
        .stateIn(
            viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    var users : StateFlow<List<User>> = db.userDao().getAll()
        .stateIn(
            viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )

    val latestRecord : StateFlow<HealthRecord?> = healthDao.getLatest()
        .stateIn(
            viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = null
        )

    suspend fun getLatestRecord(): HealthRecord? {
        return db.healthRecordDao().getLatestRecord()
    }


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

    suspend fun getGeneralDataById(id: Long): GeneralPhysical? {
        var record = db.generalPhysicalDao().getById(id)
        return record
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

    fun getBloodDataById(id: Long, onComplete: (BloodData?) -> Unit) {
        viewModelScope.launch {
            val record = db.bloodDataDao().getById(id)
            onComplete(record)
        }
    }

    suspend fun getBloodDataById(id: Long) : BloodData? {
        var record = db.bloodDataDao().getById(id)
        return record
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

    suspend fun getUrineDataById(id: Long): UrineRoutine? {
        var record = db.urineRoutineDao().getById(id)
        return record
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

    suspend fun getLiverDataById(id: Long): LiverData? {
        var record = db.liverDataDao().getById(id)
        return record
    }

    fun addEcgData(ecg: Ecg) {
        viewModelScope.launch {
            ecg.date = getRecordById(ecg.sessionId)?.date!!
            db.ecgDao().insert(ecg)
        }
    }


    fun getEcgDataById(id: Long, onComplete: (Ecg?) -> Unit) {
        viewModelScope.launch {
            val record = db.ecgDao().getById(id)
            onComplete(record)
        }
    }

    suspend fun getEcgDataById(id: Long): Ecg? {
        var record = db.ecgDao().getById(id)
        return record
    }

    fun updateEcgData(ecg: Ecg) {
        viewModelScope.launch {
            db.ecgDao().update(ecg)
        }
    }

    fun addCtScanData(ctScan: CtScan) {
        viewModelScope.launch {
            ctScan.date = getRecordById(ctScan.sessionId)?.date!!
            db.ctScanDao().insert(ctScan)
        }
    }

    fun getCtScanDataById(id: Long, onComplete: (CtScan?) -> Unit) {
        viewModelScope.launch {
            val record = db.ctScanDao().getById(id)
            onComplete(record)
        }
    }

    suspend fun getCtScanDataById(id: Long): CtScan? {
        var record = db.ctScanDao().getById(id)
        return record
    }

    fun updateCtScanData(ctScan: CtScan) {
        viewModelScope.launch {
            db.ctScanDao().update(ctScan)
        }
    }

    fun saveImage(uri: Uri): String? {
        val context = getApplication<Application>().applicationContext
        return saveImageToInternalStorage(context, uri)
    }

    fun addUser(user: User) {
        viewModelScope.launch {
            db.userDao().insert(user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            db.userDao().update(user)
        }
    }

    fun getUserById(id: Long, onComplete: (User?) -> Unit) {
        viewModelScope.launch {
            val user = db.userDao().getById(id)
            onComplete(user)
        }
    }

    suspend fun isUserEmpty(): Boolean {
        return db.userDao().getCount() == 0
    }

    private val aiService = AiChatService()

    private val _responseText = MutableStateFlow("")
    val responseText: StateFlow<String> = _responseText

    fun askAi(systemPrompt: String, userInput: String) {
        _responseText.value = ""
        viewModelScope.launch {
            aiService.askQuestion(systemPrompt, userInput).collect { chunk ->
                val delta = chunk.choices.first().delta?.content ?: ""
                _responseText.value += delta
            }
        }
    }

    private fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val fileName = "img_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)

        inputStream.use { input ->
            FileOutputStream(file).use { output ->
                input.copyTo(output)
            }
        }

        return file.absolutePath
    }

}