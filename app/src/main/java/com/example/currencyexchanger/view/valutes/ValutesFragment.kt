package com.example.currencyexchanger.view.valutes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchanger.R
import com.example.currencyexchanger.presenter.valutes.MyAdapter
import com.example.currencyexchanger.presenter.valutes.ValutesPresenter
import com.example.currencyexchanger.presenter.valutes.ValutesPresenterInterface
import kotlinx.android.synthetic.main.valutes_fragment.*
import kotlinx.android.synthetic.main.valutes_fragment.view.*

class ValutesFragment: Fragment(), ValuteViewInterface {

    private lateinit var recyclerView: RecyclerView
    private lateinit var presenter: ValutesPresenterInterface
    private lateinit var lastUpdateTime: TextView
    private lateinit var dateFiled: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.valutes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = recycler_view
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        lastUpdateTime = view.lastUpdateTimeFiled
        dateFiled = view.date_field

        presenter = ValutesPresenter(this)

        view.refresh_btn.setOnClickListener { presenter.refreshData() }
    }

    override fun getActivityLifecycle(): Lifecycle {
        return lifecycle
    }

    override fun setAdapter(adapter: MyAdapter) {
        recyclerView.adapter = adapter
    }

    override fun displayTime(time: String) {
        lastUpdateTime.text = time
    }

    override fun displayDate(date: String) {
        dateFiled.text = date
    }

    override fun toString(): String {
        return "Список"
    }
}