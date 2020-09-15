package com.example.currencyexchanger.model

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.currencyexchanger.model.network.NetworkService
import com.example.currencyexchanger.model.pojo.ValuteInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Storage {

    private var storage: MutableLiveData<ValuteInfo> = MutableLiveData()

    init {
        loadValuteInf()
    }

    fun subscribeObserver(lifecycleOwner: LifecycleOwner, observer: Observer<ValuteInfo>) {
        storage.observe(lifecycleOwner, observer)
    }

    private fun loadValuteInf() {
        NetworkService.instance
            .getAPI()
            .getActualValuteInfo()
            .enqueue(object: Callback<ValuteInfo> {
                override fun onResponse(call: Call<ValuteInfo>, response: Response<ValuteInfo>) {
                    storage.value = response.body()
                }

                override fun onFailure(call: Call<ValuteInfo>, t: Throwable) {
                    Log.d(Log.DEBUG.toString(), t.message)
                }
        })
    }

}