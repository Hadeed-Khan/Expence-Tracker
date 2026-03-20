package com.techypie.expencetracker

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.techypie.expencetracker.databinding.ActivitySplashBinding
import com.techypie.expencetracker.databinding.ActivityUpdateItemBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)





        binding.logo.translationY = 800f
        binding.logo.alpha = 0f

        binding.logo.animate().translationY(0f).alpha(1f).setDuration(2000).start()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity,MainActivity::class.java))
            finish()
        },2100)
    }
}