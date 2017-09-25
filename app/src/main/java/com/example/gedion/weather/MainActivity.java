package com.example.gedion.weather;

        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.lang.*;


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



    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempTextView = (TextView) findViewById(R.id.tempTextView);
        dateTextView = (TextView) findViewById(R.id.dateTextView);
        weatherTextView = (TextView) findViewById(R.id.weatherDesctextView);
        cityTextView = (TextView) findViewById(R.id.cityTextView);


        weatherImageView = (ImageView) findViewById(R.id.weatherImageView);

        dateTextView.setText(getCurrentDate());

        String url = "http://api.openweathermap.org/data/2.5/weather?q=rajkot,in&appid=eab90ed14f553d1ca4708e25605f11b1&units=Imperial";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseObject) {
                        //tempTextView.setText("Response: " + response.toString());
                        Log.v("WEATHER", "Response: " + responseObject.toString());

                        try
                        {

                            JSONObject mainJSONObject = responseObject.getJSONObject("main");


                            JSONArray weatherArray = responseObject.getJSONArray("weather");

                            JSONObject firstWeatherObject = weatherArray.getJSONObject(0);


                            String temp = Integer.toString((int) Math.round(mainJSONObject.getDouble("temp")));
                            String weatherDescription = firstWeatherObject.getString("description");
                           // String iconName=firstWeatherObject.getString("icon");
                            String city = responseObject.getString("name");
                            //Log.v("TempString", "Response: " + temp);
                            //Log.v("WeatherString", "Response: " + weatherDescription);
                            //Log.v("CityString", "Response: " + city);

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
        }



    private String getCurrentDate ()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd");
        String formattedDate = dateFormat.format(calendar.getTime());

        return formattedDate;
    }
}