package com.example.covidtracker;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class offlineActivity extends AppCompatActivity {

    Button button;
    TextView textView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        button.setOnClickListener(v -> {

        });

        if (!isconnected()) {
//          Toast.makeText(context, "No intertnet ", Toast.LENGTH_SHORT).show();
            Log.d("chek","No net");
        } else {
//            Toast.makeText(context, "Yehh it is done ", Toast.LENGTH_SHORT).show();
            Log.d("chek","net");
            Intent intent = new Intent(offlineActivity.this,MainActivity.class);
            startActivity(intent);

        }

    }

    private boolean isconnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo()!=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}