package com.example.currencyexchanger.model

import com.example.currencyexchanger.model.network.NetworkService
import com.example.currencyexchanger.model.pojo.ValuteInfo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class Storage private constructor(){

    private var storage: ValuteInfo? = null

    fun getData(): ValuteInfo {
        if (storage == null) {
            val deferred = GlobalScope.async {
                NetworkService.instance
                    .getAPI()
                    .getActualValuteInfo()
            }
            storage = runBlocking { deferred.await() }
        }
        return storage!!
    }


    companion object {
        val instance = Storage()
    }

}