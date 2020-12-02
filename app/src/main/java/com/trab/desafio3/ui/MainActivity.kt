package com.trab.desafio3.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trab.desafio3.R
import com.trab.desafio3.adapters.ComicsAdapter
import com.trab.desafio3.helper.MarvelAPI
import com.trab.desafio3.models.MarvelBase
import com.trab.desafio3.services.marvelServices
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.awaitResponse

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rcHq.layoutManager = GridLayoutManager(this, 3)
        rcHq.setHasFixedSize(true)
        rcHq.adapter = ComicsAdapter(MarvelAPI.getInstance())

        (rcHq.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
        rcHq.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var lastVisible = 0
            var lastDy = 0

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val manager = (recyclerView.layoutManager as GridLayoutManager)
                val lastItem = manager.findLastVisibleItemPosition()
                val adapter = recyclerView.adapter as ComicsAdapter
                if (dy > lastDy && lastItem == adapter.itemCount - 1 && lastVisible != lastItem) {
                    lastVisible = lastItem
                    MarvelAPI.getInstance().getComics {
                        updateRecycle(it, adapter)
                    }
                }

                lastDy = dy
            }

            private fun updateRecycle(response: Response<MarvelBase>, adapter: ComicsAdapter) {
                if (response.isSuccessful && response.code() == 200 && response.body() != null) {
                    val marvelBase = response.body()!!
                    MarvelAPI.getInstance().comics.addAll(marvelBase.data.results)
                    MarvelAPI.getInstance().nextPage()
                    runOnUiThread {
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}