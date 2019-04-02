package pnrs.rtrk.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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

        adapter = new CustomAdapter(this);
        adapter.addElement(new ListElement(getString(R.string.Beograd)));
        adapter.addElement(new ListElement(getString(R.string.Pariz)));
        adapter.addElement(new ListElement(getString(R.string.Barselona)));

        list.setAdapter(adapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.removeElement(position);
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cityInList:
                if(!town.getText().toString().isEmpty()){
                    adapter.addElement(new ListElement(town.getText().toString()));
                }else{
                    town.setHint("Unesite grad!");
                }
                break;
            default:
                break;
        }
    }
}
