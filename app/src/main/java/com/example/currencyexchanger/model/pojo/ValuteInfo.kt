package com.example.currencyexchanger.model.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ValuteInfo (
    @SerializedName("Date")
    @Expose
    val date: String,
    @SerializedName("Valute")
    @Expose
    val valutes: LinkedHashMap<String, Valute>
)