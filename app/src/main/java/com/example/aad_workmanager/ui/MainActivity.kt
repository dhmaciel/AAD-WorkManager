package com.example.aad_workmanager.ui

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.aad_workmanager.broadcast_receiver.NotificationReceiver
import com.example.aad_workmanager.databinding.ActivityMainBinding
import com.example.aad_workmanager.notification.NotificationCenter
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val notificationReceiver = NotificationReceiver()

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NotificationCenter.createNotificationChanel(this)
        registerReceiver(notificationReceiver, IntentFilter(NotificationReceiver.ACTION_UPDATE_NOTIFICATION))

        bindView()
        showToast("Welcome :)")
    }

    private fun bindView() {
        binding.btnWork.setOnClickListener {
            showLoading()
            NotificationCenter.cancelNotification(NotificationCenter.NOTIFICATION_ID, this)
            viewModel.getRandomImage()
            observerData()
        }
    }

    private fun observerData() {
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(viewModel.otwRequest.id)
            .observe(this, {
                when (it.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        showSnackBar("${it.outputData} in your download path")
                        NotificationCenter.updateNotification(this)
                        hideLoading()
                    }
                    WorkInfo.State.FAILED -> {
                        showToast("Error on worker")
                        hideLoading()
                    }
                    else -> return@observe
                }
            })
    }

    private fun showLoading() {
        binding.btnWork.visibility = View.GONE
        binding.progressWork.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.btnWork.visibility = View.VISIBLE
        binding.progressWork.visibility = View.INVISIBLE
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun showSnackBar(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        unregisterReceiver(notificationReceiver)
        super.onDestroy()
    }
}