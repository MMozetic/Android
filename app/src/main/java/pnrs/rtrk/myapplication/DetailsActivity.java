package pnrs.rtrk.myapplication;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import java.util.Calendar;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{

    Button temp, izlazak_i_zalazak, vetar;
    LinearLayout temp_layout;
    TextView izlazak_zalazak, podaci_vetar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView grad = findViewById(R.id.grad);
        Bundle bundle = getIntent().getExtras();

        String d = dayInSerbian();

        TextView dan = findViewById(R.id.dan);
        dan.setText(d);
        grad.setText(bundle.get("grad").toString());

        temp = findViewById(R.id.temperatura);
        temp.setOnClickListener(this);
        temp_layout = findViewById(R.id.temp_layout);
        temp_layout.setVisibility(View.INVISIBLE);

        izlazak_i_zalazak = findViewById(R.id.izlazak_i_zalazak);
        izlazak_i_zalazak.setOnClickListener(this);
        izlazak_zalazak = findViewById(R.id.izlazak_zalazak);
        izlazak_zalazak.setVisibility(View.INVISIBLE);

        vetar = findViewById(R.id.vetar);
        vetar.setOnClickListener(this);
        podaci_vetar = findViewById(R.id.podaci_vetar);
        podaci_vetar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.temperatura:
                izlazak_zalazak.setVisibility(View.INVISIBLE);
                temp_layout.setVisibility(View.VISIBLE);
                podaci_vetar.setVisibility(View.INVISIBLE);
                break;

            case R.id.izlazak_i_zalazak:
                izlazak_zalazak.setVisibility(View.VISIBLE);
                temp_layout.setVisibility(View.INVISIBLE);
                podaci_vetar.setVisibility(View.INVISIBLE);
                break;

            case R.id.vetar:
                izlazak_zalazak.setVisibility(View.INVISIBLE);
                temp_layout.setVisibility(View.INVISIBLE);
                podaci_vetar.setVisibility(View.VISIBLE);
                break;
        }
    }

    protected String dayInSerbian(){
        String dan;
        Calendar calendar = Calendar.getInstance();
        String s = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        switch(s){
            case "Monday":
                dan = "Ponedeljak";
                break;
            case "Tuesday":
                dan = "Utorak";
                break;
            case "Wednesday":
                dan = "Sreda";
                break;
            case "Thursday":
                dan = "Četvrtak";
                break;
            case "Friday":
                dan = "Petak";
                break;
            case "Saturday":
                dan = "Subota";
                break;
            default:
                dan = "Nedelja";
                break;
        }
        return dan;
    }
}
