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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private FilmData filmData;
    private SearchView searchView;
    private ListView filmList;
    private boolean searchSubstring;
    private boolean lastSearchSubstring;
    private static boolean filmInserted = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        toolbar.setLogo(R.drawable.ic_home);
        setSupportActionBar(toolbar);
        searchSubstring = true;
        lastSearchSubstring = true;

        NavigationView navView = (NavigationView) findViewById(R.id.navMenu);
        DrawerLayout navDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        navView.getMenu().getItem(NavMenuListener.homneButton).setChecked(true);
        navView.setNavigationItemSelectedListener(new NavMenuListener(this, navDrawer));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, navDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

        };
        navDrawer.setDrawerListener(toggle);
        toggle.syncState();

        filmData = new FilmData(this);
        filmData.open();

        List<Film> values = filmData.getAllFilms(MySQLiteHelper.COLUMN_TITLE);

        filmList = (ListView) findViewById(R.id.list);
        FilmAdapter adapter = new FilmAdapter(this, values);
        filmList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem =  menu.findItem(R.id.search_action_menu);

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                List<Film> values = filmData.getAllFilms(MySQLiteHelper.COLUMN_TITLE);
                FilmAdapter adapter = new FilmAdapter(getApplicationContext(), values);
                filmList.setAdapter(adapter);
                return true;
            }
        });

        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint(getString(R.string.search_hint_actor));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            List<Film> values;
            if (searchSubstring) {
                values = filmData.getFilmsContain(MySQLiteHelper.COLUMN_PROTAGONIST,
                        query, MySQLiteHelper.COLUMN_TITLE);
                lastSearchSubstring = true;
            }
            else {
                values = filmData.getFilms(MySQLiteHelper.COLUMN_PROTAGONIST, query, MySQLiteHelper.COLUMN_TITLE);
                searchSubstring = true;
                lastSearchSubstring = false;
            }
            FilmAdapter adapter = new FilmAdapter(this, values);
            filmList.setAdapter(adapter);
        }
        else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            searchSubstring = false;
            String data = intent.getDataString();
            searchView.setQuery(data, true);
        }
    }

    public static void insertFilm() {
        filmInserted = true;
    }

    @Override
    protected void onResume() {
        Predictor.setMainSearchConf();
        if (filmInserted) {
            List<Film> values;
            if (lastSearchSubstring) {
                values = filmData.getFilmsContain(MySQLiteHelper.COLUMN_PROTAGONIST,
                        searchView.getQuery().toString(), MySQLiteHelper.COLUMN_TITLE);
            }
            else {
                values = filmData.getFilms(MySQLiteHelper.COLUMN_PROTAGONIST, searchView.getQuery().toString(), MySQLiteHelper.COLUMN_TITLE);
            }
            FilmAdapter adapter = new FilmAdapter(this, values);
            filmList.setAdapter(adapter);
            filmInserted = false;
        }
        super.onResume();
    }
}