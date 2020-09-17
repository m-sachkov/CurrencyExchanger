package com.example.currencyexchanger.model.pojo

import com.google.gson.annotations.SerializedName

data class Valute(
    @SerializedName("CharCode")
    val charCode: String,
    @SerializedName("Nominal")
    val nominal: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Value")
    val value: Double
) {
    override fun toString(): String {
        return "$charCode $name"
    }
}