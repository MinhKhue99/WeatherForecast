package com.minhkhue.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
               getCurrentWeatherData(currentCity);
           }
           else {
               currentCity = city;
               getCurrentWeatherData(currentCity);
           }
            getCurrentWeatherData(city);
        });
        btnChangeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = edtSearch.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("City", city);
                startActivity(intent);
            }
        });
    }

    public void getCurrentWeatherData(String data) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+data+"&units=metric&appid=88967936556921d3b145758f422da297";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response ->
        {
            try {
                JSONObject jsonObject = new JSONObject(response);

                String day = jsonObject.getString("dt");
                String location = jsonObject.getString("name");
                tvCity.setText("City: "+location);
                long l = Long.valueOf(day);
                Date date = new Date(l*1000L);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-mm-dd HH:mm");
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
                Double a = Double.valueOf(temperature);
                String Temperature = String.valueOf(a.intValue());
                tvTemperature.setText(Temperature+" C");
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