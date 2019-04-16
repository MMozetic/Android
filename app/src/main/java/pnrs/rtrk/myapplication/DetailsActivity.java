package pnrs.rtrk.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button tempButton, sunButton, windButton;
    private LinearLayout tempLayout, sunLayout, windLayout;
    private String temp1,temp2,temp3,sun1,sun2,wind1,wind2;
    private TextView tmp1,tmp2,tmp3,sunRise,sunSet,windSpeed,windDir;

    public static String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static String KEY = "&APPID=8a8b70915fd021fff2707ceaef3dceb1&units=metric";
    public String GET_INFO;
    private HTTPHelper httpHelper;
    private Spinner format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView town = findViewById(R.id.town);
        Bundle bundle = getIntent().getExtras();

        TextView day = findViewById(R.id.day);
        day.setText(dayInSerbian());
        town.setText(bundle.get("town").toString());

        tempButton = findViewById(R.id.temperatureButton);
        tempButton.setOnClickListener(this);
        tempLayout = findViewById(R.id.tempData);
        tempLayout.setVisibility(View.INVISIBLE);

        sunButton = findViewById(R.id.sunButton);
        sunButton.setOnClickListener(this);
        sunLayout = findViewById(R.id.sunData);
        sunLayout.setVisibility(View.INVISIBLE);

        windButton = findViewById(R.id.windButton);
        windButton.setOnClickListener(this);
        windLayout = findViewById(R.id.windData);
        windLayout.setVisibility(View.INVISIBLE);

        httpHelper = new HTTPHelper();
        GET_INFO = BASE_URL + bundle.get("town").toString() + KEY;
        Log.d("URL", GET_INFO);
        tmp1 = findViewById(R.id.temp1);
        tmp2 = findViewById(R.id.temp2);
        tmp3 = findViewById(R.id.temp3);
        sunRise = findViewById(R.id.sunRise);
        sunSet = findViewById(R.id.sunSet);
        windDir = findViewById(R.id.windDir);
        windSpeed = findViewById(R.id.windSpeed);

        format = findViewById(R.id.list);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.formats,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        format.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.temperatureButton:
                sunLayout.setVisibility(View.INVISIBLE);
                tempLayout.setVisibility(View.VISIBLE);
                windLayout.setVisibility(View.INVISIBLE);

                new Thread(new Runnable() {
                    public void run() {
                        try{
                            JSONObject jsonObject = httpHelper.getJSONObjectFromURL(GET_INFO);

                            JSONObject temperature = jsonObject.getJSONObject("main");
                            final String temp = temperature.get("temp").toString();
                            temp1 = "Temperatura: " + temperature.get("temp").toString();
                            temp2 = "Pritisak: " + temperature.get("pressure").toString() + " mbar";
                            temp3 = "Vlažnost vazduha: " + temperature.get("humidity").toString() + "%";

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tmp1.setText(temp1);
                                    tmp2.setText(temp2);
                                    tmp3.setText(temp3);

                                    format.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            switch (parent.getItemAtPosition(position).toString()){
                                                case "°C":
                                                    tmp1.setText(temp1);
                                                    break;
                                                default:
                                                    double temperature = Double.parseDouble(temp)*9/5 + 32;
                                                    tmp1.setText("Temperatura: " + Double.toString(temperature));
                                                    break;
                                            }
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {
                                            tmp1.setText(temp1);
                                        }
                                    });
                                }
                            });
                        }catch (JSONException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;

            case R.id.sunButton:
                sunLayout.setVisibility(View.VISIBLE);
                tempLayout.setVisibility(View.INVISIBLE);
                windLayout.setVisibility(View.INVISIBLE);

                new Thread(new Runnable() {
                    public void run() {
                        try{
                            JSONObject jsonObject = httpHelper.getJSONObjectFromURL(GET_INFO);

                            JSONObject sun = jsonObject.getJSONObject("sys");
                            long s1 = Long.valueOf(sun.get("sunrise").toString())*1000;
                            long s2 = Long.valueOf(sun.get("sunset").toString())*1000;
                            Date d1 = new java.util.Date(s1);
                            Date d2 = new java.util.Date(s2);
                            Locale locale = new Locale.Builder().setLanguage("sr").setRegion("RS").setScript("Latn").build();
                            sun1 = "Izlazak sunca: " + new SimpleDateFormat("hh:mma ", locale).format(d1);
                            sun2 = "Zalazak sunca: " + new SimpleDateFormat("hh:mma ",locale).format(d2);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    sunRise.setText(sun1);
                                    sunSet.setText(sun2);
                                }
                            });
                        }catch (JSONException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;

            case R.id.windButton:
                sunLayout.setVisibility(View.INVISIBLE);
                tempLayout.setVisibility(View.INVISIBLE);
                windLayout.setVisibility(View.VISIBLE);

                new Thread(new Runnable() {
                    public void run() {
                        try{
                            JSONObject jsonObject = httpHelper.getJSONObjectFromURL(GET_INFO);

                            JSONObject wind = jsonObject.getJSONObject("wind");
                            wind1 = "Brzina vetra: " + wind.get("speed").toString() + " m/s";
                            wind2 = "Pravac: " + windConverter(wind.getDouble("deg"));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    windSpeed.setText(wind1);
                                    windDir.setText(wind2);
                                }
                            });
                        }catch (JSONException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }

    protected String dayInSerbian(){
        Calendar calendar = Calendar.getInstance();
        Locale locale = new Locale.Builder().setLanguage("sr").setRegion("RS").setScript("Latn").build();
        String s = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);

        return s;
    }

    protected String windConverter(double degree){
        if(degree < 22.5){
            return "N";
        }
        if(degree<67.5){
            return "N-E";
        }
        if(degree<122.5){
            return "E";
        }
        if(degree<157.5){
            return "S-E";
        }
        if(degree<202.5){
            return "S";
        }
        if(degree<247.5){
            return "S-W";
        }
        if(degree<292.5){
            return "W";
        }
        if(degree<337.5){
            return "N-W";
        }
        return "N";
    }
}
