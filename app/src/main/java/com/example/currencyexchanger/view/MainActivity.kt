package com.example.currencyexchanger.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.currencyexchanger.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabsNames = listOf(
            resources.getString(R.string.first_tab),
            resources.getString(R.string.second_tab)
        )

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = MyFragmentPageAdapter(this, tabsNames.size)

        val tabLayout: TabLayout = findViewById(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            tab.text = tabsNames[position]
        }.attach()
    }

}