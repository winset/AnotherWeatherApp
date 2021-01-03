package com.example.anotherweatherapp.ui.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.anotherweatherapp.data.model.Hourly;
import com.example.anotherweatherapp.utils.DateUtils;
import com.example.anotherweatherapp.utils.ImageUtils;
import com.example.anotherweatherapp.utils.MetricUtils;
import com.example.anotherweatherapp.R;

import java.util.ArrayList;
import java.util.List;

public class HourlyForecastAdapter extends RecyclerView.Adapter<HourlyForecastAdapter.HourlyForecastViewHolder> {

    private List<Hourly> hourlyForecastList = new ArrayList<>();
    private Context context;

    public HourlyForecastAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HourlyForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_forecast_item, parent, false);
        return new HourlyForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyForecastViewHolder holder, int position) {
        holder.bind(hourlyForecastList.get(position));
    }

    @Override
    public int getItemCount() {

        return hourlyForecastList.size();
    }

    public void addData(List<Hourly> hourlyForecastsInfoList) {
        this.hourlyForecastList.clear();
        this.hourlyForecastList.addAll(hourlyForecastsInfoList);
        /*  Log.d(MainFragment.TAG, "addData: " + hourlyForecastsInfoList.get(1).getDateTime());*/
        notifyDataSetChanged();
    }

    class HourlyForecastViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private ImageView imageView;
        private TextView temp;


        public HourlyForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.hourly_date_textView);
            imageView = itemView.findViewById(R.id.hourly_image_iv);
            temp = itemView.findViewById(R.id.hourly_temp_textView);
        }

        public void bind(Hourly hourlyForecasts) {
            long dateS = hourlyForecasts.getDt();
            String temperature = String.valueOf(MetricUtils.kelvinToCelsius(hourlyForecasts.getTemp()));
            Drawable drawable = ImageUtils.getImageFromId(context, hourlyForecasts.getWeather().get(0).getId());
            date.setText(DateUtils.format(dateS));
            temp.setText(temperature);
            imageView.setImageDrawable(drawable);
        }
    }
}
