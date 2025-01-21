package com.lms.lmsassignment

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.lms.lmsassignment.databinding.ActivityMainBinding
import java.util.concurrent.Executor

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var sharedPrefManager: SharedPrefManager

    companion object {
        private const val SESSION_TIMEOUT = 10 * 1000L // 120 seconds in milliseconds
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefManager = SharedPrefManager(this)
        executor = ContextCompat.getMainExecutor(this)



        if (!isBiometricSupported()) return
        if (goToHome()){
            sharedPrefManager.clearSessionData()
            navigateToHome()
        }
        setupBiometricPrompt()
        setupLoginButton()
    }

    private fun isBiometricSupported(): Boolean {
        val biometricManager = BiometricManager.from(this)
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                showToast("No biometric hardware available")
                false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                showToast("Biometric hardware is currently unavailable")
                false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                showToast("No biometric credentials enrolled")
                false
            }
            else -> false
        }
    }

    private fun setupBiometricPrompt() {
        biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onBiometricAuthenticationSuccess()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                showToast("Authentication failed!")
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                showToast("Error: $errString")
            }
        })
    }

    private fun setupLoginButton() {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login")
            .setSubtitle("Log in using your fingerprint")
            .setNegativeButtonText("Cancel")
            .build()

        binding.btnLogin.setOnClickListener {
            /** uncomment for auth prompt */
//            biometricPrompt.authenticate(promptInfo)
            navigateToHome()
        }
    }

    private fun onBiometricAuthenticationSuccess() {
        showToast("Authentication succeeded!")
        navigateToHome()
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun goToHome(): Boolean {
        val lastActiveTime = sharedPrefManager.getLastActiveTime()
        if (lastActiveTime == -1L) {
            return false // No timestamp found, auth required
        }

        val logoutReason = intent.getStringExtra("LOGOUT_REASON")
        if (logoutReason == "LOGGED_OUT_BY_CLICK") { return false }
        if (logoutReason == "LOGGED_OUT") { return false }

        val currentTime = System.currentTimeMillis()

        return (currentTime - lastActiveTime) <= SESSION_TIMEOUT
    }
}