package com.example.pr_idi.mydatabaseexample;


import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private FilmData filmData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        filmData = new FilmData(this);
        filmData.open();

        List<Film> values = filmData.getAllFilms(MySQLiteHelper.COLUMN_TITLE);

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ListView filmList = (ListView) findViewById(R.id.list);
        ArrayAdapter<Film> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);
        filmList.setAdapter(adapter);

        NavigationView navView = (NavigationView) findViewById(R.id.navMenu);
        DrawerLayout navDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        navView.setNavigationItemSelectedListener(new NavMenuListener(this, navDrawer));
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