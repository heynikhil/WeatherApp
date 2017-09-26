package com.example.gedion.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{

    TextView tempTextView;
    TextView dateTextView;
    TextView weatherTextView;
    TextView cityTextView;
    ImageView weatherImageView;
    Button pressureButton;
    Button humidityButton;
    Button tempButton;
    Button levelButton;
    Button WindButton;





    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempTextView = (TextView) findViewById(R.id.tempTextView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        weatherTextView = (TextView) findViewById(R.id.weatherDesctextView);
        cityTextView = (TextView) findViewById(R.id.cityTextView);
        pressureButton = (Button) findViewById(R.id.pressureButton);
        humidityButton = (Button) findViewById(R.id.humidityButton);
        tempButton = (Button) findViewById(R.id.tempButton);
        levelButton = (Button) findViewById(R.id.levelButton);
        WindButton = (Button) findViewById(R.id.WindButton);





        weatherImageView = (ImageView) findViewById(R.id.weatherImageView);

        dateTextView.setText(getCurrentDate());

        String url = "http://api.openweathermap.org/data/2.5/weather?q=surat,in&appid=eab90ed14f553d1ca4708e25605f11b1&units=Imperial";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseObject) {
                        Log.v("WEATHER", "Response: " + responseObject.toString());

                        try
                        {

                            JSONObject mainJSONObject = responseObject.getJSONObject("main");


                            JSONArray weatherArray = responseObject.getJSONArray("weather");

                            JSONObject firstWeatherObject = weatherArray.getJSONObject(0);


                            String temp = Integer.toString((int) Math.round(mainJSONObject.getDouble("temp")));
                            String weatherDescription = firstWeatherObject.getString("description");
                            String city = responseObject.getString("name");

                            tempTextView.setText(temp);
                            weatherTextView.setText(weatherDescription);
                            cityTextView.setText(city);

                            int iconResourceId = getResources().getIdentifier("icon_" + weatherDescription.replace(" ", ""), "drawable", getPackageName());
                            weatherImageView.setImageResource(iconResourceId);
                        }
                        catch (JSONException e)
                        {
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

        pressureButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        PressureActivity.class);
                startActivity(myIntent);
            }
        });
        humidityButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        humidityActivity.class);
                startActivity(myIntent);
            }
        });
        tempButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        tempActivity.class);
                startActivity(myIntent);
            }
        });
        levelButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        levelActivity.class);
                startActivity(myIntent);
            }
        });
       WindButton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Start NewActivity.class
                Intent myIntent = new Intent(MainActivity.this,
                        windActivity.class);
                startActivity(myIntent);
            }
        });
        }



    private String getCurrentDate ()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd");
        String formattedDate = dateFormat.format(calendar.getTime());

        return formattedDate;
    }
}