package com.example.currencyexchanger.view.valutes

import androidx.lifecycle.Lifecycle
import com.example.currencyexchanger.presenter.valutes.MyAdapter

interface ValuteViewInterface {
    fun getActivityLifecycle(): Lifecycle
    fun setAdapter(adapter: MyAdapter)
    fun displayTime(time: String)
    fun displayDate(date: String)
}