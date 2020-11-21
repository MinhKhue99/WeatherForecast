package com.minhkhue.weatherforecast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    private String city="";
    private ImageView btnBack;
    private TextView tvCity;
    private ListView listView;
    private Adapter adapter;
    private ArrayList<Weather> weatherArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String City = intent.getStringExtra("City");
        if (city.equals("")){
            city = "Ha Noi";
            get7DaysData(city);
        }
        else {
            city = City;
            get7DaysData(city);
        }
        mapping();
        btnBack.setOnClickListener(v -> onBackPressed());
    }

    private void mapping() {
        btnBack = findViewById(R.id.btn_back);
        tvCity = findViewById(R.id.tv_City);
        listView = findViewById(R.id.lv_7);
        weatherArrayList = new ArrayList<Weather>();
        adapter = new Adapter(MainActivity2.this, weatherArrayList);
        listView.setAdapter(adapter);
    }

    private void get7DaysData(String data) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=hanoi&cnt=7&appid=3c9fbec2a39a57987c6d4d62b337b3c8";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                        String city = jsonObjectCity.getString("name");
                        tvCity.setText(city);

                        JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                        for (int i=0;i<jsonArrayList.length();i++){

                            JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                            String day = jsonObjectList.getString("dt");
                            long l = Long.parseLong(day);
                            Date date = new Date(l*1000L);
                            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                            String Day = simpleDateFormat.format(date);


                            JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("temp");

                            String maxTemp = jsonObjectTemp.getString("max");
                            double a = Double.parseDouble(maxTemp);
                            String TempMax = String.valueOf((int) a);

                            String minTemp = jsonObjectTemp.getString("min");
                            double b = Double.parseDouble(minTemp);
                            String TempMin = String.valueOf((int) b);


                            JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("description");

                            String icon = jsonObjectWeather.getString("icon");
                            weatherArrayList.add(new Weather(Day, icon, status,TempMin,TempMax));
                        }
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {

                });
        requestQueue.add(stringRequest);
    }
}