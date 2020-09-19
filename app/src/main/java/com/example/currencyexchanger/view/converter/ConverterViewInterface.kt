package com.example.currencyexchanger.view.converter

interface ConverterViewInterface {
    fun setRateFrom(str: String)
    fun setRateTo(str: String)
    fun setNumToOppositeEditText(editTextId: Int, str: String)

    fun getSelectedSpinnerFromItem(): String
    fun getSelectedSpinnerToItem(): String
    fun setDataToSpinners(data: List<String>)
    fun setSelectionToSpinnerFrom(position: Int)
    fun setSelectionToSpinnerTo(position: Int)

    fun getNumToConvert(): String
    fun displayDate(date: String)
}