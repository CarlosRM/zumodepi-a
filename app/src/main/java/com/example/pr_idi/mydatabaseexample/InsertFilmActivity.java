package com.example.pr_idi.mydatabaseexample;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

    private Button deleteTitleButton;
    private Button deleteProtagonistButton;
    private Button deleteDirectorButton;

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
        toolbar.setTitle("New Film");
        setSupportActionBar(toolbar);

        DrawerToggleAdvanced toggle = new DrawerToggleAdvanced(
                this, navDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navDrawer.setDrawerListener(toggle);
        toggle.syncState();

        deleteDirectorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDirector.setText("");
            }
        });

        deleteProtagonistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputProtagonist.setText("");
            }
        });

        deleteTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputTitle.setText("");
            }
        });

        inputDirector.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) deleteDirectorButton.setAlpha(0.2f);
                else deleteDirectorButton.setAlpha(0.6f);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputProtagonist.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) deleteProtagonistButton.setAlpha(0.2f);
                else deleteProtagonistButton.setAlpha(0.6f);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) deleteTitleButton.setAlpha(0.2f);
                else deleteTitleButton.setAlpha(0.6f);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



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
                            (int) (inputRate.getRating() * 2));
                            inputDirector.setText("");
                            inputProtagonist.setText("");
                            inputTitle.setText("");
                    RecyclerViewActivity.insertFilm();
                    MainActivity.insertFilm();
                    Toast.makeText(getApplicationContext(), "Film added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        inputRate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateValue.setText(Integer.toString((int) (rating * 2)));
            }
        });

    }

    private void initializeViews() {
        inputTitle = (EditText) findViewById(R.id.insertTitleView);

        inputCountry = (Spinner) findViewById(R.id.countrySpinnerView);
        inputCountry.getBackground().setColorFilter(Color.parseColor("#AAAAAA"), PorterDuff.Mode.SRC_ATOP);
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
        inputYear.getBackground().setColorFilter(Color.parseColor("#AAAAAA"), PorterDuff.Mode.SRC_ATOP);
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

        deleteDirectorButton = (Button) findViewById(R.id.deleteDirectorButton);
        deleteProtagonistButton = (Button) findViewById(R.id.deleteProtagonistButton);
        deleteTitleButton = (Button) findViewById(R.id.deleteTitleButton);
    }
}
