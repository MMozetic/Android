package pnrs.rtrk.myapplication;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import pnrs.rtrk.MyApplication;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button tempButton, sunButton, windButton;
    private LinearLayout tempLayout, sunLayout, windLayout;
    private String temp1,temp2,temp3,sun1,sun2,wind1,wind2;
    private TextView tmp1,tmp2,tmp3,sunRise,sunSet,windSpeed,windDir, updateText;
    private ImageView image;
    private RadioButton updateBtn;

    public static String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
    public static String KEY = "&APPID=8a8b70915fd021fff2707ceaef3dceb1&units=metric";
    public String GET_INFO;
    private HTTPHelper httpHelper;
    private Spinner format;
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView town = findViewById(R.id.town);
        Bundle bundle = getIntent().getExtras();

        TextView day = findViewById(R.id.day);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("dd/MMM/yyyy");

        //day.setText(getString(R.string.dayText) + " " + dayInSerbian());
        day.setText(getString(R.string.dateText) + " " + date.format(c.getTime()));

        town.setText(getString(R.string.locationText) + " " + bundle.get("town").toString());

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

        image = findViewById(R.id.sunImage);


        ContentResolver resolver = getContentResolver();

        ContentValues values = new ContentValues();
        values.put(WeatherDbHelper.COLUMN_DATE,"10/05/2019");
        values.put(WeatherDbHelper.COLUMN_NAME,bundle.get("town").toString());
        values.put(WeatherDbHelper.COLUMN_TEMPERATURE,10);
        values.put(WeatherDbHelper.COLUMN_PREASSURE,1000);
        values.put(WeatherDbHelper.COLUMN_HUMIDITY,53);
        values.put(WeatherDbHelper.COLUMN_SUNRISE,"05:00");
        values.put(WeatherDbHelper.COLUMN_SUNSET,"08:00");
        values.put(WeatherDbHelper.COLUMN_WIND_SPEED,4.3);
        values.put(WeatherDbHelper.COLUMN_WIND_DIRECTION,"N");
        resolver.insert(WeatherProvider.CONTENT_URI,values);


        Cursor cursor = resolver.query(WeatherProvider.CONTENT_URI,null,"Name=?",new String[]{bundle.get("town").toString()},null);

        counter = 0;

        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            counter++;
        }

        //day.setText(String.valueOf(counter));
        if(counter == 0){
            Toast.makeText(this, "Grad nije u bazi!",Toast.LENGTH_SHORT).show();
        }else{
            getHTTPData();
        }

        cursor.close();

        updateText = findViewById(R.id.updateText);
        updateBtn = findViewById(R.id.updateRadioBtn);
        updateBtn.setOnClickListener(this);
        updateBtn.setChecked(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.temperatureButton:
                sunLayout.setVisibility(View.INVISIBLE);
                tempLayout.setVisibility(View.VISIBLE);
                windLayout.setVisibility(View.INVISIBLE);

                format.setSelection(0);

                break;

            case R.id.sunButton:
                sunLayout.setVisibility(View.VISIBLE);
                tempLayout.setVisibility(View.INVISIBLE);
                windLayout.setVisibility(View.INVISIBLE);

                break;

            case R.id.windButton:
                sunLayout.setVisibility(View.INVISIBLE);
                tempLayout.setVisibility(View.INVISIBLE);
                windLayout.setVisibility(View.VISIBLE);

                break;

            case R.id.updateRadioBtn:
                getHTTPData();
                updateText.setVisibility(View.INVISIBLE);
                updateBtn.setVisibility(View.INVISIBLE);
                updateBtn.setChecked(false);

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

    protected void getHTTPData(){
        new Thread(new Runnable() {
            public void run() {
                try{
                    JSONObject jsonObject = httpHelper.getJSONObjectFromURL(GET_INFO);

                    JSONObject temperature = jsonObject.getJSONObject("main");
                    final String temp = temperature.get("temp").toString();
                    temp1 = getString(R.string.tempJson)+ " "  + temperature.get("temp").toString();
                    temp2 = getString(R.string.preassureJson)+ " "  + temperature.get("pressure").toString() + " mbar";
                    temp3 = getString(R.string.humidityJson)+ " "  + temperature.get("humidity").toString() + "%";

                    String icon = "";
                    JSONArray jsonarray = jsonObject.getJSONArray("weather");
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        icon = jsonobject.getString("icon");
                    }

                    final String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";

                    JSONObject sun = jsonObject.getJSONObject("sys");
                    long s1 = Long.valueOf(sun.get("sunrise").toString())*1000;
                    long s2 = Long.valueOf(sun.get("sunset").toString())*1000;
                    Date d1 = new Date(s1);
                    Date d2 = new Date(s2);
                    Locale locale = new Locale.Builder().setLanguage("sr").setRegion("RS").setScript("Latn").build();
                    sun1 = getString(R.string.sun1Json)+ " "  + new SimpleDateFormat("hh:mma ", locale).format(d1);
                    sun2 = getString(R.string.sun2Json)+ " "  + new SimpleDateFormat("hh:mma ",locale).format(d2);

                    JSONObject wind = jsonObject.getJSONObject("wind");
                    wind1 = getString(R.string.wind1Json)+ " "  + wind.get("speed").toString() + " m/s";
                    wind2 = getString(R.string.wind2Json)+ " "  + windConverter(wind.getDouble("deg"));

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tmp1.setText(temp1);
                            tmp2.setText(temp2);
                            tmp3.setText(temp3);
                            sunRise.setText(sun1);
                            sunSet.setText(sun2);
                            windSpeed.setText(wind1);
                            windDir.setText(wind2);
                            Picasso.with(MyApplication.getAppContext()).load(iconUrl).into(image);
                            format.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    switch (parent.getItemAtPosition(position).toString()){
                                        case "°C":
                                            tmp1.setText(temp1);
                                            break;
                                        default:
                                            double temperature = Double.parseDouble(temp)*9/5 + 32;
                                            tmp1.setText(getString(R.string.tempJson) + Double.toString(temperature));
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
    }
}
