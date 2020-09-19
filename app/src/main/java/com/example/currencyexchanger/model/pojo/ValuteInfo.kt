package com.example.currencyexchanger.model.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ValuteInfo (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @SerializedName("Date")
    var date: String,
    @SerializedName("Valute")
    var valutes: LinkedHashMap<String, Valute>
) {
    override fun equals(other: Any?): Boolean {
        if (other is ValuteInfo) {
            return date == other.date && valutes == other.valutes
        }
        return false
    }

    fun getCopy(): ValuteInfo {
        return ValuteInfo(id, date, valutes.clone() as LinkedHashMap<String, Valute>)
    }
}