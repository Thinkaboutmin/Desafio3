package com.trab.desafio3.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trab.desafio3.R
import com.trab.desafio3.adapters.ComicsAdapter
import com.trab.desafio3.helper.MarvelAPI
import com.trab.desafio3.models.MarvelBase
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}