package com.lms.lmsassignment.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.lms.lmsassignment.R
import com.lms.lmsassignment.databinding.ActivityHomeBinding
import com.lms.lmsassignment.utils.Const
import com.lms.lmsassignment.utils.SharedPrefManager
import com.lms.lmsassignment.view_model.LMSViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var sharedPrefManager: SharedPrefManager
    private var logOutPressed = false
    private lateinit var sharedViewModel : LMSViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = ContextCompat.getColor(this, R.color.lms_primary)
        sharedPrefManager = SharedPrefManager(this)
        setupLogoutButton()

        // Save the timestamp immediately after a fresh login
        saveLastActiveTime()

        /** Setup tabs and bottom nav */
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

        sharedViewModel =  ViewModelProvider(this)[LMSViewModel::class.java]
        sharedViewModel.getSummary()
        sharedViewModel.getBattingList()
        sharedViewModel.getBowlingList()
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
        return (currentTime - lastActiveTime) > Const.SESSION_TIMEOUT
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