package com.example.currencyexchanger.view.converter

import android.widget.SpinnerAdapter

interface ConverterViewInterface {
    fun setRateFrom(str: String)
    fun setRateTo(str: String)
    fun setConvertedNum(str: String)
    fun setNumToConvert(str: String)

    fun getSelectedSpinnerFromItem(): String
    fun getSelectedSpinnerToItem(): String
    fun setDataToSpinners(data: List<String>)
    fun setSelectionToSpinnerFrom(position: Int)
    fun setSelectionToSpinnerTo(position: Int)

    fun getNumToConvert(): String
}