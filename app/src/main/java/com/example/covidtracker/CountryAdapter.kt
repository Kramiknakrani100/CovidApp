package com.example.covidtracker

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.covidtracker.api.CountryData
import java.text.NumberFormat

class CountryAdapter(private val context: Context, private var list: List<CountryData>) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem =
            layoutInflater.inflate(R.layout.country_item, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.cases.text = NumberFormat.getInstance().format(data.cases.toInt().toLong())
        holder.countryname.text = data.country
        holder.sno.text = (position + 1).toString()
        val img = data.countryInfo
        Glide.with(context).load(img["flag"]).into(holder.flag)
        holder.itemView.setOnClickListener { v: View? ->
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("country", data.country)
            context.startActivity(intent)
        }
    }

    fun filterList(filterlist: List<CountryData>) {
        list = filterlist
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sno: TextView
        val countryname: TextView
        val cases: TextView
        val flag: ImageView

        init {
            sno = itemView.findViewById(R.id.sno)
            countryname = itemView.findViewById(R.id.country_name)
            cases = itemView.findViewById(R.id.cases)
            flag = itemView.findViewById(R.id.flag)
        }
    }
}