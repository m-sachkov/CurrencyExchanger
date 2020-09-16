package com.example.currencyexchanger.model.network

import com.example.currencyexchanger.model.pojo.ValuteInfo
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface NetworkAPI {
    @GET("/daily_json.js")
    suspend fun getActualValuteInfo(): ValuteInfo
}