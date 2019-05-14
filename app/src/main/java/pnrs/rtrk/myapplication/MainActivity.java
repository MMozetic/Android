package pnrs.rtrk.myapplication;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    private Button showDetails;
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

        ContentResolver resolver = getContentResolver();

        ContentValues values = new ContentValues();
        values.put(WeatherDbHelper.COLUMN_DATE,"10/May/2019");
        values.put(WeatherDbHelper.COLUMN_NAME,"Novi Sad");
        values.put(WeatherDbHelper.COLUMN_TEMPERATURE,10);
        values.put(WeatherDbHelper.COLUMN_PREASSURE,1000);
        values.put(WeatherDbHelper.COLUMN_HUMIDITY,53);
        values.put(WeatherDbHelper.COLUMN_SUNRISE,"05:00pre podne");
        values.put(WeatherDbHelper.COLUMN_SUNSET,"08:00popodne");
        values.put(WeatherDbHelper.COLUMN_WIND_SPEED,4.3);
        values.put(WeatherDbHelper.COLUMN_WIND_DIRECTION,"N");
        values.put(WeatherDbHelper.COLUMN_IMAGE_URL, "09d");
        resolver.insert(WeatherProvider.CONTENT_URI,values);

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
            default:
                break;
        }
    }
}
