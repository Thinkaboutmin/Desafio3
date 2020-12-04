package com.trab.desafio3.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.trab.desafio3.adapters.ComicsAdapter
import com.trab.desafio3.helper.MarvelAPI
import com.trab.desafio3.models.MarvelBase
import com.trab.desafio3.models.Results
import retrofit2.Response
import java.util.*

class HQListViewModel : ViewModel() {
    val comicList = MutableLiveData<ArrayList<Results>>()

    inner class PaginationCall : RecyclerView.OnScrollListener() {
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
            if (response.isSuccessful && response.body() != null) {
                val api = MarvelAPI.getInstance()
                api.nextPage()
                val marvelBase = response.body()!!
                marvelBase.data.results
                api.addWithImageAndDesc(response.body()!!.data.results)
                comicList.postValue(api.comics)
            }
        }
    }
}