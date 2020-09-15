package com.example.currencyexchanger.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.currencyexchanger.MyAdapter
import com.example.currencyexchanger.model.Storage
import com.example.currencyexchanger.view.MainActivityInterface

class MainPresenter(val mainActivity: MainActivityInterface): MainPresenterInterface, LifecycleOwner {

    private val adapter: MyAdapter = MyAdapter(LinkedHashMap())
    private val storage: Storage = Storage()

    init {
        mainActivity.setAdapter(adapter)
        storage.subscribeObserver(this, Observer {
            adapter.setData(it.valutes)
        })
    }

    override fun getLifecycle(): Lifecycle {
       return mainActivity.getActivityLifecycle()
    }
}