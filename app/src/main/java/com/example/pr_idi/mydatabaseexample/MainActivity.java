package com.example.pr_idi.mydatabaseexample;


import java.util.List;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private FilmData filmData;
    private SearchView searchView;
    private ListView filmList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        //toolbar.setLogo(R.drawable.ic_home);
        setSupportActionBar(toolbar);

        NavigationView navView = (NavigationView) findViewById(R.id.navMenu);
        DrawerLayout navDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        navView.getMenu().getItem(NavMenuListener.homneButton).setChecked(true);
        navView.setNavigationItemSelectedListener(new NavMenuListener(this, navDrawer));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, navDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navDrawer.setDrawerListener(toggle);
        toggle.syncState();

        filmData = new FilmData(this);
        filmData.open();

        List<Film> values = filmData.getAllFilms(MySQLiteHelper.COLUMN_TITLE);

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        filmList = (ListView) findViewById(R.id.list);
        ArrayAdapter<Film> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, values);
        filmList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search_action_menu);
        searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            List<Film> filmsFound = filmData.getFilmsContain(MySQLiteHelper.COLUMN_PROTAGONIST,
                    query, null);
            ArrayAdapter<Film> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, filmsFound);
            filmList.setAdapter(adapter);
        }
        else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String data = intent.getDataString();
            System.out.println(data);
            searchView.setQuery(data, true);
        }
    }

    @Override
    protected void onResume() {
        Predictor.setCurrentCriteria(MySQLiteHelper.COLUMN_PROTAGONIST);
        super.onResume();
    }
}