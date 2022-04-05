package com.example.covidtracker

import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covidtracker.api.ApiUtilites
import com.example.covidtracker.api.CountryData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class CountryActivity : AppCompatActivity() {
    private var list: MutableList<CountryData>? = null
    private var dialog: ProgressDialog? = null
    private var searchbar: EditText? = null
    private var adapter: CountryAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)
        val recyclerView = findViewById<RecyclerView>(R.id.countries)
        searchbar = findViewById(R.id.search_bar)
        list = ArrayList()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        adapter = CountryAdapter(this, list as ArrayList<CountryData>)
        recyclerView.adapter = adapter
        dialog = ProgressDialog(this)
        dialog!!.setMessage("Loading...")
        dialog!!.setCancelable(false)
        dialog!!.show()

        ApiUtilites.apiInterface.countryData.enqueue(object : Callback<List<CountryData>> {
            override fun onResponse(
                call: Call<List<CountryData>>,
                response: Response<List<CountryData>>
            ) {
                assert(response.body() != null)
                (list as ArrayList<CountryData>).addAll(response.body()!!)
                adapter!!.notifyDataSetChanged()
                dialog!!.dismiss()
            }

            override fun onFailure(call: Call<List<CountryData>>, t: Throwable) {
                Toast.makeText(this@CountryActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })

        searchbar?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })

    }

    private fun filter(text: String) {
        val filterlist: MutableList<CountryData> = ArrayList()
        for (item in list!!) {
            if (item.country.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))) {
                filterlist.add(item)
            }
        }
        adapter!!.filterList(filterlist)
    }
}