package com.example.covidtracker.apisecond;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidtracker.R;
import com.example.covidtracker.apisecond.Models.CovidData;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final List<CovidData> coviddata;
    private final Context context;

    public Adapter(List<CovidData> coviddata, Context context) {
        this.coviddata = coviddata;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.seconditem,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

    CovidData c = coviddata.get(position);
    holder.state.setText(c.getState_name());
    holder.district.setText(c.getDistrict_name());
    holder.fee.setText(c.getFee_type());
    holder.name.setText(c.getName());

    }

    @Override
    public int getItemCount() {
        return coviddata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView state, district, fee, name;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            state = itemView.findViewById(R.id.state);
            district = itemView.findViewById(R.id.district);
            fee = itemView.findViewById(R.id.fee);
            name = itemView.findViewById(R.id.name);

        }

    }
}
