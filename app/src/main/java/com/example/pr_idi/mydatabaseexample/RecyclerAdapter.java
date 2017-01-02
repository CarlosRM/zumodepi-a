package com.example.pr_idi.mydatabaseexample;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Carlos on 29/12/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {

    private List<Film> listFilmData;
    private LayoutInflater inflater;
    private Context context;


    private ItemClickCallback itemClickCallback;

    public interface ItemClickCallback {
        void onItemClick(int p);
    }

    public void setItemClickCallback (final ItemClickCallback itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }


    public RecyclerAdapter(List<Film> listFilmData, Context context){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.listFilmData = listFilmData;
    }

    public void updateData(List<Film> listFilmData) {
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

        if(position == 0)holder.divider.setVisibility(View.INVISIBLE);
        holder.title.setText(film.getTitle());
        holder.director.setText(film.getDirector());
        holder.country.setText(" - " + film.getCountry());
        holder.year.setText(" - " + String.valueOf(film.getYear()));
        holder.protagonist.setText(film.getProtagonist());

        int rate = film.getCritics_rate();
        holder.star1.setImageResource(R.drawable.ic_star_border_black_18dp);
        holder.star2.setImageResource(R.drawable.ic_star_border_black_18dp);
        holder.star2.setImageResource(R.drawable.ic_star_border_black_18dp);
        holder.star2.setImageResource(R.drawable.ic_star_border_black_18dp);
        holder.star2.setImageResource(R.drawable.ic_star_border_black_18dp);

        if(rate >= 10 && rate < 20){
            holder.star1.setImageResource(R.drawable.ic_star_half_black_18dp);
            holder.star2.setImageResource(R.drawable.ic_star_border_black_18dp);
            holder.star3.setImageResource(R.drawable.ic_star_border_black_18dp);
            holder.star4.setImageResource(R.drawable.ic_star_border_black_18dp);
            holder.star5.setImageResource(R.drawable.ic_star_border_black_18dp);
        }else if(rate >= 20 && rate < 30){
            holder.star1.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star2.setImageResource(R.drawable.ic_star_border_black_18dp);
            holder.star3.setImageResource(R.drawable.ic_star_border_black_18dp);
            holder.star4.setImageResource(R.drawable.ic_star_border_black_18dp);
            holder.star5.setImageResource(R.drawable.ic_star_border_black_18dp);
        }else if(rate >= 30 && rate < 40){
            holder.star1.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star2.setImageResource(R.drawable.ic_star_half_black_18dp);
            holder.star3.setImageResource(R.drawable.ic_star_border_black_18dp);
            holder.star4.setImageResource(R.drawable.ic_star_border_black_18dp);
            holder.star5.setImageResource(R.drawable.ic_star_border_black_18dp);
        }else if(rate >= 40 && rate < 50){
            holder.star1.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star2.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star3.setImageResource(R.drawable.ic_star_border_black_18dp);
            holder.star4.setImageResource(R.drawable.ic_star_border_black_18dp);
            holder.star5.setImageResource(R.drawable.ic_star_border_black_18dp);
        }else if(rate >= 50 && rate < 60){
            holder.star1.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star2.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star3.setImageResource(R.drawable.ic_star_half_black_18dp);
            holder.star4.setImageResource(R.drawable.ic_star_border_black_18dp);
            holder.star5.setImageResource(R.drawable.ic_star_border_black_18dp);
        }else if(rate >= 60 && rate < 70){
            holder.star1.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star2.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star3.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star4.setImageResource(R.drawable.ic_star_border_black_18dp);
            holder.star5.setImageResource(R.drawable.ic_star_border_black_18dp);
        }else if(rate >= 70 && rate < 80){
            holder.star1.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star2.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star3.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star4.setImageResource(R.drawable.ic_star_half_black_18dp);
            holder.star5.setImageResource(R.drawable.ic_star_border_black_18dp);
        }else if(rate >= 80 && rate < 90){
            holder.star1.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star2.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star3.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star4.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star5.setImageResource(R.drawable.ic_star_border_black_18dp);
        }else if(rate >= 90 && rate < 99){
            holder.star1.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star2.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star3.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star4.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star5.setImageResource(R.drawable.ic_star_half_black_18dp);
        }else {
            holder.star1.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star2.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star3.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star4.setImageResource(R.drawable.ic_star_black_18dp);
            holder.star5.setImageResource(R.drawable.ic_star_black_18dp);
        }
    }

    @Override
    public int getItemCount() {
        return listFilmData.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title;
        private TextView director;
        private TextView year;
        private TextView country;
        private TextView protagonist;

        private ImageView star1;
        private ImageView star2;
        private ImageView star3;
        private ImageView star4;
        private ImageView star5;

        private ImageView deleteFilm;

        private View divider;
        private View container;


        public RecyclerHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.titleTextView);
            director = (TextView) itemView.findViewById(R.id.directorTextView);
            year = (TextView) itemView.findViewById(R.id.yearTextView);
            country = (TextView) itemView.findViewById(R.id.countryTextView);
            protagonist = (TextView) itemView.findViewById(R.id.protagonistTextView);
            star1 = (ImageView) itemView.findViewById(R.id.star1);
            star2 = (ImageView) itemView.findViewById(R.id.star2);
            star3 = (ImageView) itemView.findViewById(R.id.star3);
            star4 = (ImageView) itemView.findViewById(R.id.star4);
            star5 = (ImageView) itemView.findViewById(R.id.star5);
            deleteFilm = (ImageView) itemView.findViewById(R.id.deleteFilmImageView);
            divider = itemView.findViewById(R.id.lineView);
            container = itemView.findViewById(R.id.containerView);
            //container.setOnClickListener(this);
            deleteFilm.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickCallback.onItemClick(getAdapterPosition());
        }

    }


}
