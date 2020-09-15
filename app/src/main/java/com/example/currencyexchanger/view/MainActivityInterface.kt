package com.example.currencyexchanger.view

import androidx.lifecycle.Lifecycle
import com.example.currencyexchanger.MyAdapter

interface MainActivityInterface {
    fun getActivityLifecycle(): Lifecycle
    fun setAdapter(adapter: MyAdapter)
}