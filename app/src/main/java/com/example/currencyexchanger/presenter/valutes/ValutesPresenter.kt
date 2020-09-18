package com.example.currencyexchanger.presenter.valutes

import com.example.currencyexchanger.model.Storage
import com.example.currencyexchanger.model.pojo.ValuteInfo
import com.example.currencyexchanger.view.valutes.ValuteViewInterface
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.LinkedHashMap

class ValutesPresenter(val valuteView: ValuteViewInterface): ValutesPresenterInterface, Storage.AutoDataUpdateNotificationsListener {

    private val adapter: MyAdapter = MyAdapter(LinkedHashMap())
    private val storage: Storage = Storage.instance
    private val timeFormatter = SimpleDateFormat("HH:mm:ss", Locale.US)
    private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy")

    init {
        setData(storage.getData())
        valuteView.setAdapter(adapter)
        storage.subscribeOnAutoUpdNotifications(this)
    }

    override fun refreshData() {
        storage.refreshData()
    }

    private fun setData(data: ValuteInfo?) {
        data?.let {
            adapter.setData(it.valutes)
            valuteView.displayDate(
                ZonedDateTime.parse(it.date).format(dateFormatter))
            valuteView.displayTime(
                timeFormatter.format(Date(System.currentTimeMillis())))
        }
    }

    override fun onStorageAutomaticallyUpdated() {
        setData(storage.getData())
    }
}