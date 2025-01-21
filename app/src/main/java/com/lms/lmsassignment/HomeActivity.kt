package com.lms.lmsassignment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.lms.lmsassignment.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var sharedPrefManager: SharedPrefManager
    private var logOutPressed = false

    companion object {
        private const val SESSION_TIMEOUT = 10 * 1000L // 120 seconds in milliseconds
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefManager = SharedPrefManager(this)
        setupLogoutButton()

        // Save the timestamp immediately after a fresh login
        saveLastActiveTime()

        /** Setup tabs and bottom nav */
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

    }

    private fun setupLogoutButton() {
        binding.btnLogOut.setOnClickListener {
            logOutPressed = true
            logOutByUser()
        }
    }


    override fun onResume() {
        super.onResume()
        if (isSessionExpired()) {
            logOutUser()
        } else {
            saveLastActiveTime()
        }
    }
    override fun onPause() {
        super.onPause()
        //save time when app is inactive
        if (!logOutPressed) {
            saveLastActiveTime()
        }
    }

    override fun onDestroy() {
        //save time when app is being destroyed but not logged out
        if (!logOutPressed) {
            saveLastActiveTime()
        }
        super.onDestroy()
    }





    private fun isSessionExpired(): Boolean {
        val lastActiveTime = sharedPrefManager.getLastActiveTime()
        if (lastActiveTime == -1L) {
            return false // No timestamp found, assume session is still valid
        }
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastActiveTime) > SESSION_TIMEOUT
    }

    private fun logOutUser() {
        clearSessionData()
        showToast("Session expired. Please log in again.")
        val intent = Intent(this, AuthActivity::class.java)
        intent.putExtra("LOGOUT_REASON", "LOGGED_OUT")
        startActivity(intent)
        finish()
    }

    private fun logOutByUser() {
        showToast("Logged Out")
        clearSessionData()

        val intent = Intent(this, AuthActivity::class.java)
        intent.putExtra("LOGOUT_REASON", "LOGGED_OUT_BY_CLICK")
        startActivity(intent)
        finish()
    }

    private fun saveLastActiveTime() {
        val currentTime = System.currentTimeMillis()
        sharedPrefManager.saveLastActiveTime(currentTime)
    }

    private fun clearSessionData() {
        sharedPrefManager.clearSessionData()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}