package com.example.pr_idi.mydatabaseexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.Locale;

public class InsertFilmActivity extends AppCompatActivity {

    private EditText inputTitle;
    private Spinner inputCountry;
    private int defaultCountryValue;
    private EditText inputDirector;
    private EditText inputProtagonist;
    private SeekBar inputYear;
    private TextView yearValue;
    private SeekBar inputRate;
    private TextView rateValue;
    private FilmData filmData;
    private Button insertButton;

    private ImageView star1;
    private ImageView star2;
    private ImageView star3;
    private ImageView star4;
    private ImageView star5;

    private int star1State;
    private int star2State;
    private int star3State;
    private int star4State;
    private int star5State;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_film);
        initializeViews();
        setListeners();
        inputRate.setProgress(50);
        inputCountry.setSelection(defaultCountryValue);
        filmData = new FilmData(this);
        filmData.open();



        inputRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rateValue.setText(Integer.toString(progress));
                updateStars(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
    private void setListeners(){
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inputTitle.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "A title is required",Toast.LENGTH_SHORT).show();
                }else if(inputDirector.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"A director is required",Toast.LENGTH_SHORT).show();
                }else if(inputProtagonist.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"A protagonist is required",Toast.LENGTH_SHORT).show();
                }else {
                    filmData.createFilm(inputTitle.getText().toString(), inputDirector.getText().toString(),
                            inputCountry.getSelectedItem().toString(), inputProtagonist.getText().toString(),
                            inputYear.getProgress() + 1896,
                            inputRate.getProgress());

                    Toast.makeText(getApplicationContext(), "Film added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        inputRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rateValue.setText(Integer.toString(progress));
                updateStars(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        inputYear.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                yearValue.setText(Integer.toString(progress+1896));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (star1State){
                    case 0:
                        star1State = 1;
                        inputRate.setProgress(10);
                        break;
                    case 1:
                        star1State = 2;
                        inputRate.setProgress(20);
                        break;
                    case 2:
                        star1State = 0;
                        inputRate.setProgress(0);
                        break;
                }
            }
        });

        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (star2State){
                    case 0:
                        star1State = 1;
                        inputRate.setProgress(30);
                        break;
                    case 1:
                        star2State = 2;
                        inputRate.setProgress(40);
                        break;
                    case 2:
                        star2State = 0;
                        inputRate.setProgress(20);
                        break;
                }
            }
        });

        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (star3State){
                    case 0:
                        star3State = 1;
                        inputRate.setProgress(50);
                        break;
                    case 1:
                        star3State = 2;
                        inputRate.setProgress(60);
                        break;
                    case 2:
                        star3State = 0;
                        inputRate.setProgress(40);
                        break;
                }
            }
        });

        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (star4State){
                    case 0:
                        star4State = 1;
                        inputRate.setProgress(70);
                        break;
                    case 1:
                        star4State = 2;
                        inputRate.setProgress(80);
                        break;
                    case 2:
                        star4State = 0;
                        inputRate.setProgress(60);
                        break;
                }
            }
        });

        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (star5State){
                    case 0:
                        star5State = 1;
                        inputRate.setProgress(90);
                        break;
                    case 1:
                        star5State = 2;
                        inputRate.setProgress(100);
                        break;
                    case 2:
                        star5State = 0;
                        inputRate.setProgress(80);
                        break;
                }
            }
        });
    }

    private void updateStars(int progress){
        if(progress < 10){
            star1.setImageResource(R.drawable.ic_star_border_black_18dp);
            star2.setImageResource(R.drawable.ic_star_border_black_18dp);
            star3.setImageResource(R.drawable.ic_star_border_black_18dp);
            star4.setImageResource(R.drawable.ic_star_border_black_18dp);
            star5.setImageResource(R.drawable.ic_star_border_black_18dp);
            star1State = 0;
            star2State = 0;
            star3State = 0;
            star4State = 0;
            star5State = 0;
        }else if(progress >= 10 && progress < 20){
            star1.setImageResource(R.drawable.ic_star_half_black_18dp);
            star2.setImageResource(R.drawable.ic_star_border_black_18dp);
            star3.setImageResource(R.drawable.ic_star_border_black_18dp);
            star4.setImageResource(R.drawable.ic_star_border_black_18dp);
            star5.setImageResource(R.drawable.ic_star_border_black_18dp);
            star1State = 1;
            star2State = 0;
            star3State = 0;
            star4State = 0;
            star5State = 0;
        }else if(progress >= 20 && progress < 30){
            star1.setImageResource(R.drawable.ic_star_black_18dp);
            star2.setImageResource(R.drawable.ic_star_border_black_18dp);
            star3.setImageResource(R.drawable.ic_star_border_black_18dp);
            star4.setImageResource(R.drawable.ic_star_border_black_18dp);
            star5.setImageResource(R.drawable.ic_star_border_black_18dp);
            star1State = 2;
            star2State = 0;
            star3State = 0;
            star4State = 0;
            star5State = 0;
        }else if(progress >= 30 && progress < 40){
            star1.setImageResource(R.drawable.ic_star_black_18dp);
            star2.setImageResource(R.drawable.ic_star_half_black_18dp);
            star3.setImageResource(R.drawable.ic_star_border_black_18dp);
            star4.setImageResource(R.drawable.ic_star_border_black_18dp);
            star5.setImageResource(R.drawable.ic_star_border_black_18dp);
            star1State = 2;
            star2State = 1;
            star3State = 0;
            star4State = 0;
            star5State = 0;
        }else if(progress >= 40 && progress < 50){
            star1.setImageResource(R.drawable.ic_star_black_18dp);
            star2.setImageResource(R.drawable.ic_star_black_18dp);
            star3.setImageResource(R.drawable.ic_star_border_black_18dp);
            star4.setImageResource(R.drawable.ic_star_border_black_18dp);
            star5.setImageResource(R.drawable.ic_star_border_black_18dp);
            star1State = 2;
            star2State = 2;
            star3State = 0;
            star4State = 0;
            star5State = 0;
        }else if(progress >= 50 && progress < 60){
            star1.setImageResource(R.drawable.ic_star_black_18dp);
            star2.setImageResource(R.drawable.ic_star_black_18dp);
            star3.setImageResource(R.drawable.ic_star_half_black_18dp);
            star4.setImageResource(R.drawable.ic_star_border_black_18dp);
            star5.setImageResource(R.drawable.ic_star_border_black_18dp);
            star1State = 2;
            star2State = 2;
            star3State = 1;
            star4State = 0;
            star5State = 0;
        }else if(progress >= 60 && progress < 70){
            star1.setImageResource(R.drawable.ic_star_black_18dp);
            star2.setImageResource(R.drawable.ic_star_black_18dp);
            star3.setImageResource(R.drawable.ic_star_black_18dp);
            star4.setImageResource(R.drawable.ic_star_border_black_18dp);
            star5.setImageResource(R.drawable.ic_star_border_black_18dp);
            star1State = 2;
            star2State = 2;
            star3State = 2;
            star4State = 0;
            star5State = 0;
        }else if(progress >= 70 && progress < 80){
            star1.setImageResource(R.drawable.ic_star_black_18dp);
            star2.setImageResource(R.drawable.ic_star_black_18dp);
            star3.setImageResource(R.drawable.ic_star_black_18dp);
            star4.setImageResource(R.drawable.ic_star_half_black_18dp);
            star5.setImageResource(R.drawable.ic_star_border_black_18dp);
            star1State = 2;
            star2State = 2;
            star3State = 2;
            star4State = 1;
            star5State = 0;
        }else if(progress >= 80 && progress < 90){
            star1.setImageResource(R.drawable.ic_star_black_18dp);
            star2.setImageResource(R.drawable.ic_star_black_18dp);
            star3.setImageResource(R.drawable.ic_star_black_18dp);
            star4.setImageResource(R.drawable.ic_star_black_18dp);
            star5.setImageResource(R.drawable.ic_star_border_black_18dp);
            star1State = 2;
            star2State = 2;
            star3State = 2;
            star4State = 2;
            star5State = 0;
        }else if(progress >= 90 && progress < 99){
            star1.setImageResource(R.drawable.ic_star_black_18dp);
            star2.setImageResource(R.drawable.ic_star_black_18dp);
            star3.setImageResource(R.drawable.ic_star_black_18dp);
            star4.setImageResource(R.drawable.ic_star_black_18dp);
            star5.setImageResource(R.drawable.ic_star_half_black_18dp);
            star1State = 2;
            star2State = 2;
            star3State = 2;
            star4State = 2;
            star5State = 1;
        }else {
            star1.setImageResource(R.drawable.ic_star_black_18dp);
            star2.setImageResource(R.drawable.ic_star_black_18dp);
            star3.setImageResource(R.drawable.ic_star_black_18dp);
            star4.setImageResource(R.drawable.ic_star_black_18dp);
            star5.setImageResource(R.drawable.ic_star_black_18dp);
            star1State = 2;
            star2State = 2;
            star3State = 2;
            star4State = 2;
            star5State = 2;
        }
    }

    private void initializeViews(){
        inputTitle = (EditText) findViewById(R.id.insertTitleView);

        inputCountry = (Spinner) findViewById(R.id.countrySpinnerView);
            //Populate spinner
            Locale[] locale = Locale.getAvailableLocales();
            ArrayList<String> countries = new ArrayList<String>();
            String country;
            for( Locale loc : locale ){
                country = loc.getDisplayCountry();
                if( country.length() > 0 && !countries.contains(country) ){
                    countries.add( country );
                }
            }
            Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
            defaultCountryValue = countries.indexOf(this.getResources().getConfiguration().locale.getDisplayCountry());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.country_spinner_style, countries);
            inputCountry.setAdapter(adapter);

        inputDirector = (EditText) findViewById(R.id.insertDirectorView);
        inputProtagonist = (EditText) findViewById(R.id.insertProtagonistView);
        inputYear = (SeekBar) findViewById(R.id.insertYearView);
        yearValue = (TextView) findViewById(R.id.yearValueView);
        inputRate = (SeekBar) findViewById(R.id.insertRateView);
        rateValue = (TextView) findViewById(R.id.rateValueView);
        insertButton = (Button) findViewById(R.id.insertButtonView);
        star1 = (ImageView) findViewById(R.id.star11);
        star2 = (ImageView) findViewById(R.id.star22);
        star3 = (ImageView) findViewById(R.id.star33);
        star4 = (ImageView) findViewById(R.id.star44);
        star5 = (ImageView) findViewById(R.id.star55);

        star1State = star2State = star3State = star4State = star5State = 0;

    }


}
