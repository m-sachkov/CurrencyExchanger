package com.example.currencyexchanger.model.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ValuteInfo (
    @PrimaryKey
    @SerializedName("Date")
    var date: String,
    @SerializedName("Valute")
    var valutes: LinkedHashMap<String, Valute>
)