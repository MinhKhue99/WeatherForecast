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
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvDay = convertView.findViewById(R.id.tv_Day);
            viewHolder.tvStatus = convertView.findViewById(R.id.tv_Status);
            viewHolder.tvMaxTemp = convertView.findViewById(R.id.max_temp);
            viewHolder.tvMinTemp = convertView.findViewById(R.id.min_temp);
            viewHolder.iconWeather = convertView.findViewById(R.id.icon_weather);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Weather weather = weatherArrayList.get(position);
        viewHolder.tvDay.setText(weather.day);
        viewHolder.tvStatus.setText(weather.status);
        viewHolder.tvMaxTemp.setText(weather.maxTemp+"°C");
        viewHolder.tvMinTemp.setText(weather.minTemp+"°C");
        Picasso.get().load("https://openweathermap.org/img/w/"+weather.icon+".png").into(viewHolder.iconWeather);
        return convertView;
    }
    public class ViewHolder {
        private TextView tvDay;
        private TextView tvStatus;
        private TextView tvMaxTemp;
        private TextView tvMinTemp;
        private ImageView iconWeather;
    }
}
