package com.example.pr_idi.mydatabaseexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity implements RecyclerAdapter.ItemClickCallback{

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private FilmData filmData;
    private List<Film> values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        filmData = new FilmData(this);
        filmData.open();

        values = filmData.getAllFilms(MySQLiteHelper.COLUMN_YEAR_RELEASE);

        recyclerAdapter = new RecyclerAdapter(values,this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setItemClickCallback(this);


    }

    @Override
    public void onItemClick(int p) {
        Film film = (Film) values.get(p);
        filmData.deleteFilm(film);
        values.remove(p);
        recyclerView.removeViewAt(p);
        recyclerAdapter.notifyItemRemoved(p);
        recyclerAdapter.notifyItemRangeChanged(p, values.size());
    }
}
