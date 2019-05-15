package pnrs.rtrk.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StatisticsActivity extends AppCompatActivity {

    private String city, day, date;
    private TextView t1,t2,t3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        Bundle bundle = getIntent().getExtras();
        city = bundle.get("town").toString();
        day = bundle.get("day").toString();
        date =  bundle.get("date").toString();

        t1 = findViewById(R.id.cityText);
        t2 = findViewById(R.id.dayText);
        t3 = findViewById(R.id.dateText);

        t1.setText(city);
        t2.setText(day);
        t3.setText(date);
    }
}
