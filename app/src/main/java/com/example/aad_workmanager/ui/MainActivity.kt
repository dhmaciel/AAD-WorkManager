package com.example.aad_workmanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.aad_workmanager.databinding.ActivityMainBinding
import com.example.aad_workmanager.ui.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindView()
        showToast("Welcome :)")
    }

    private fun bindView() {
        binding.btnWork.setOnClickListener {
            showLoading()
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
                        hideLoading()}
                    WorkInfo.State.FAILED -> {
                        showToast("Error on worker")
                        hideLoading()}
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
}