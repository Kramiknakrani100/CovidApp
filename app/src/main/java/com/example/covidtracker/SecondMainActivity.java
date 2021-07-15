package com.example.covidtracker;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidtracker.apisecond.Adapter;
import com.example.covidtracker.apisecond.ApiSecondUtilites;
import com.example.covidtracker.apisecond.Models.CovidData;
import com.example.covidtracker.apisecond.Models.covid;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondMainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<CovidData> covidData = new ArrayList<>();
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_main);

        mRecyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        Call<covid> call;

        call = ApiSecondUtilites.getInstance().getApi().getCentre("400040", "31-05-2021");
        call.enqueue(new Callback<covid>() {
            @Override
            public void onResponse(Call<covid> call, Response<covid> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getCenters() != null) {
                        covidData.clear();
                        covidData = response.body().getCenters();
                        adapter = new Adapter(covidData, SecondMainActivity.this);
                        mRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<covid> call, Throwable t) {
                Toast.makeText(SecondMainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}