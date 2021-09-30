package com.example.bio

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import com.example.bio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var biometricPrompt: BiometricPrompt

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val canAuthenticate = BiometricManager.from(applicationContext).canAuthenticate()

        Log.i("TAG", "canAuthenticate: $canAuthenticate")

        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            binding.button.setOnClickListener {
                Log.d("TAG", "buttonClicked")
               showBiometricPromptForDecryption()
            }
        }


    }

    private fun showBiometricPromptForDecryption() {
            biometricPrompt =
                BiometricPromptUtils.createBiometricPrompt(
                    this,
                    processSuccess =  ::showSuccessToast,
                    processFail = ::showFailToast,
                    processError = ::showErrorToast
                )
            val promptInfo = BiometricPromptUtils.createPromptInfo(this)
            biometricPrompt.authenticate(promptInfo)
    }

    private fun showSuccessToast() {
        Toast.makeText(this, "Authentication success", Toast.LENGTH_LONG).show()
    }

    private fun showFailToast() {
        Toast.makeText(this, "Authentication failed", Toast.LENGTH_LONG).show()
    }

    private fun showErrorToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}