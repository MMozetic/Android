package pnrs.rtrk.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StatisticsActivity extends AppCompatActivity {

    private String city, day, date;
    private TextView cityView, imageEdit;

    private ImageView snowImage, sunImage;

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

    }
}
