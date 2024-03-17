package com.oleg.servicepermisson

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.oleg.servicepermisson.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRootBinding
    private lateinit var viewModel: RootViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RootViewModel::class.java]

        supportActionBar?.subtitle = "PEKO permission test"

        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.btnCheckNotifications.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) showSnackMessage(message = "Old BUILD")
            else viewModel.checkPermission(Manifest.permission.POST_NOTIFICATIONS)
        }

        binding.btnOpenPrefs.setOnClickListener {
            requestPermission()
        }

        binding.btnStartService.setOnClickListener {
            val thread = Thread() {
                startForegroundService()
            }
            thread.start()

            viewModel.startServiceTimer()
        }
    }

    private fun setUpObservers() {
        viewModel.errorMessage.observe(this) {
            showSnackMessage(message = it)
        }

        viewModel.serviceTimer.observe(this){time->
            supportActionBar?.subtitle = "service time = $time"
        }
    }

    private fun showSnackMessage(message: String, button: String = "Ok") {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setTextMaxLines(10)
            .setAction(button) { }
            .show()
    }


    private fun requestPermission() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.fromParts("package", this.packageName, null)
        startActivity(intent)
    }

    private fun startForegroundService() {
        val intentService = Intent(this, CustomService::class.java)
        ContextCompat.startForegroundService(this, intentService)
    }
}