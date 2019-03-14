package pnrs.rtrk.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    Button button1;
    EditText grad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.prikazi);
        button1.setOnClickListener(this);
        grad = findViewById(R.id.unos_grada);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.prikazi:
                Intent prelaz = new Intent(this, DetailsActivity.class);
                prelaz.putExtra("grad", grad.getText());
                startActivity(prelaz);
                break;
            default:
                break;
        }
    }
}
