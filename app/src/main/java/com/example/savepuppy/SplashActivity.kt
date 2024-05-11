package com.example.savepuppy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Using a Handler to delay the transition
        Handler().postDelayed({
            // Start MainActivity and finish SplashActivity
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000) // 3000ms delay
    }
}