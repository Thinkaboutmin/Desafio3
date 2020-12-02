package com.trab.desafio3.ui

import android.content.Intent
import android.os.Bundle
import android.util.TimeUtils
import androidx.appcompat.app.AppCompatActivity
import com.trab.desafio3.R
import com.trab.desafio3.helper.MarvelAPI
import com.trab.desafio3.models.MarvelBase
import com.trab.desafio3.services.marvelServices
import retrofit2.Call
import retrofit2.awaitResponse
import kotlinx.coroutines.*
import retrofit2.Response
import java.time.LocalDateTime
import java.util.*


class SplashActivity : AppCompatActivity() {
    var run = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val apiGen = MarvelAPI.getInstance()

        val intent = Intent(this, LoginActivity::class.java)
        GlobalScope.launch {
            val resp = apiGen.getComics(::preCache)

            if (run) {
                return@launch
            }
            run = true

            delay(3000)

            startActivity(intent)
            finish()
        }
    }

    private fun preCache(response: Response<MarvelBase>) {
        // Se falhar... Vemos isso depois :)
        if (response.code() == 200) {
            if (response.body() != null) {
                val body = response.body() as MarvelBase
                MarvelAPI.getInstance().comics.addAll(body.data.results)
                MarvelAPI.getInstance().nextPage()
            }
        }
    }
}