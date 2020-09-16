package com.example.currencyexchanger.presenter.valutes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchanger.R
import com.example.currencyexchanger.model.pojo.Valute

class MyAdapter(var valutes: LinkedHashMap<String, Valute>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(valutes.values.elementAt(position))
    }

    override fun getItemCount(): Int = valutes.size

    fun setData(data: LinkedHashMap<String, Valute>) {
        valutes = data
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val charCode: TextView = view.findViewById(R.id.char_code)
        private val nominal: TextView = view.findViewById(R.id.nominal)
        private val name: TextView = view.findViewById(R.id.name)
        private val value: TextView = view.findViewById(R.id.value)

        fun bind(valute: Valute) {
            charCode.text = valute.charCode
            nominal.text = valute.nominal.toString()
            name.text = valute.name
            value.text = valute.value.toString()
        }
    }
}