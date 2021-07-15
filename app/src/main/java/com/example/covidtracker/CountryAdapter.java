package com.example.covidtracker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covidtracker.api.CountryData;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private Context context;
    private List<CountryData> list;

    public CountryAdapter(Context context, List<CountryData> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.country_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CountryAdapter.ViewHolder holder, int position) {

        CountryData data = list.get(position);

        holder.cases.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getCases())));
        holder.countryname.setText(data.getCountry());
        holder.sno.setText(String.valueOf(position+1));

        Map<String,String> img = data.getCountryInfo();

        //holder.sno.setText(img.get("long"));
//
//        String latitude = img.get("lat");
//        String longitude = img.get("long");
//        Log.d("kaka","lat is = "+latitude);
//        Log.d("kaka","long is = "+longitude);

        Glide.with(context).load(img.get("flag")).into(holder.flag);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context,MainActivity.class);
            intent.putExtra("country",data.getCountry());
            context.startActivity(intent);
        });
    }

    public void filterList(List<CountryData> filterlist){
        list = filterlist;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView sno,countryname,cases;
        private ImageView flag;

        public ViewHolder(View itemView) {
            super(itemView);

            sno = itemView.findViewById(R.id.sno);
            countryname = itemView.findViewById(R.id.country_name);
            cases = itemView.findViewById(R.id.cases);
            flag = itemView.findViewById(R.id.flag);
        }
    }
}
