package com.example.currencyexchanger.presenter.valutes

import com.example.currencyexchanger.model.Storage
import com.example.currencyexchanger.view.valutes.ValuteViewInterface

class ValutesPresenter(valuteView: ValuteViewInterface): ValutesPresenterInterface {

    private val adapter: MyAdapter = MyAdapter(LinkedHashMap())
    private val storage: Storage = Storage.instance

    init {
        storage.getData()?.valutes?.let { adapter.setData(it) }
        valuteView.setAdapter(adapter)
    }
}