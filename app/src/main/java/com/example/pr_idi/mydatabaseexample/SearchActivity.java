package com.example.pr_idi.mydatabaseexample;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private FilmData filmData;
    private ListView listView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = (ListView)findViewById(R.id.searchResultList);

        filmData = new FilmData(this);
        filmData.open();

        onSearchRequested();

        /*editText = (EditText)findViewById(R.id.searchText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    List<Film> filmsFound = filmData.getFilms(MySQLiteHelper.COLUMN_PROTAGONIST, editText.getText().toString());
                    FilmAdapter adapter = new FilmAdapter(getApplicationContext(), filmsFound);
                    listView.setAdapter(adapter);
                    return true;
                }
                return false;
            }
        });*/
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            System.out.println("Hola " + query);
            List<Film> filmsFound = filmData.getFilms(MySQLiteHelper.COLUMN_PROTAGONIST, query);
            FilmAdapter adapter = new FilmAdapter(getApplicationContext(), filmsFound);
            listView.setAdapter(adapter);
        }
    }
}
