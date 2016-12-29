package com.example.pr_idi.mydatabaseexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private FilmData filmData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        filmData = new FilmData(this);
        filmData.open();

        List<Film> values = filmData.getAllFilms(MySQLiteHelper.COLUMN_YEAR_RELEASE);

        recyclerAdapter = new RecyclerAdapter(values,this);
        recyclerView.setAdapter(recyclerAdapter);


    }
}
