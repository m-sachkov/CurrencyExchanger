package com.example.currencyexchanger.presenter.valutes

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.currencyexchanger.model.Storage
import com.example.currencyexchanger.view.valutes.ValuteViewInterface

class ValutesPresenter(val valuteView: ValuteViewInterface): ValutesPresenterInterface, LifecycleOwner {

    private val adapter: MyAdapter = MyAdapter(LinkedHashMap())
    private val storage: Storage = Storage()

    init {
        valuteView.setAdapter(adapter)
        storage.subscribeObserver(this, Observer {
            adapter.setData(it.valutes)
        })
    }

    override fun getLifecycle(): Lifecycle {
       return valuteView.getActivityLifecycle()
    }
}