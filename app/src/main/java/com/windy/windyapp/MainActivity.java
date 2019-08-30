package com.windy.windyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.windy.windyapp.ClassWindy.ClassWindy;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    String url;
    String city;
    String weatherType;
    String weatherDescription;
    String weatherIcon;
    Integer mainTemp;
    Double windSpeed;
    String iconUrl;

    TextView txtCity;
    TextView txtWeatherType;
    TextView txtWeatherDescription;
    ImageView imgWeatherIcon;
    TextView txtMainTemp;
    TextView txtWindSpeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtCity = findViewById(R.id.txtcity);
        txtWeatherType = findViewById(R.id.txtdes);
        txtWeatherDescription = findViewById(R.id.txtdname);
        txtMainTemp = findViewById(R.id.txtDec);
        txtWindSpeed = findViewById(R.id.txtWindsp);


        url = "https://api.openweathermap.org/data/2.5/weather?q=Rasht&units=metric&APPID=ba2d6ad9c4fbf609c806ea31c2327e60";

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(url, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                Gson gson = new Gson();

                ClassWindy classWindy = gson.fromJson(response.toString(), ClassWindy.class);



                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    String weatherData = jsonObject.getString("weather");
                    JSONArray jsonArray = new JSONArray(weatherData);

                    weatherType = "";
                    weatherDescription = "";
                    weatherIcon = null;
                    iconUrl = null;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject weatherPart = jsonArray.getJSONObject(i);
                        weatherType = weatherPart.getString("main");
                        weatherDescription = weatherPart.getString("description");
                        weatherIcon = weatherPart.getString("icon");
                        String wIcon = weatherIcon;
                    }

                    txtWeatherType.setText(weatherType);
                    txtWeatherDescription.setText(weatherDescription);
                    iconUrl = "http://openweathermap.org/img/wn/" + weatherIcon + ".png";
                    Picasso.get().load(Uri.parse(iconUrl)).into(imgWeatherIcon);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                city = classWindy.getName();
                txtCity.setText(city);


                mainTemp = classWindy.getMain().getTemp();
                txtMainTemp.setText("Temperature : " + mainTemp.toString() + "°C");

                windSpeed = classWindy.getWind().getSpeed();
                txtWindSpeed.setText("WindSpeed : " + windSpeed.toString() + "m/s");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
        TextView txtsearch=findViewById(R.id.txtsearch);
        txtsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,sp.class);
                startActivity(intent);
            }
        });


    }
}