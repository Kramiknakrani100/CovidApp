package com.example.covidtracker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.covidtracker.api.ApiUtilites
import com.example.covidtracker.api.CountryData
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var totalConform: TextView? = null
    private var totalActive: TextView? = null
    private var totalRecovered: TextView? = null
    private var totalDeath: TextView? = null
    private var totalTests: TextView? = null
    private var todayConform: TextView? = null
    private var todayRecovered: TextView? = null
    private var todayDeath: TextView? = null
    private var date: TextView? = null
    private var pieChart: PieChart? = null
    private var mapimage: ImageView? = null
    private var list: MutableList<CountryData>? = null
    var country: String? = "India"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list = ArrayList()
        if (intent.getStringExtra("country") != null) country = intent.getStringExtra("country")
        init()
        val cname = findViewById<TextView>(R.id.cName)
        cname.text = country
        cname.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    CountryActivity::class.java
                )
            )
        }

        ApiUtilites.getApiInterface().countryData
            .enqueue(object : Callback<List<CountryData>?> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<List<CountryData>?>,
                    response: Response<List<CountryData>?>
                ) {
                    assert(response.body() != null)
                    (list as ArrayList<CountryData>).addAll(response.body()!!)
                    for (i in (list as ArrayList<CountryData>).indices) {
                        if ((list as ArrayList<CountryData>)[i].country == country) {
                            val conform = (list as ArrayList<CountryData>)[i].cases.toInt()
                            val active = (list as ArrayList<CountryData>)[i].active.toInt()
                            val recovered = (list as ArrayList<CountryData>)[i].recovered.toInt()
                            val death = (list as ArrayList<CountryData>)[i].deaths.toInt()
                            val datas = (list as ArrayList<CountryData>)[i]
                            val maplist = datas.countryInfo
                            val latitude = maplist["lat"]
                            val longitude = maplist["long"]
                            Log.d("Mainkaka", "lat is = $latitude")
                            Log.d("Mainkaka", "long is = $longitude")
                            mapimage!!.setOnClickListener {
                                val mapintent = Intent(this@MainActivity, MapsActivity::class.java)
                                mapintent.putExtra("lati", latitude)
                                mapintent.putExtra("longi", longitude)
                                mapintent.putExtra("country", country)
                                startActivity(mapintent)
                            }
                            totalConform!!.text =
                                NumberFormat.getInstance().format(conform.toLong())
                            totalActive!!.text = NumberFormat.getInstance().format(active.toLong())
                            totalRecovered!!.text =
                                NumberFormat.getInstance().format(recovered.toLong())
                            totalDeath!!.text = NumberFormat.getInstance().format(death.toLong())
                            todayDeath!!.text = "( +" + NumberFormat.getInstance()
                                .format((list as ArrayList<CountryData>)[i].todayDeaths.toInt().toLong()) + " )"
                            todayConform!!.text = "( +" + NumberFormat.getInstance()
                                .format((list as ArrayList<CountryData>)[i].todayCases.toInt().toLong()) + " )"
                            todayRecovered!!.text = "( +" + NumberFormat.getInstance()
                                .format((list as ArrayList<CountryData>)[i].todayRecovered.toInt().toLong()) + " )"
                            totalTests!!.text = NumberFormat.getInstance()
                                .format((list as ArrayList<CountryData>)[i].tests.toInt().toLong())
                            setText((list as ArrayList<CountryData>)[i].updated)
                            pieChart!!.addPieSlice(
                                PieModel(
                                    "Confirm",
                                    conform.toFloat(),
                                    resources.getColor(R.color.yellow)
                                )
                            )
                            pieChart!!.addPieSlice(
                                PieModel(
                                    "Active",
                                    active.toFloat(),
                                    resources.getColor(R.color.blue)
                                )
                            )
                            pieChart!!.addPieSlice(
                                PieModel(
                                    "Recovered",
                                    recovered.toFloat(),
                                    resources.getColor(R.color.green)
                                )
                            )
                            pieChart!!.addPieSlice(
                                PieModel(
                                    "Death",
                                    death.toFloat(),
                                    resources.getColor(R.color.red)
                                )
                            )
                            pieChart!!.startAnimation()
                        }
                    }
                }

                override fun onFailure(call: Call<List<CountryData>?>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Please Turn on Your Internet",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun setText(updated: String) {
        @SuppressLint("SimpleDateFormat") val format: DateFormat = SimpleDateFormat("MMM dd, yyyy")
        val milisecond = updated.toLong()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milisecond
        date!!.text = "Updated at " + format.format(calendar.time)
    }

    private fun init() {
        totalConform = findViewById(R.id.totalconform)
        totalActive = findViewById(R.id.totalactive)
        totalRecovered = findViewById(R.id.totalrecovered)
        totalDeath = findViewById(R.id.totaldeath)
        totalTests = findViewById(R.id.totaltest)
        todayConform = findViewById(R.id.todayconform)
        todayRecovered = findViewById(R.id.todayrecovered)
        todayDeath = findViewById(R.id.todaydaeth)
        pieChart = findViewById(R.id.piechart)
        date = findViewById(R.id.date)
        mapimage = findViewById(R.id.mapimage)
    }
}