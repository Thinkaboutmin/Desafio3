package com.trab.desafio3.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.os.SystemClock.sleep
import androidx.appcompat.app.AppCompatActivity
import com.trab.desafio3.R
import kotlin.concurrent.thread

class SplashActivity : AppCompatActivity() {
    var run = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        thread {
            if (run) {
                return@thread
            }
            run = true
            val intent = Intent(this, LoginActivity::class.java)
            sleep(5000)
            startActivity(intent)
            finish()
        }
    }
}