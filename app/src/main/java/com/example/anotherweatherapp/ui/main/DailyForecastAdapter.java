package com.example.anotherweatherapp.ui.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.anotherweatherapp.data.model.Daily;
import com.example.anotherweatherapp.utils.DateUtils;
import com.example.anotherweatherapp.utils.ImageUtils;
import com.example.anotherweatherapp.utils.MetricUtils;
import com.example.anotherweatherapp.R;

import java.util.ArrayList;
import java.util.List;

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.DailyForecastViewHolder> {

    private List<Daily> dailyForecastList = new ArrayList<>();
    private Context context;

    public DailyForecastAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public DailyForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_forecast_item, parent, false);
        return new DailyForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyForecastViewHolder holder, int position) {
        holder.bind(dailyForecastList.get(position));
    }

    @Override
    public int getItemCount() {
        Log.d(MainFragment.TAG, "getItemCount: " + dailyForecastList.size());
        return dailyForecastList.size();
    }

    public void addData(List<Daily> dailyForecastList) {
        this.dailyForecastList.clear();
        this.dailyForecastList.addAll(dailyForecastList);
        Log.d(MainFragment.TAG, "addData: " + dailyForecastList.size());
        notifyDataSetChanged();
    }

    class DailyForecastViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private ImageView image;
        private TextView temp;


        public DailyForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.day_of_week_tv);
            image = itemView.findViewById(R.id.daily_image_iv);
            temp = itemView.findViewById(R.id.daily_temp_tv);
        }

        public void bind(Daily dailyForecast) {
            Log.d(MainFragment.TAG, "time: " + dailyForecast.getDt());
            String dateS = DateUtils.getDayOfWeekFromDate(dailyForecast.getDt());
            String temperature = String.valueOf(MetricUtils.kelvinToCelsius(dailyForecast.getTemp().getMax()));
            Drawable drawable = ImageUtils.getImageFromId(context, dailyForecast.getWeather().get(0).getId());
            date.setText(dateS);
            //  Log.d(MainFragment.TAG, "bind: " + dateS+ " " + temperature);
            image.setImageDrawable(drawable);
            temp.setText(temperature);
        }
    }
}
