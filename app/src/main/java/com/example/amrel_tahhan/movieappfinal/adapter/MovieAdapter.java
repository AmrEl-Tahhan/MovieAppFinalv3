package com.example.amrel_tahhan.movieappfinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amrel_tahhan.movieappfinal.Constants;
import com.example.amrel_tahhan.movieappfinal.Description;
import com.example.amrel_tahhan.movieappfinal.R;
import com.example.amrel_tahhan.movieappfinal.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Amr El-Tahhan on 2/27/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> Movies;

    public MovieAdapter(List<Movie> movies) {
        Movies = movies;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ItemPoster = itemView.findViewById(R.id.item_poster);

        TextView ItemTitle = itemView.findViewById(R.id.item_title);
        View viewHolderView;

        public ViewHolder(View itemView) {
            super(itemView);
            viewHolderView = itemView;
        }
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grid, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        final Movie movieItem = Movies.get(position);
        final Context context = holder.viewHolderView.getContext();

        holder.ItemTitle.setText(movieItem.getTitle());

        String imagePath = Constants.ROOT_POSTER_IMAGE_URL + movieItem.getPoster_path();

        Picasso.with(context)
                .load(imagePath)
                .placeholder(R.drawable.load)
                .error(android.R.color.transparent)
                .into(holder.ItemPoster);
        holder.viewHolderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to Description an pass the movieItem data to it using intent.putExtra()
                Intent intent = new Intent(context, Description.class);
                intent.putExtra("movieItem", movieItem);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return Movies.size();
    }


}
