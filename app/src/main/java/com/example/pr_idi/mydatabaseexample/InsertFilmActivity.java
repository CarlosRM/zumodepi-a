package com.example.pr_idi.mydatabaseexample;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class InsertFilmActivity extends AppCompatActivity {

    private EditText inputTitle;
    private Spinner inputCountry;
    private Spinner inputYear;
    private int defaultCountryValue;
    private EditText inputDirector;
    private EditText inputProtagonist;
    private RatingBar inputRate;
    private TextView rateValue;
    private FilmData filmData;
    private Button insertButton;

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
    }

    private void setListeners() {

        NavigationView navView = (NavigationView) findViewById(R.id.navMenu);
        DrawerLayout navDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        navView.getMenu().getItem(NavMenuListener.insertFilmButton).setChecked(true);
        navView.setNavigationItemSelectedListener(new NavMenuListener(this, navDrawer));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, navDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navDrawer.setDrawerListener(toggle);
        toggle.syncState();

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (inputTitle.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "A title is required", Toast.LENGTH_SHORT).show();
                } else if (inputDirector.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "A director is required", Toast.LENGTH_SHORT).show();
                } else if (inputProtagonist.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "A protagonist is required", Toast.LENGTH_SHORT).show();
                } else {
                    filmData.createFilm(inputTitle.getText().toString(), inputDirector.getText().toString(),
                            inputCountry.getSelectedItem().toString(), inputProtagonist.getText().toString(),
                            Integer.valueOf(inputYear.getSelectedItem().toString()),
                            (int) (inputRate.getRating() * 20));

                    Toast.makeText(getApplicationContext(), "Film added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        inputRate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateValue.setText(Float.toString(rating * 2));
            }
        });

    }

    private void initializeViews() {
        inputTitle = (EditText) findViewById(R.id.insertTitleView);

        inputCountry = (Spinner) findViewById(R.id.countrySpinnerView);
        //Populate spinner
        Locale[] locale = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        String country;
        for (Locale loc : locale) {
            country = loc.getDisplayCountry();
            if (country.length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
        defaultCountryValue = countries.indexOf(this.getResources().getConfiguration().locale.getDisplayCountry());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.country_spinner_style, countries);
        inputCountry.setAdapter(adapter);

        inputYear = (Spinner) findViewById(R.id.yearSpinnerView);
        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        ArrayList<String> years = new ArrayList<>();
        for (int i = currentYear; i >= 1896; --i) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, R.layout.country_spinner_style, years);
        inputYear.setAdapter(yearAdapter);

        inputDirector = (EditText) findViewById(R.id.insertDirectorView);
        inputProtagonist = (EditText) findViewById(R.id.insertProtagonistView);
        inputRate = (RatingBar) findViewById(R.id.ratingBarInsert);
        rateValue = (TextView) findViewById(R.id.rateValueInsert);
        insertButton = (Button) findViewById(R.id.insertButtonView);
    }
}
