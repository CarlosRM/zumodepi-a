package com.example.pr_idi.mydatabaseexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickCallback{

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private EditText searchView;
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
        initializeViews();

        NavigationView navView = (NavigationView) findViewById(R.id.navMenu);
        DrawerLayout navDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        navView.setNavigationItemSelectedListener(new NavMenuListener(this, navDrawer));

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
                if (searchView.getText().toString() == "") {
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
                if (searchView.getText().toString() == "") {
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
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchView.addTextChangedListener(new TextWatcher() {
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
        });

        filmData = new FilmData(this);
        filmData.open();

        values = filmData.getAllFilms(currentOrder);

        recyclerAdapter = new RecyclerAdapter(values,this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setItemClickCallback(this);


    }

    @Override
    public void onItemClick(int p) {
        final int removePosition = p;

        new AlertDialog.Builder(this)
                .setTitle("Delete film")
                .setMessage("Are you sure you want to delete this film?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Film film = (Film) values.get(removePosition);
                        filmData.deleteFilm(film);
                        values.remove(removePosition);
                        /*recyclerView.removeViewAt(removePosition);
                        recyclerAdapter.notifyItemRemoved(removePosition);
                        recyclerAdapter.notifyItemRangeChanged(removePosition, values.size());*/
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
    }

    private void initializeViews(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        categorySpinner = (Spinner) findViewById(R.id.categorySpinnerView);
        categorySpinner2 = (Spinner) findViewById(R.id.categorySpinnerView2);
        searchView = (EditText) findViewById(R.id.titleSearchView);
        currentOrder = MySQLiteHelper.COLUMN_YEAR_RELEASE;
        currentCriteria = MySQLiteHelper.COLUMN_TITLE;
        categories = new ArrayList<String>();
        categories.add("Title");
        categories.add("Director");
        categories.add("Protagonist");
        categories.add("Country");
        categories.add("Year");
    }
}
