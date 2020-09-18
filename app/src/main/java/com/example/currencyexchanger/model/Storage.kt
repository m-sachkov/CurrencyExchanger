package com.example.currencyexchanger.model

import android.content.Context
import androidx.room.Room
import com.example.currencyexchanger.App
import com.example.currencyexchanger.model.db.AppDB
import com.example.currencyexchanger.model.network.NetworkService
import com.example.currencyexchanger.model.pojo.ValuteInfo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class Storage private constructor(context: Context){

    private var storage: ValuteInfo? = null
    private val localDBConnection: AppDB =
        Room.databaseBuilder(context, AppDB::class.java, "db").build()

    fun getData(): ValuteInfo {
        if (storage == null) {
            val deferred = GlobalScope.async {
                val localData = localDBConnection.appDao().getValuteInfo()
                if (localData == null) {
                    storage = NetworkService.instance
                    .getAPI()
                    .getActualValuteInfo()
                    storage?.let{ localDBConnection.appDao().insert(it) }
                }
                else {
                    storage = localData
                }
            }
            runBlocking { deferred.await() }
        }
        return storage!!
    }


    companion object {
        val instance = Storage(App.getAppContext())
    }

}