package com.example.pr_idi.mydatabaseexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.EventListener;

public class InsertFilmActivity extends AppCompatActivity {

    private EditText inputTitle;
    private EditText inputCountry;
    private EditText inputDirector;
    private EditText inputProtagonist;
    private EditText inputYear;
    private EditText inputRate;
    private FilmData filmData;
    private Button insertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_film);
        initializeViews();
        filmData = new FilmData(this);
        filmData.open();

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filmData.createFilm(inputTitle.getText().toString(),inputDirector.getText().toString(),
                        inputCountry.getText().toString(),inputProtagonist.getText().toString(),
                        Integer.parseInt(inputYear.getText().toString()),
                        Integer.parseInt(inputRate.getText().toString()));

                Toast.makeText(getApplicationContext(),"Film added successfully",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initializeViews(){
        inputTitle = (EditText) findViewById(R.id.insertTitleView);
        inputCountry = (EditText) findViewById(R.id.insertCountryView);
        inputDirector = (EditText) findViewById(R.id.insertDirectorView);
        inputProtagonist = (EditText) findViewById(R.id.insertProtagonistView);
        inputYear = (EditText) findViewById(R.id.insertYearView);
        inputRate = (EditText) findViewById(R.id.insertRateView);
        insertButton = (Button) findViewById(R.id.insertButtonView);
    }


}
