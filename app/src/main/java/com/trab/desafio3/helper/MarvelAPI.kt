package com.trab.desafio3.helper

import com.trab.desafio3.models.MarvelBase
import com.trab.desafio3.models.Results
import com.trab.desafio3.services.marvelServices
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.awaitResponse

class MarvelAPI(val kPub: String, private val kPrivate: String, val timeStamp: String) {
    var onCall = false
    private set

    var hash: String? = null
    private set

    var offset = 0
    private set

    private var limit = 10

    var onLastPage = false
    private set

    /**
     * Guarda os valores já pegos da API.
     */
    val comics: ArrayList<Results> = arrayListOf()

    companion object {
        private var obj: MarvelAPI? = null

        fun getInstance(): MarvelAPI {
            if (obj == null) {
                obj = MarvelAPI("c70e158b477c5ee33c838850e91a0be0",
                    "d427f5a8db234bc02d1ffd9203d7fadb13254878",
                    "1")
                return obj!!
            }

            return obj!!
        }
    }

    fun getComics(callback: ((response: Response<MarvelBase>) -> Unit)) {
        if (onLastPage && onCall) {
            return
        } else if (limit == offset) {
            onLastPage = true
        }

        onCall = true

        if (hash == null) {
            md5Digest()
        }

        val call = marvelServices.getComics(timeStamp, kPub, hash!!, offset)

        GlobalScope.launch {
            val response = call.awaitResponse()
            callback(response)

            if (limit == 0) {
                if (response.isSuccessful && response.code() == 200) {
                    limit = response.body()!!.data.total / response.body()!!.data.limit
                }
            }

            onCall = false
        }
    }

    fun nextPage() = ++offset

    fun backPage() = --offset

    fun addWithImageAndDesc(comics: List<Results>) {
        for (comic in comics) {
            if (comic.images.isNotEmpty() && comic.description != null) {
                this.comics.add(comic)
            }
        }
    }

    /**
     * Na API da Marvel é necessario passar um hash md5 logo fazemos isso aqui
     * recebendo somente a quantidade de tempo da sessão entre o servidor e o cliente.
     *
     */
    private fun md5Digest(): String {
        hash = (timeStamp + kPrivate + kPub).toMD5()
        return hash!!
    }
}