package com.trab.desafio3.services

import com.trab.desafio3.models.MarvelBase
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.*

/**
 * Chamadas para os serviços da Marvel
 *
 * Como o foco da aplicação é para demonstrar HQ's focamos somente nesse tipo de chamada.
 */
interface MarvelServices {
    @GET("v1/public/comics?format=comic&noVariants=true&" +
            "titleStartsWith=amazing&hasDigitalIssue=true&limit=70")
    fun getComics(
        @Query("ts") ts: String,
        @Query("apikey") kPub: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int = 0,
        @Query("dateRange") dateRange: String = "2008-01-01,${SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())}"
    ) : Call<MarvelBase>
}

private val retrofitMarvel: Retrofit = Retrofit.Builder().baseUrl("https://gateway.marvel.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val marvelServices: MarvelServices = retrofitMarvel.create(MarvelServices::class.java)