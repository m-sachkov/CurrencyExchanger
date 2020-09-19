package com.example.currencyexchanger.view.converter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.currencyexchanger.R
import com.example.currencyexchanger.presenter.converter.ConverterPresenter
import com.example.currencyexchanger.presenter.converter.ConverterPresenterInterface
import kotlinx.android.synthetic.main.converter_fragment.view.*


class ConverterFragment: Fragment(), ConverterViewInterface {

    private lateinit var valutesFrom: Spinner
    private lateinit var valutesTo: Spinner
    private lateinit var numToConvert: EditText
    private lateinit var convertedNum: EditText
    private lateinit var rateFrom: TextView
    private lateinit var rateTo: TextView
    private lateinit var date: TextView
    private lateinit var bufferedContext: Context

    private lateinit var imm: InputMethodManager
    private var changeSpinnerSelectionProgrammatically = false
    private var changeEditTextDataProgrammatically = false

    private lateinit var presenter: ConverterPresenterInterface

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.converter_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        valutesFrom = view.valutes_from
        valutesTo = view.valutes_to
        numToConvert = view.num_to_convert
        convertedNum = view.num_converted
        rateFrom = view.rate_from
        rateTo = view.rate_to
        date = view.date

        presenter = ConverterPresenter(this)
        valutesFrom.onItemSelectedListener = onSpinnerItemSelected()
        valutesTo.onItemSelectedListener = onSpinnerItemSelected()

        numToConvert.addTextChangedListener(textWatcherWithView(numToConvert))
        convertedNum.addTextChangedListener(textWatcherWithView(convertedNum))
        numToConvert.setOnFocusChangeListener { v, hasFocus -> editTextFocusChanged(v, hasFocus) }
        convertedNum.setOnFocusChangeListener { v, hasFocus -> editTextFocusChanged(v, hasFocus) }

        imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        view.reverse_btn.setOnClickListener {
            val t = valutesFrom.selectedItemPosition
            setSelectionToSpinnerFrom(valutesTo.selectedItemPosition)
            setSelectionToSpinnerTo(t)

            presenter.newValuteSelected()
        }
    }

    override fun onResume() {
        super.onResume()
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
        numToConvert.requestFocus()
    }

    private fun editTextFocusChanged(view: View, hasFocus: Boolean) {
        if (view is EditText && !hasFocus) {
            val text = view.text.toString()
            if(text.isNotEmpty() && text.toDouble() == 0.0) view.setText("")
        }
    }

    private fun textWatcherWithView(view: View) = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (!changeEditTextDataProgrammatically) {
                s?.let { presenter.convertNumFromView(it, view.id) }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private fun onSpinnerItemSelected() =
         object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (!changeSpinnerSelectionProgrammatically) {
                    presenter.newValuteSelected()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bufferedContext = context
    }

    override fun setRateFrom(str: String) {
        rateFrom.text = str
    }

    override fun setRateTo(str: String) {
        rateTo.text = str
    }

    override fun setNumToOppositeEditText(editTextId: Int, str: String) {
        changeEditTextDataProgrammatically = true
        when (editTextId) {
            R.id.num_to_convert -> convertedNum.setText(str)
            R.id.num_converted -> numToConvert.setText(str)
        }
        changeEditTextDataProgrammatically = false
    }

    override fun getSelectedSpinnerFromItem() =
        valutesFrom.selectedItem.toString()


    override fun getSelectedSpinnerToItem() =
        valutesTo.selectedItem.toString()

    override fun setDataToSpinners(data: List<String>) {
        val adapter = context?.let {
                ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, data)
        }
        valutesTo.adapter = adapter
        valutesFrom.adapter = adapter
    }

    private fun setSpinnerSelectionProgrammatically(spinner: Spinner, position: Int) {
        changeSpinnerSelectionProgrammatically = true
        spinner.setSelection(position)
        changeSpinnerSelectionProgrammatically = false
    }

    override fun setSelectionToSpinnerFrom(position: Int) {
        setSpinnerSelectionProgrammatically(valutesFrom, position)
    }

    override fun setSelectionToSpinnerTo(position: Int) {
        setSpinnerSelectionProgrammatically(valutesTo, position)
    }

    override fun getNumToConvert() = numToConvert.text.toString()

    override fun displayDate(date: String) {
        this.date.text = date
    }

}