package pnrs.rtrk.myapplication;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import java.util.Calendar;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button tempButton, sunButton, windButton;
    private LinearLayout tempLayout, sunLayout, windLayout;

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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.temperatureButton:
                sunLayout.setVisibility(View.INVISIBLE);
                tempLayout.setVisibility(View.VISIBLE);
                windLayout.setVisibility(View.INVISIBLE);
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
        }
    }

    protected String dayInSerbian(){
        Calendar calendar = Calendar.getInstance();
        Locale locale = new Locale.Builder().setLanguage("sr").setRegion("RS").setScript("Latn").build();
        String s = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);

        return s;
    }
}
