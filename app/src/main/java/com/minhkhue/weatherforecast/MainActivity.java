package com.minhkhue.weatherforecast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private EditText edtSearch;
    private Button btnSearch, btnChangeActivity;
    private TextView tvCity, tvCountry, tvDay,tvTemperature, tvStatus, tvClound, tvHumidity, tvWind;
    private ImageView imgIcon;
    private String currentCity = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapping();
        getCurrentWeatherData("Ha Noi");
        btnSearch.setOnClickListener(v -> {
           String city = edtSearch.getText().toString().trim();
           if (city.equals("")){
               currentCity = "Ha Noi";
           }
           else {
               currentCity = city;
           }
            getCurrentWeatherData(currentCity);
            getCurrentWeatherData(city);
        });
        btnChangeActivity.setOnClickListener(v -> {
            String city = edtSearch.getText().toString().trim();
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("City", city);
            startActivity(intent);
        });
    }

    public void getCurrentWeatherData(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=3c9fbec2a39a57987c6d4d62b337b3c8";
        @SuppressLint("SetTextI18n") StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response ->
        {
            try {
                JSONObject jsonObject = new JSONObject(response);

                String location = jsonObject.getString("name");
                tvCity.setText("City: "+location);

                String day = jsonObject.getString("dt");
                long l = Long.parseLong(day);
                Date date = new Date(l*1000L);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH:mm");
                String Day = simpleDateFormat.format(date);
                tvDay.setText(Day);

                JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                String status = jsonObjectWeather.getString("main");
                String icon = jsonObjectWeather.getString("icon");
                Picasso.get().load("https://openweathermap.org/img/w/"+icon+".png").into(imgIcon);
                tvStatus.setText(status);

                JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                String temperature = jsonObjectMain.getString("temp");
                String Humidity = jsonObjectMain.getString("humidity");
                double a = Double.parseDouble(temperature);
                String Temperature = String.valueOf((int) a);
                tvTemperature.setText(Temperature+" °C");
                tvHumidity.setText(Humidity+"%");

                JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                String wind = jsonObjectWind.getString("speed");
                tvWind.setText(wind+" m/s");

                JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                String cloud = jsonObjectCloud.getString("all");
                tvClound.setText(cloud+ "%");

                JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                String country = jsonObjectSys.getString("country");
                tvCountry.setText("Country: "+country);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        },
                error -> {

                });
        requestQueue.add(stringRequest);
    }
    private void mapping() {
        edtSearch = findViewById(R.id.edt_search);
        btnSearch = findViewById(R.id.btn_serch);
        btnChangeActivity = findViewById(R.id.btn_changeActivity);
        tvCity = findViewById(R.id.tv_city);
        tvCountry = findViewById(R.id.tv_country);
        tvDay = findViewById(R.id.tv_day);
        tvTemperature = findViewById(R.id.tv_temperature);
        tvStatus = findViewById(R.id.tv_status);
        tvClound = findViewById(R.id.tv_cloud);
        tvHumidity = findViewById(R.id.tv_humidity);
        tvWind = findViewById(R.id.tv_wind);
        imgIcon = findViewById(R.id.imv_icon);
    }
}