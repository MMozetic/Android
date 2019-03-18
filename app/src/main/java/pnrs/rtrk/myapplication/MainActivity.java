package pnrs.rtrk.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    private Button showDetails;
    private EditText town;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showDetails = findViewById(R.id.showDetails);
        showDetails.setOnClickListener(this);
        town = findViewById(R.id.enterTown);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.showDetails:
                Intent switchActivity = new Intent(this, DetailsActivity.class);
                switchActivity.putExtra("town", town.getText());
                startActivity(switchActivity);
                break;
            default:
                break;
        }
    }
}
