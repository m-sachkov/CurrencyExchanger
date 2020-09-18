package com.example.currencyexchanger.model.db

import androidx.room.TypeConverter
import com.example.currencyexchanger.model.pojo.Valute
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class DBConverter {

    companion object {

        @TypeConverter @JvmStatic
        fun fromMap(data: LinkedHashMap<String, Valute>): String {
            val gson = Gson()
            val res = gson.toJson(data)
            return gson.toJson(data)
        }

        @TypeConverter @JvmStatic
        fun toMap(data: String): LinkedHashMap<String, Valute> {
            val gson = Gson()
            return gson.fromJson(data, object : TypeToken<LinkedHashMap<String, Valute>>() {}.type)
        }
    }
}