package com.example.currencyexchanger.presenter.converter

interface ConverterPresenterInterface {
    fun newValuteSelected()
    fun convertNumFromView(strWithNum: CharSequence?, viewId: Int)
}