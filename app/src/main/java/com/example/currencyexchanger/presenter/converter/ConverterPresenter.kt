package com.example.currencyexchanger.presenter.converter

import android.util.Log
import com.example.currencyexchanger.R
import com.example.currencyexchanger.model.Storage
import com.example.currencyexchanger.model.pojo.Valute
import com.example.currencyexchanger.model.pojo.ValuteInfo
import com.example.currencyexchanger.view.converter.ConverterViewInterface
import kotlinx.coroutines.runBlocking
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class ConverterPresenter(val view: ConverterViewInterface): ConverterPresenterInterface,
    Storage.AutoDataUpdateNotificationsListener {

    private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy")
    private val module: Storage = Storage.instance
    private var charCodeFrom: String = "RUR"
    private var charCodeTo: String = "USD"

    init {
        module.subscribeOnAutoUpdNotifications(this)
        setDataToSpinners()
        displayDate()
    }

    private fun getStorageData(): ValuteInfo {
        val data = module.getData().getCopy()
        if (!data.valutes.containsKey("RUR")) {
            data.valutes["RUR"] = Valute("RUR", 1, "Российский рубль", 1.0)
        }
        return data
    }

    private fun setDataToSpinners() {
        val data = getStorageData()
        val spinnerStrings = data.valutes.map {
                entry -> entry.value.charCode + " " + entry.value.name
        }.toMutableList()

        runBlocking {
            view.setDataToSpinners(spinnerStrings)

            view.setSelectionToSpinnerFrom(
                spinnerStrings.indexOf(data.valutes[charCodeFrom].toString()))
            view.setSelectionToSpinnerTo(
                spinnerStrings.indexOf(data.valutes[charCodeTo].toString()))
        }
    }

    override fun newValuteSelected() {
        charCodeFrom = getCharCodeFromStr(view.getSelectedSpinnerFromItem())
        charCodeTo = getCharCodeFromStr(view.getSelectedSpinnerToItem())

        val rateFrom = convert(charCodeFrom, charCodeTo)
        val rateTo = convert(charCodeTo, charCodeFrom)

        view.setRateFrom("1 $charCodeFrom = %.4f $charCodeTo".format(rateFrom))
        view.setRateTo("1 $charCodeTo = %.4f $charCodeFrom".format(rateTo))

        convertNumFromView(view.getNumToConvert(), R.id.num_to_convert)
    }

    override fun convertNumFromView(strWithNum: CharSequence?, viewId: Int) {
        strWithNum?.toString().run {
            val num = if (this!!.isEmpty()) 0.0 else this.toDouble()

            var result = 0.0
            when (viewId) {
                R.id.num_to_convert -> result = convert(charCodeFrom, charCodeTo, num)
                R.id.num_converted -> result = convert(charCodeTo, charCodeFrom, num)
            }
            view.setNumToOppositeEditText(viewId, if (result == 0.0) "" else decFormat(result))
        }
    }

    private fun decFormat(value: Double) = "%.4f".format(value).replace(',', '.')

    private fun getCharCodeFromStr(str: String) = str.split(" ")[0]

    private fun convert(charCodeFrom: String, charCodeTo: String, convertValue: Double = 1.0): Double {
        val valute1 = getStorageData().valutes[charCodeFrom]
        val valute2 = getStorageData().valutes[charCodeTo]
        if (valute1 == null || valute2 == null) {
            return 0.0
        }
        return convert(valute1, valute2, convertValue)
    }

    private fun convert(valuteFrom: Valute, valuteTo: Valute, convertValue: Double) =
        (valuteFrom.value / valuteFrom.nominal / valuteTo.value) * convertValue

    override fun onStorageAutomaticallyUpdated() {
        displayDate()
    }

    private fun displayDate() {
        val date = getStorageData().date
        view.displayDate(ZonedDateTime.parse(date).format(dateFormatter))
    }
}