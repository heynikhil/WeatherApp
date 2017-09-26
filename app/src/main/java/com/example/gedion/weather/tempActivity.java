package com.example.gedion.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class tempActivity extends AppCompatActivity {

    TextView maxtempView;
    TextView mintempView;
    ImageView weatherImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        maxtempView = (TextView) findViewById(R.id.degView);
        mintempView = (TextView) findViewById(R.id.speedView);
        weatherImageView = (ImageView) findViewById(R.id.weatherImageView);


        String url = "http://api.openweathermap.org/data/2.5/weather?q=surat,in&appid=eab90ed14f553d1ca4708e25605f11b1&units=Imperial";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseObject) {
                        Log.v("WEATHER", "Response: " + responseObject.toString());
                        try {
                            JSONObject mainJSONObject = responseObject.getJSONObject("main");
                            JSONArray weatherArray = responseObject.getJSONArray("weather");
                            JSONObject firstWeatherObject = weatherArray.getJSONObject(0);
                            String weatherDescription = firstWeatherObject.getString("description");
                            String temp_max = Integer.toString((int) Math.round(mainJSONObject.getDouble("temp_max")));
                            String temp_min = Integer.toString((int) Math.round(mainJSONObject.getDouble("temp_min")));

                            maxtempView.setText(temp_min);
                            mintempView.setText(temp_max);
                            int iconResourceId = getResources().getIdentifier("icon_" + weatherDescription.replace(" ", ""), "drawable", getPackageName());
                            weatherImageView.setImageResource(iconResourceId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                    }
                });

        // Access the RequestQueue through your singleton class.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);


    }


}