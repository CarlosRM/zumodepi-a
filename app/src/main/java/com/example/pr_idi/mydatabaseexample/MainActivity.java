package com.example.pr_idi.mydatabaseexample;


import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private FilmData filmData;
    private ListView filmList;
    private NavigationView navView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        filmData = new FilmData(this);
        filmData.open();

        List<Film> values = filmData.getAllFilms(null);

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        filmList = (ListView)findViewById(R.id.list);
        ArrayAdapter<Film> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);
        filmList.setAdapter(adapter);

        navView = (NavigationView) findViewById(R.id.navMenu);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.switchActivity:
                        Intent myIntent = new Intent(getApplicationContext(), RecyclerViewActivity.class);
                        startActivity(myIntent);
                        break;
                    case R.id.addFilmButton:
                        Intent myIntent2 = new Intent(getApplicationContext(), InsertFilmActivity.class);
                        startActivity(myIntent2);
                        break;
                    default: return false;
                }
                return true;
            }
        });
    }
    
    @Override
    protected void onResume() {
        filmData.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        filmData.close();
        super.onPause();
    }

}