package com.example.covidtracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.covidtracker.api.ApiUtilites;
import com.example.covidtracker.api.CountryData;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MainActivity extends AppCompatActivity{

    private TextView totalConform, totalActive, totalRecovered, totalDeath, totalTests;
    private TextView todayConform, todayRecovered, todayDeath, date;
    private TextView second;
    private PieChart pieChart;
    private ImageView mapimage;

    private List<CountryData> list;
    String country = "India";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        if (getIntent().getStringExtra("country") != null)
            country = getIntent().getStringExtra("country");

        init();


        TextView cname = findViewById(R.id.cName);
        cname.setText(country);

        cname.setOnClickListener(v -> startActivity(new Intent(MainActivity.this,CountryActivity.class)));

        ApiUtilites.getApiInterface().getCountryData()
                .enqueue(new Callback<List<CountryData>>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {

                        assert response.body() != null;
                        list.addAll(response.body());

                        for (int i = 0; i < list.size(); i++){
                            if (list.get(i).getCountry().equals(country)){
                                int conform = Integer.parseInt(list.get(i).getCases());
                                int active = Integer.parseInt(list.get(i).getActive());
                                int recovered = Integer.parseInt(list.get(i).getRecovered());
                                int death = Integer.parseInt(list.get(i).getDeaths());

                                CountryData datas = list.get(i);

                                Map<String,String> maplist = datas.getCountryInfo();

                                //holder.sno.setText(img.get("long"));

                                String latitude = maplist.get("lat");
                                String longitude = maplist.get("long");
                                Log.d("Mainkaka","lat is = "+latitude);
                                Log.d("Mainkaka","long is = "+longitude);

                                mapimage.setOnClickListener(v -> {

                                Intent mapintent = new Intent(MainActivity.this, MapsActivity.class);
                                mapintent.putExtra("lati", latitude);
                                mapintent.putExtra("longi", longitude);
                                mapintent.putExtra("country", country);
                                startActivity(mapintent);
                            });


                                totalConform.setText(NumberFormat.getInstance().format(conform));
                                totalActive.setText(NumberFormat.getInstance().format(active));
                                totalRecovered.setText(NumberFormat.getInstance().format(recovered));
                                totalDeath.setText(NumberFormat.getInstance().format(death));

                                todayDeath.setText("( +"+NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths()))+" )");
                                todayConform.setText("( +"+NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases()))+" )");
                                todayRecovered.setText("( +"+NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered()))+" )");
                                totalTests.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTests())));

                                setText(list.get(i).getUpdated());

                                pieChart.addPieSlice(new PieModel("Confirm", conform, getResources().getColor(R.color.yellow)));
                                pieChart.addPieSlice(new PieModel("Active", active, getResources().getColor(R.color.blue)));
                                pieChart.addPieSlice(new PieModel("Recovered", recovered, getResources().getColor(R.color.green)));
                                pieChart.addPieSlice(new PieModel("Death", death, getResources().getColor(R.color.red)));
                                pieChart.startAnimation();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<List<CountryData>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Please Turn on Your Internet", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    private void setText(String updated) {

        @SuppressLint("SimpleDateFormat") DateFormat format = new SimpleDateFormat("MMM dd, yyyy");

        long milisecond = Long.parseLong(updated);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milisecond);

        date.setText("Updated at "+format.format(calendar.getTime()));
    }

    private void init(){

        totalConform = findViewById(R.id.totalconform);
        totalActive = findViewById(R.id.totalactive);
        totalRecovered = findViewById(R.id.totalrecovered);
        totalDeath = findViewById(R.id.totaldeath);
        totalTests = findViewById(R.id.totaltest);

        todayConform = findViewById(R.id.todayconform);
        todayRecovered = findViewById(R.id.todayrecovered);
        todayDeath = findViewById(R.id.todaydaeth);

        pieChart = findViewById(R.id.piechart);

        date = findViewById(R.id.date);

        mapimage = findViewById(R.id.mapimage);


    }
}