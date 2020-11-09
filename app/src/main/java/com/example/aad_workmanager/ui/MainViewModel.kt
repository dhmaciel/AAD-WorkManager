package com.example.aad_workmanager.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.example.aad_workmanager.worker.ImageWorker

class MainViewModel(private val appContext: Application) : AndroidViewModel(appContext) {

    lateinit var otwRequest: OneTimeWorkRequest

    fun getRandomImage() {
        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        otwRequest = OneTimeWorkRequestBuilder<ImageWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(appContext).enqueue(otwRequest)
    }
}