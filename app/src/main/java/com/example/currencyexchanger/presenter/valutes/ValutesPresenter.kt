package com.example.currencyexchanger.presenter.valutes

import com.example.currencyexchanger.model.Storage
import com.example.currencyexchanger.model.pojo.Valute
import com.example.currencyexchanger.view.valutes.ValuteViewInterface
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.LinkedHashMap

class ValutesPresenter(val valuteView: ValuteViewInterface): ValutesPresenterInterface, Storage.AutoDataUpdateNotificationsListener {

    private val adapter: MyAdapter = MyAdapter(LinkedHashMap())
    private val storage: Storage = Storage.instance
    private val timeFormatter =
        SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.SHORT, SimpleDateFormat.MEDIUM)

    init {
        setAdapterData(storage.getData().valutes)
        valuteView.setAdapter(adapter)
        storage.subscribeOnAutoUpdNotifications(this)
    }

    override fun refreshData() {
        setAdapterData(storage.refreshData()?.valutes)
    }

    private fun setAdapterData(data: LinkedHashMap<String, Valute>?) {
        data?.let { adapter.setData(it) }
        valuteView.displayTime(timeFormatter.format(Date(System.currentTimeMillis())))
    }

    override fun onStorageAutomaticallyUpdated() {
        setAdapterData(storage.getData().valutes)
    }
}