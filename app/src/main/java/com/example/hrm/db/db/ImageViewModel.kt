package com.example.hrm.db.db

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class ImageViewModel(application: Application) : AndroidViewModel(application) {
    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java,
        "my_database"
    ).build()

    fun saveImage(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val context = getApplication<Application>()
            val path = saveImageToInternalStorage(context, uri)
            if (path != null) {
                db.imageDao().insertImage(ImageEntity(path = path))
            }
        }
    }

    private val _imagePaths = mutableStateListOf<String>()
    val imagePaths: List<String> get() = _imagePaths

    fun loadImagesFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val images = db.imageDao().getAllImages() // 返回 List<ImageEntity>
            val paths = images.map { it.path }
            withContext(Dispatchers.Main) {
                _imagePaths.clear()
                _imagePaths.addAll(paths)
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
