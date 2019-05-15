package pnrs.rtrk.myapplication;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Typeface;
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
    private TextView cityView, imageEdit, minDan, minTemp, maxDan, maxTemp, ponedeljak,utorak,sreda,četvrtak,petak,subota,nedelja;
    private TextView ponedeljakTemp,ponedeljakPritisak,ponedeljakVlaznost,utorakTemp,utorakPritisak,utorakVlaznost,sredaPritisak,sredaVlaznost,sredaTemp;
    private TextView četvrtakTemp,četvrtakPritisak,četvrtakVlaznost,petakTemp,petakPritisak,petakVlaznost,subotaTemp,subotaPritisak,subotaVlaznost,nedeljaTemp,nedeljaPritisak,nedeljaVlaznost;

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
                ContentResolver resolver = getContentResolver();
                Cursor cursor = resolver.query(WeatherProvider.CONTENT_URI, null, "Name=?",new String[]{city},"Date ASC");

                String edit = "";

                if(cursor.getCount()<2){
                    cursor.moveToFirst();
                    if(cursor.getDouble(cursor.getColumnIndex("Temperature"))<=10.0){
                        edit += cursor.getString(cursor.getColumnIndex("Day")) + " - ";
                        edit += Double.toString(cursor.getDouble(cursor.getColumnIndex("Temperature"))) + "\n";
                    }
                }else{
                    for(cursor.moveToLast();!cursor.isBeforeFirst();cursor.moveToPrevious()){
                        if(cursor.getString(cursor.getColumnIndex("Day")).equals("nedelja")){
                            break;
                        }else if(cursor.getDouble(cursor.getColumnIndex("Temperature"))<=10.0){
                                edit += cursor.getString(cursor.getColumnIndex("Day")) + " - ";
                                edit += Double.toString(cursor.getDouble(cursor.getColumnIndex("Temperature"))) + "\n";
                            }

                    }
                }

                cursor.close();
                imageEdit.setText(edit);
            }
        });
        sunImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver resolver = getContentResolver();
                Cursor cursor = resolver.query(WeatherProvider.CONTENT_URI, null, "Name=?",new String[]{city},"Date ASC");

                String edit = "";

                if(cursor.getCount()<2){
                    cursor.moveToFirst();
                    if(cursor.getDouble(cursor.getColumnIndex("Temperature"))>=10.0){
                        edit += cursor.getString(cursor.getColumnIndex("Day")) + " - ";
                        edit += Double.toString(cursor.getDouble(cursor.getColumnIndex("Temperature"))) + "\n";
                    }
                }else{
                    for(cursor.moveToLast();!cursor.isBeforeFirst();cursor.moveToPrevious()){
                        if(cursor.getString(cursor.getColumnIndex("Day")).equals("nedelja")){
                            break;
                        }else if(cursor.getDouble(cursor.getColumnIndex("Temperature"))>=10.0){
                                edit += cursor.getString(cursor.getColumnIndex("Day")) + " - ";
                                edit += Double.toString(cursor.getDouble(cursor.getColumnIndex("Temperature"))) + "\n";
                            }
                    }
                }

                cursor.close();
                imageEdit.setText(edit);
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

        minDan.setText(cursor.getString(cursor.getColumnIndex("Day")));
        minTemp.setText(Double.toString(cursor.getDouble(cursor.getColumnIndex("Temperature"))));

        cursor.moveToLast();
        maxDan.setText(cursor.getString(cursor.getColumnIndex("Day")));
        maxTemp.setText(Double.toString(cursor.getDouble(cursor.getColumnIndex("Temperature"))));

        cursor.close();

        ponedeljak = findViewById(R.id.ponedeljak);
        ponedeljakTemp = findViewById(R.id.ponedeljakTemp);
        ponedeljakPritisak = findViewById(R.id.ponedeljakPritisak);
        ponedeljakVlaznost = findViewById(R.id.ponedeljakVlaznost);
        utorak = findViewById(R.id.utorak);
        utorakTemp = findViewById(R.id.utorakTemp);
        utorakPritisak = findViewById(R.id.utorakPritisak);
        utorakVlaznost = findViewById(R.id.utorakVlaznost);
        sreda = findViewById(R.id.sreda);
        sredaTemp = findViewById(R.id.sredaTemp);
        sredaPritisak = findViewById(R.id.sredaPritisak);
        sredaVlaznost = findViewById(R.id.sredaVlaznost);
        četvrtak = findViewById(R.id.četvrtak);
        četvrtakTemp = findViewById(R.id.četvrtakTemp);
        četvrtakPritisak = findViewById(R.id.četvrtakPritisak);
        četvrtakVlaznost = findViewById(R.id.četvrtakVlaznost);
        petak = findViewById(R.id.petak);
        petakTemp = findViewById(R.id.petakTemp);
        petakPritisak = findViewById(R.id.petakPritisak);
        petakVlaznost = findViewById(R.id.petakVlaznost);
        subota = findViewById(R.id.subota);
        subotaTemp = findViewById(R.id.subotaTemp);
        subotaPritisak = findViewById(R.id.subotaPritisak);
        subotaVlaznost = findViewById(R.id.subotaVlaznost);
        nedelja = findViewById(R.id.nedelja);
        nedeljaTemp = findViewById(R.id.nedeljaTemp);
        nedeljaPritisak = findViewById(R.id.nedeljaPritisak);
        nedeljaVlaznost = findViewById(R.id.nedeljaVlaznost);

        if(day.equals("ponedeljak")){
            ponedeljak.setTypeface(Typeface.DEFAULT_BOLD);
            ponedeljak.setTextSize(20);
            ponedeljakTemp.setTypeface(Typeface.DEFAULT_BOLD);
            ponedeljakTemp.setTextSize(20);
            ponedeljakPritisak.setTypeface(Typeface.DEFAULT_BOLD);
            ponedeljakPritisak.setTextSize(20);
            ponedeljakVlaznost.setTypeface(Typeface.DEFAULT_BOLD);
            ponedeljakVlaznost.setTextSize(20);
        }else if(day.equals("utorak")){
            utorak.setTypeface(Typeface.DEFAULT_BOLD);
            utorak.setTextSize(15);
            utorakTemp.setTypeface(Typeface.DEFAULT_BOLD);
            utorakTemp.setTextSize(15);
            utorakPritisak.setTypeface(Typeface.DEFAULT_BOLD);
            utorakPritisak.setTextSize(15);
            utorakVlaznost.setTypeface(Typeface.DEFAULT_BOLD);
            utorakVlaznost.setTextSize(15);
        }else if(day.equals("sreda")){
            sreda.setTypeface(Typeface.DEFAULT_BOLD);
            sreda.setTextSize(15);
            sredaTemp.setTypeface(Typeface.DEFAULT_BOLD);
            sredaTemp.setTextSize(15);
            sredaPritisak.setTypeface(Typeface.DEFAULT_BOLD);
            sredaPritisak.setTextSize(15);
            sredaVlaznost.setTypeface(Typeface.DEFAULT_BOLD);
            sredaVlaznost.setTextSize(15);
        }else if(day.equals("četvrtak")){
            četvrtak.setTypeface(Typeface.DEFAULT_BOLD);
            četvrtak.setTextSize(15);
            četvrtakTemp.setTypeface(Typeface.DEFAULT_BOLD);
            četvrtakTemp.setTextSize(15);
            četvrtakPritisak.setTypeface(Typeface.DEFAULT_BOLD);
            četvrtakVlaznost.setTextSize(15);
        }else if(day.equals("petak")){
            petak.setTypeface(Typeface.DEFAULT_BOLD);
            petak.setTextSize(15);
            petakTemp.setTypeface(Typeface.DEFAULT_BOLD);
            petakTemp.setTextSize(15);
            petakPritisak.setTypeface(Typeface.DEFAULT_BOLD);
            petakPritisak.setTextSize(15);
            petakVlaznost.setTypeface(Typeface.DEFAULT_BOLD);
            petakVlaznost.setTextSize(15);
        }else if(day.equals("subota")){
            subota.setTypeface(Typeface.DEFAULT_BOLD);
            subota.setTextSize(15);
            subotaTemp.setTypeface(Typeface.DEFAULT_BOLD);
            subotaTemp.setTextSize(15);
            subotaPritisak.setTypeface(Typeface.DEFAULT_BOLD);
            subotaPritisak.setTextSize(15);
            subotaVlaznost.setTypeface(Typeface.DEFAULT_BOLD);
            subotaVlaznost.setTextSize(15);
        } else {
            nedelja.setTypeface(Typeface.DEFAULT_BOLD);
            nedelja.setTextSize(15);
            nedeljaTemp.setTypeface(Typeface.DEFAULT_BOLD);
            nedeljaTemp.setTextSize(15);
            nedeljaPritisak.setTypeface(Typeface.DEFAULT_BOLD);
            nedeljaPritisak.setTextSize(15);
            nedeljaVlaznost.setTypeface(Typeface.DEFAULT_BOLD);
            nedeljaVlaznost.setTextSize(15);
        }

        resolver = getContentResolver();
        cursor = resolver.query(WeatherProvider.CONTENT_URI, null, "Name=?",new String[]{city},"Date ASC");

        if(cursor.getCount()<2){
            cursor.moveToFirst();
            printData(cursor);
        }else{
            for(cursor.moveToLast();!cursor.isBeforeFirst();cursor.moveToPrevious()){
                if(cursor.getString(cursor.getColumnIndex("Day")).equals("nedelja")){
                    break;
                }else{
                    printData(cursor);
                }
            }
        }

        cursor.close();

    }

    private void printData(Cursor cursor){
        if(cursor.getString(cursor.getColumnIndex("Day")).equals("ponedeljak")){
            ponedeljakTemp.setText(Double.toString(cursor.getDouble(cursor.getColumnIndex("Temperature"))));
            ponedeljakPritisak.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("Preassure"))));
            ponedeljakVlaznost.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("Humidity"))));
        }else if(cursor.getString(cursor.getColumnIndex("Day")).equals("utorak")){
            utorakTemp.setText(Double.toString(cursor.getDouble(cursor.getColumnIndex("Temperature"))));
            utorakPritisak.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("Preassure"))));
            utorakVlaznost.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("Humidity"))));
        }else if(cursor.getString(cursor.getColumnIndex("Day")).equals("sreda")){
            sredaTemp.setText(Double.toString(cursor.getDouble(cursor.getColumnIndex("Temperature"))));
            sredaPritisak.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("Preassure"))));
            sredaVlaznost.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("Humidity"))));
        }else if(cursor.getString(cursor.getColumnIndex("Day")).equals("četvrtak")){
            četvrtakTemp.setText(Double.toString(cursor.getDouble(cursor.getColumnIndex("Temperature"))));
            četvrtakPritisak.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("Preassure"))));
            četvrtakVlaznost.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("Humidity"))));
        }else if(cursor.getString(cursor.getColumnIndex("Day")).equals("petak")){
            petakTemp.setText(Double.toString(cursor.getDouble(cursor.getColumnIndex("Temperature"))));
            petakPritisak.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("Preassure"))));
            petakVlaznost.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("Humidity"))));
        }else if(cursor.getString(cursor.getColumnIndex("Day")).equals("subota")){
            subotaTemp.setText(Double.toString(cursor.getDouble(cursor.getColumnIndex("Temperature"))));
            subotaPritisak.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("Preassure"))));
            subotaVlaznost.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("Humidity"))));
        }else{
            nedeljaTemp.setText(Double.toString(cursor.getDouble(cursor.getColumnIndex("Temperature"))));
            nedeljaPritisak.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("Preassure"))));
            nedeljaVlaznost.setText(Integer.toString(cursor.getInt(cursor.getColumnIndex("Humidity"))));
        }
    }

}
