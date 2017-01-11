package com.example.pr_idi.mydatabaseexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FilmAdapter extends ArrayAdapter<Film> {
    public FilmAdapter(Context context, List<Film> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Film film = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.film_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.filmTextViewItem);
        TextView authorName = (TextView)convertView.findViewById(R.id.authorTextViewItem);
        // Populate the data into the template view using the data object
        tvName.setText(film.getTitle());
        authorName.setText(film.getDirector());
        // Return the completed view to render on screen
        return convertView;
    }
}
