package com.example.currencyexchanger.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchanger.R
import com.example.currencyexchanger.MyAdapter
import com.example.currencyexchanger.presenter.MainPresenter
import com.example.currencyexchanger.presenter.MainPresenterInterface
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityInterface {

    private lateinit var recyclerView: RecyclerView
    private lateinit var presenter: MainPresenterInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = recycler_view
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        presenter = MainPresenter(this)
    }

    override fun getActivityLifecycle(): Lifecycle = lifecycle

    override fun setAdapter(adapter: MyAdapter) {
        recyclerView.adapter = adapter
    }
}