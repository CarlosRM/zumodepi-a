package com.example.pr_idi.mydatabaseexample;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickCallback{

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    //private EditText searchView;
    private SearchView searchView;
    private Spinner categorySpinner;
    private FilmData filmData;
    private List<Film> values;
    private List<String> categories;
    private String currentOrder;
    private String currentCriteria;
    private Spinner categorySpinner2;
    int removePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Advanced View");
        setSupportActionBar(toolbar);
        initializeViews();

        NavigationView navView = (NavigationView) findViewById(R.id.navMenu);
        DrawerLayout navDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        navView.getMenu().getItem(NavMenuListener.advancedViewButton).setChecked(true);
        navView.setNavigationItemSelectedListener(new NavMenuListener(this, navDrawer));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, navDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navDrawer.setDrawerListener(toggle);
        toggle.syncState();

        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(this, R.layout.category_spinner_style,categories);
        categorySpinnerAdapter.setDropDownViewResource(R.layout.category_spinner_style);
        categorySpinner.setAdapter(categorySpinnerAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = categorySpinner.getSelectedItem().toString().toLowerCase();
                //searchView.setHint("Search by " + selectedItem);
                switch (selectedItem) {
                    case "title":
                        currentCriteria = MySQLiteHelper.COLUMN_TITLE;
                        break;
                    case "director":
                        currentCriteria = MySQLiteHelper.COLUMN_DIRECTOR;
                        break;
                    case "protagonist":
                        currentCriteria = MySQLiteHelper.COLUMN_PROTAGONIST;
                        break;
                    case "year":
                        currentCriteria = MySQLiteHelper.COLUMN_YEAR_RELEASE;
                        break;
                    case "country":
                        currentCriteria = MySQLiteHelper.COLUMN_COUNTRY;
                        break;
                }
                if (searchView.getQuery().toString() == "") {
                    List<Film> filmsFound = filmData.getAllFilms(currentOrder);
                    values = filmsFound;
                    recyclerAdapter.updateData(filmsFound);
                    recyclerAdapter.notifyDataSetChanged();
                } else {
                    List<Film> filmsFound = filmData.getFilmsContain(currentCriteria,
                            searchView.getQuery().toString(), currentOrder);
                    values = filmsFound;
                    recyclerAdapter.updateData(filmsFound);
                    recyclerAdapter.notifyDataSetChanged();
                }
                Predictor.setCurrentCriteria(currentCriteria);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        categorySpinner2.setAdapter(categorySpinnerAdapter);
        categorySpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = categorySpinner2.getSelectedItem().toString().toLowerCase();
                //searchView.setHint("Search by " + selectedItem);
                switch (selectedItem) {
                    case "title":
                        currentOrder = MySQLiteHelper.COLUMN_TITLE;
                        break;
                    case "director":
                        currentOrder = MySQLiteHelper.COLUMN_DIRECTOR;
                        break;
                    case "protagonist":
                        currentOrder = MySQLiteHelper.COLUMN_PROTAGONIST;
                        break;
                    case "year":
                        currentOrder = MySQLiteHelper.COLUMN_YEAR_RELEASE;
                        break;
                    case "country":
                        currentOrder = MySQLiteHelper.COLUMN_COUNTRY;
                        break;
                }
                if (searchView.getQuery().toString() == "") {
                    List<Film> filmsFound = filmData.getAllFilms(currentOrder);
                    recyclerAdapter.updateData(filmsFound);
                    recyclerAdapter.notifyDataSetChanged();
                } else {
                    List<Film> filmsFound = filmData.getFilmsContain(currentCriteria,
                            searchView.getQuery().toString(), currentOrder);
                    recyclerAdapter.updateData(filmsFound);
                    recyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(searchView.getText().toString()=="") {
                    List<Film> filmsFound = filmData.getAllFilms(currentOrder);
                    recyclerAdapter.updateData(filmsFound);
                    recyclerAdapter.notifyDataSetChanged();
                } else {
                    List<Film> filmsFound = filmData.getFilmsContain(currentCriteria,
                            searchView.getText().toString(), currentOrder);
                    recyclerAdapter.updateData(filmsFound);
                    recyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        filmData = new FilmData(this);
        filmData.open();

        values = filmData.getAllFilms(currentOrder);

        recyclerAdapter = new RecyclerAdapter(values,this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setItemClickCallback(this);
    }

    @Override
    public void onItemClick(MenuItem item, int p) {
        final int position = p;
        switch (item.getItemId()) {
            case R.id.modify_button:
                Film film = values.get(position);
                filmData.updateRatingFilm(film, 0);
                recyclerAdapter.notifyDataSetChanged();
                break;
            case R.id.delete_button:
                new AlertDialog.Builder(this)
                        .setTitle("Delete film")
                        .setMessage("Are you sure you want to delete this film?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Film film = values.get(position);
                                filmData.deleteFilm(film);
                                values.remove(position);
                                recyclerAdapter.updateData(values);
                                recyclerAdapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(),film.getTitle() + " was deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(R.drawable.ic_delete_forever_black_24dp)
                        .show();
                break;
        }
    }

    private void initializeViews() {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.searchView);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        View searchPlate = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        searchPlate.setBackgroundResource(android.R.drawable.dark_header);

        ImageView searchIcon = (ImageView)searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageResource(R.drawable.ic_search_white_24dp);

        ImageView searchCloseIcon = (ImageView)searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchCloseIcon.setImageResource(R.drawable.ic_clear_search);

        searchCloseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("", false);
                System.out.println("Closing " + currentOrder);
                /*List<Film> allFilms = filmData.getAllFilms(currentOrder);
                values = allFilms;*/
                values = filmData.getAllFilms(currentOrder);
                recyclerAdapter.updateData(values);
                recyclerAdapter.notifyDataSetChanged();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categorySpinner = (Spinner) findViewById(R.id.categorySpinnerView);
        categorySpinner2 = (Spinner) findViewById(R.id.categorySpinnerView2);
        //searchView = (EditText) findViewById(R.id.titleSearchView);
        currentOrder = MySQLiteHelper.COLUMN_YEAR_RELEASE;
        currentCriteria = MySQLiteHelper.COLUMN_TITLE;
        categories = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.categories)));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            List<Film> filmsFound = filmData.getFilmsContain(currentCriteria,
                    query, currentOrder);
            values = filmsFound;
            recyclerAdapter.updateData(filmsFound);
            recyclerAdapter.notifyDataSetChanged();
        }
        else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String data = intent.getDataString();
            System.out.println(data);
            searchView.setQuery(data, true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Predictor.setCurrentCriteria(currentCriteria);
        Predictor.setLowerBound(1);
        List<Film> filmsFound = filmData.getFilmsContain(currentCriteria,
                searchView.getQuery().toString(), currentOrder);
        values = filmsFound;
        recyclerAdapter.updateData(filmsFound);
        recyclerAdapter.notifyDataSetChanged();
    }
}
