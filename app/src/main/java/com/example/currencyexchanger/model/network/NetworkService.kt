package com.example.currencyexchanger.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService private constructor(){

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://www.cbr-xml-daily.ru/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getAPI(): NetworkAPI = retrofit.create(NetworkAPI::class.java)

    companion object {
        val instance: NetworkService = NetworkService()
    }
}