package com.example.pr_idi.mydatabaseexample;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    int removePosition;

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
        final int removePosition = p;

        new AlertDialog.Builder(this)
                .setTitle("Delete film")
                .setMessage("Are you sure you want to delete this film?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Film film = (Film) values.get(removePosition);
                        filmData.deleteFilm(film);
                        values.remove(removePosition);
                        recyclerView.removeViewAt(removePosition);
                        recyclerAdapter.notifyItemRemoved(removePosition);
                        recyclerAdapter.notifyItemRangeChanged(removePosition, values.size());
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
}
