package com.example.currencyexchanger.presenter.valutes

import com.example.currencyexchanger.model.Storage
import com.example.currencyexchanger.model.pojo.Valute
import com.example.currencyexchanger.view.valutes.ValuteViewInterface

class ValutesPresenter(valuteView: ValuteViewInterface): ValutesPresenterInterface {

    private val adapter: MyAdapter = MyAdapter(LinkedHashMap())
    private val storage: Storage = Storage.instance

    init {
        setAdapterData(storage.getData().valutes)
        valuteView.setAdapter(adapter)
    }

    override fun refreshData() {
        setAdapterData(storage.refreshData()?.valutes)
    }

    private fun setAdapterData(data: LinkedHashMap<String, Valute>?) {
        data?.let { adapter.setData(it) }
    }
}