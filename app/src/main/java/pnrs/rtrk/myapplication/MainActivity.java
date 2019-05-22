package pnrs.rtrk.myapplication;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    private Button showDetails,stopService;
    private EditText town;
    private ListView list;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showDetails = findViewById(R.id.cityInList);
        showDetails.setOnClickListener(this);

        list = findViewById(R.id.listElement);

        town = findViewById(R.id.enterTown);
        town.setHint(getString(R.string.hintText1));

        adapter = new CustomAdapter(this);
        adapter.addElement(new ListElement(getString(R.string.Beograd)));
        adapter.addElement(new ListElement(getString(R.string.Pariz)));
        adapter.addElement(new ListElement(getString(R.string.Barselona)));
        adapter.addElement(new ListElement(getString(R.string.London)));

        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.removeElement(position);
                return true;
            }
        });
        initDataBase();

        stopService = findViewById(R.id.stopService);
        stopService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cityInList:
                ListElement element = new ListElement(town.getText().toString());
                if(!town.getText().toString().isEmpty()){
                    if(adapter.containsElement(element)){
                        Toast.makeText(this, getString(R.string.toastWarning1),Toast.LENGTH_SHORT).show();
                    }else {
                        adapter.addElement(element);
                        Toast.makeText(this, getString(R.string.toastSuccess1),Toast.LENGTH_SHORT).show();
                    }
                    town.setText("");
                }else{
                    Toast.makeText(this, getString(R.string.toastWarning2),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.stopService:
                Intent intent = new Intent(this, WeatherService.class);
                stopService(intent);
                break;
            default:
                break;
        }
    }

    protected boolean dataExist(Cursor cursor, String date){
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (date.equals(cursor.getString(cursor.getColumnIndex("Date")))){
                return true;
            }
        }
        return false;
    }

    protected void initDataBase(){

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(WeatherProvider.CONTENT_URI,null,"Name=?",new String[]{"Novi Sad"},"Date ASC");

        this.deleteDatabase("weather.db");

        if(!dataExist(cursor,"18/May/2019")){
            ContentValues values = new ContentValues();
            values.put(WeatherDbHelper.COLUMN_DATE,"18/May/2019");
            values.put(WeatherDbHelper.COLUMN_NAME,"Novi Sad");
            values.put(WeatherDbHelper.COLUMN_TEMPERATURE,40);
            values.put(WeatherDbHelper.COLUMN_PREASSURE,1000);
            values.put(WeatherDbHelper.COLUMN_HUMIDITY,53);
            values.put(WeatherDbHelper.COLUMN_SUNRISE,"05:00pre podne");
            values.put(WeatherDbHelper.COLUMN_SUNSET,"08:00popodne");
            values.put(WeatherDbHelper.COLUMN_WIND_SPEED,4.3);
            values.put(WeatherDbHelper.COLUMN_WIND_DIRECTION,"N");
            values.put(WeatherDbHelper.COLUMN_IMAGE_URL, "09d");
            values.put(WeatherDbHelper.COLUMN_DAY, "subota");
            resolver.insert(WeatherProvider.CONTENT_URI,values);
        }

        if(!dataExist(cursor,"19/May/2019")){
            ContentValues values = new ContentValues();
            values.put(WeatherDbHelper.COLUMN_DATE,"19/May/2019");
            values.put(WeatherDbHelper.COLUMN_NAME,"Novi Sad");
            values.put(WeatherDbHelper.COLUMN_TEMPERATURE,12.3);
            values.put(WeatherDbHelper.COLUMN_PREASSURE,1029);
            values.put(WeatherDbHelper.COLUMN_HUMIDITY,75);
            values.put(WeatherDbHelper.COLUMN_SUNRISE,"05:20pre podne");
            values.put(WeatherDbHelper.COLUMN_SUNSET,"08:12popodne");
            values.put(WeatherDbHelper.COLUMN_WIND_SPEED,3.1);
            values.put(WeatherDbHelper.COLUMN_WIND_DIRECTION,"S");
            values.put(WeatherDbHelper.COLUMN_IMAGE_URL, "09d");
            values.put(WeatherDbHelper.COLUMN_DAY, "nedelja");
            resolver.insert(WeatherProvider.CONTENT_URI,values);
        }

        if(!dataExist(cursor,"20/May/2019")){
            ContentValues values = new ContentValues();
            values.put(WeatherDbHelper.COLUMN_DATE,"20/May/2019");
            values.put(WeatherDbHelper.COLUMN_NAME,"Novi Sad");
            values.put(WeatherDbHelper.COLUMN_TEMPERATURE,15.5);
            values.put(WeatherDbHelper.COLUMN_PREASSURE,1033);
            values.put(WeatherDbHelper.COLUMN_HUMIDITY,65);
            values.put(WeatherDbHelper.COLUMN_SUNRISE,"05:15pre podne");
            values.put(WeatherDbHelper.COLUMN_SUNSET,"08:10popodne");
            values.put(WeatherDbHelper.COLUMN_WIND_SPEED,5.1);
            values.put(WeatherDbHelper.COLUMN_WIND_DIRECTION,"N-E");
            values.put(WeatherDbHelper.COLUMN_IMAGE_URL, "09d");
            values.put(WeatherDbHelper.COLUMN_DAY, "ponedeljak");
            resolver.insert(WeatherProvider.CONTENT_URI,values);
        }

        if(!dataExist(cursor,"21/May/2019")){
            ContentValues values = new ContentValues();
            values.put(WeatherDbHelper.COLUMN_DATE,"21/May/2019");
            values.put(WeatherDbHelper.COLUMN_NAME,"Novi Sad");
            values.put(WeatherDbHelper.COLUMN_TEMPERATURE,7.7);
            values.put(WeatherDbHelper.COLUMN_PREASSURE,1042);
            values.put(WeatherDbHelper.COLUMN_HUMIDITY,82);
            values.put(WeatherDbHelper.COLUMN_SUNRISE,"05:25pre podne");
            values.put(WeatherDbHelper.COLUMN_SUNSET,"07:53popodne");
            values.put(WeatherDbHelper.COLUMN_WIND_SPEED,2.0);
            values.put(WeatherDbHelper.COLUMN_WIND_DIRECTION,"W");
            values.put(WeatherDbHelper.COLUMN_IMAGE_URL, "09d");
            values.put(WeatherDbHelper.COLUMN_DAY, "utorak");
            resolver.insert(WeatherProvider.CONTENT_URI,values);
        }

        cursor.close();


        cursor = resolver.query(WeatherProvider.CONTENT_URI,null,"Name=?",new String[]{"Belgrade"},"Date ASC");

        if(!dataExist(cursor,"20/May/2019")){
            ContentValues values = new ContentValues();
            values.put(WeatherDbHelper.COLUMN_DATE,"20/May/2019");
            values.put(WeatherDbHelper.COLUMN_NAME,"Belgrade");
            values.put(WeatherDbHelper.COLUMN_TEMPERATURE,8.5);
            values.put(WeatherDbHelper.COLUMN_PREASSURE,1018);
            values.put(WeatherDbHelper.COLUMN_HUMIDITY,61);
            values.put(WeatherDbHelper.COLUMN_SUNRISE,"05:31pre podne");
            values.put(WeatherDbHelper.COLUMN_SUNSET,"08:15popodne");
            values.put(WeatherDbHelper.COLUMN_WIND_SPEED,6.3);
            values.put(WeatherDbHelper.COLUMN_WIND_DIRECTION,"N");
            values.put(WeatherDbHelper.COLUMN_IMAGE_URL, "10d");
            values.put(WeatherDbHelper.COLUMN_DAY, "ponedeljak");
            resolver.insert(WeatherProvider.CONTENT_URI,values);
        }

        if(!dataExist(cursor,"21/May/2019")){
            ContentValues values = new ContentValues();
            values.put(WeatherDbHelper.COLUMN_DATE,"21/May/2019");
            values.put(WeatherDbHelper.COLUMN_NAME,"Belgrade");
            values.put(WeatherDbHelper.COLUMN_TEMPERATURE,8.5);
            values.put(WeatherDbHelper.COLUMN_PREASSURE,1001);
            values.put(WeatherDbHelper.COLUMN_HUMIDITY,55);
            values.put(WeatherDbHelper.COLUMN_SUNRISE,"05:36pre podne");
            values.put(WeatherDbHelper.COLUMN_SUNSET,"07:48popodne");
            values.put(WeatherDbHelper.COLUMN_WIND_SPEED,4.3);
            values.put(WeatherDbHelper.COLUMN_WIND_DIRECTION,"E");
            values.put(WeatherDbHelper.COLUMN_IMAGE_URL, "10d");
            values.put(WeatherDbHelper.COLUMN_DAY, "utorak");
            resolver.insert(WeatherProvider.CONTENT_URI,values);
        }

        cursor.close();

    }
}
