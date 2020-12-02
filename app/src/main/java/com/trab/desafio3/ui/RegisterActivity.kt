package com.trab.desafio3.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.trab.desafio3.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        btnRegister.setOnClickListener {
            finish()
        }
    }
}