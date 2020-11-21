package com.minhkhue.weatherforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<Weather> weatherArrayList;

    public Adapter(Context context, ArrayList<Weather> weatherArrayList) {
        this.context = context;
        this.weatherArrayList = weatherArrayList;
    }

    @Override
    public int getCount() {
        return weatherArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return weatherArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.listview_item, null);
        Weather weather = weatherArrayList.get(position);

        TextView tvDay = convertView.findViewById(R.id.tv_Day);
        TextView tvStatus = convertView.findViewById(R.id.tv_Status);
        TextView tvMaxTemp = convertView.findViewById(R.id.max_temp);
        TextView tvMinTemp = convertView.findViewById(R.id.min_temp);
        ImageView iconWeather = convertView.findViewById(R.id.icon_weather);

        tvDay.setText(weather.day);
        tvStatus.setText(weather.status);
        tvMaxTemp.setText(weather.maxTemp+"°C");
        tvMinTemp.setText(weather.minTemp+"°C");
        Picasso.get().load("https://openweathermap.org/img/w/"+weather.icon+".png").into(iconWeather);

        return convertView;
    }
}
