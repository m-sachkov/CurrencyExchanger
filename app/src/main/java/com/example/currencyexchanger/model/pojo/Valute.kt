package com.example.currencyexchanger.model.pojo

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class Valute(
    @SerializedName("CharCode")
    var charCode: String,
    @SerializedName("Nominal")
    var nominal: Int,
    @SerializedName("Name")
    var name: String,
    @SerializedName("Value")
    var value: Double
) {
    override fun toString(): String {
        return "$charCode $name"
    }
}