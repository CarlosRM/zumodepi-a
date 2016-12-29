package com.example.pr_idi.mydatabaseexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Carlos on 29/12/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

    private List<Film> listFilmData;
    private LayoutInflater inflater;

    public RecyclerAdapter(List<Film> listFilmData, Context context){
        this.inflater = LayoutInflater.from(context);
        this.listFilmData = listFilmData;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_view_item,parent,false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        Film film = listFilmData.get(position);
        holder.title.setText(film.getTitle());
        holder.director.setText(film.getDirector());
    }

    @Override
    public int getItemCount() {
        return listFilmData.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView director;
        private View container;

        public RecyclerHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.titleTextView);
            director = (TextView) itemView.findViewById(R.id.directorTextView);
            container = itemView.findViewById(R.id.containerView);
        }
    }
}
