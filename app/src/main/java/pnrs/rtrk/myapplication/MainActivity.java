package pnrs.rtrk.myapplication;

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
