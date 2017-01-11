package com.example.pr_idi.mydatabaseexample;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.List;

public class ModifyRateListener implements DialogInterface.OnClickListener {
    private RatingBar ratingBar;
    private Context context;
    private List<Film> values;
    private int position;
    private FilmData filmData;
    private RecyclerAdapter recyclerAdapter;

    public ModifyRateListener(Context context, List<Film> values, int position, FilmData filmData, RecyclerAdapter recyclerAdapter) {
        this.context = context;
        this.values = values;
        this.position = position;
        this.filmData = filmData;
        this.recyclerAdapter = recyclerAdapter;
        ratingBar = null;
    }

    public void setRatingBar(RatingBar ratingBar) {
        this.ratingBar = ratingBar;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Film film = values.get(position);
        filmData.updateRatingFilm(film, (int)(ratingBar.getRating()*2));
        recyclerAdapter.notifyDataSetChanged();
        Toast.makeText(context, film.getTitle() + "'s rate was modified successfully", Toast.LENGTH_SHORT).show();
    }
}
