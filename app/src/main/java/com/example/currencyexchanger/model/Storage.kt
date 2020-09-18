package com.example.currencyexchanger.model

import android.content.Context
import android.util.Log
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
                val localData =
                    localDBConnection
                    .appDao()
                    .getValuteInfo()
                if (localData == null) {
                    storage = loadActualValutesInfo()
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

    private suspend fun loadActualValutesInfo() =
        NetworkService.instance
        .getAPI()
        .getActualValuteInfo()

    fun refreshData(): ValuteInfo? {
        val deferred = GlobalScope.async {
            val actualData = loadActualValutesInfo()
            if (actualData != storage) {
                localDBConnection
                    .appDao()
                    .update(actualData)
                storage = actualData
            }
            storage
        }
        return runBlocking { deferred.await() }
    }

    companion object {
        val instance = Storage(App.getAppContext())
    }

}