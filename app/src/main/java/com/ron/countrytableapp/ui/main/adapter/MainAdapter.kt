package com.ron.countrytableapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ron.countrytableapp.R
import com.ron.countrytableapp.data.model.Country
import kotlinx.android.synthetic.main.item_layout.view.*

interface OnClickItem {
    fun onItemClick(country: Country)
}

class MainAdapter(
    private val countries: ArrayList<Country>,
    private val listener: OnClickItem?
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(country: Country) {
            itemView.textViewName.text = country.name
            itemView.textViewNativeName.text = country.nativeName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = countries.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int){
        val country = countries[position]
        holder.bind(countries[position])
        holder.itemView.setOnClickListener {
            listener?.onItemClick(country)
        }
    }


    fun addData(list: List<Country>) {
        countries.clear()
        countries.addAll(list)
    }
}