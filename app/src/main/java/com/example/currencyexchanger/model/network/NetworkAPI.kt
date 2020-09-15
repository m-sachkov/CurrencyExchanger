package com.example.currencyexchanger.model.network

import com.example.currencyexchanger.model.pojo.ValuteInfo
import retrofit2.Call
import retrofit2.http.GET

interface NetworkAPI {
    @GET("/daily_json.js")
    fun getActualValuteInfo(): Call<ValuteInfo>
}