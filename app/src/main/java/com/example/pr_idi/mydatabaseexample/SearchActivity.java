package com.example.pr_idi.mydatabaseexample;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private FilmData filmData;
    private ListView listView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView = (ListView) findViewById(R.id.searchResultList);

        filmData = new FilmData(this);
        filmData.open();

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) findViewById(R.id.searchView);

        /*LinearLayout ll = (LinearLayout)searchView.getChildAt(0);
        LinearLayout ll2 = (LinearLayout)ll.getChildAt(2);
        LinearLayout ll3 = (LinearLayout)ll2.getChildAt(1);
        SearchView.SearchAutoComplete autoComplete = ((SearchView.SearchAutoComplete)ll3.getChildAt(0));
        autoComplete.setHintTextColor(Color.WHITE);*/

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            List<Film> filmsFound = filmData.getFilms(MySQLiteHelper.COLUMN_PROTAGONIST, query);
            FilmAdapter adapter = new FilmAdapter(getApplicationContext(), filmsFound);
            listView.setAdapter(adapter);
        }
        else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String data = intent.getDataString();
            System.out.println(data);
            searchView.setQuery(data, true);
        }
    }
}
