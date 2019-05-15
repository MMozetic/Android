package pnrs.rtrk.myapplication;

import android.content.ContentResolver;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StatisticsActivity extends AppCompatActivity {

    private String city, day, date;
    private TextView cityView, imageEdit, minDan, minTemp, maxDan, maxTemp;

    private ImageView snowImage, sunImage;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Bundle bundle = getIntent().getExtras();
        city = bundle.get("town").toString();
        day = bundle.get("day").toString();
        date =  bundle.get("date").toString();

        cityView = findViewById(R.id.cityText);

        imageEdit = findViewById(R.id.imageEdit);
        snowImage = findViewById(R.id.snowImage);
        sunImage = findViewById(R.id.sunImage);

        snowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageEdit.setText("Hladnooo");
            }
        });
        sunImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageEdit.setText("Toplooo");
            }
        });
        cityView.setText(city);

        minDan = findViewById(R.id.minDan);
        minTemp = findViewById(R.id.minTemp);
        maxDan = findViewById(R.id.maxDan);
        maxTemp = findViewById(R.id.maxTemp);

        ContentResolver resolver = getContentResolver();
        cursor = resolver.query(WeatherProvider.CONTENT_URI, null, "Name=?",new String[]{city},"Temperature ASC");
        cursor.moveToFirst();

        minDan.setText(cursor.getString(cursor.getColumnIndex("Date")));
        minTemp.setText(Double.toString(cursor.getDouble(cursor.getColumnIndex("Temperature"))));

        cursor.moveToLast();
        maxDan.setText(cursor.getString(cursor.getColumnIndex("Date")));
        maxTemp.setText(Double.toString(cursor.getDouble(cursor.getColumnIndex("Temperature"))));

        cursor.close();
    }
}
