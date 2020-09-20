package com.example.anotherweatherapp.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anotherweatherapp.R;
import com.example.anotherweatherapp.data.model.HourlyForecastsInfo;
import com.example.anotherweatherapp.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HourlyForecastAdapter extends RecyclerView.Adapter<HourlyForecastAdapter.HourlyForecastViewHolder> {

    private List<HourlyForecastsInfo> hourlyForecastsInfoList = new ArrayList<>();

    @Inject
    public HourlyForecastAdapter() {

    }

    @NonNull
    @Override
    public HourlyForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_forecast_item,parent,false);
        return new HourlyForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyForecastViewHolder holder, int position) {
        holder.bind(hourlyForecastsInfoList.get(position));
    }

    @Override
    public int getItemCount() {

        return hourlyForecastsInfoList.size();
    }

    public void addData(List<HourlyForecastsInfo> hourlyForecastsInfoList){
        this.hourlyForecastsInfoList.clear();
        this.hourlyForecastsInfoList.addAll(hourlyForecastsInfoList);
      /*  Log.d(MainFragment.TAG, "addData: " + hourlyForecastsInfoList.get(1).getDateTime());*/
        notifyDataSetChanged();
    }

    class HourlyForecastViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView temp;


        public HourlyForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_textView);
            temp = itemView.findViewById(R.id.temp_textView);
        }

        public void bind(HourlyForecastsInfo hourlyForecastsInfo){
            long dateS = hourlyForecastsInfo.getEpochDateTime();
            String temperature = hourlyForecastsInfo.getTemperature().getValue().toString();
            date.setText(DateUtils.format(dateS));
            temp.setText(temperature);
        }
    }
}
