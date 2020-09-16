package com.example.currencyexchanger.view

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.currencyexchanger.view.converter.ConverterFragment
import com.example.currencyexchanger.view.valutes.ValutesFragment


class MyFragmentPageAdapter(activity: AppCompatActivity, val size: Int): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = size

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return ValutesFragment()
        }
        return ConverterFragment()
    }
}