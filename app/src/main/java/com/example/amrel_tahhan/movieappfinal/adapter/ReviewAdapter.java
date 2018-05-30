package com.example.amrel_tahhan.movieappfinal.adapter;

/**
 * Created by Amr El-Tahhan on 5/30/2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.amrel_tahhan.movieappfinal.R;
import com.example.amrel_tahhan.movieappfinal.model.Review;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Amr El-Tahhan on 2/27/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<Review> reviewList = new ArrayList<>();


    public ReviewAdapter(List<Review> reviews) {
        reviews = reviewList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView reviewAuthor = itemView.findViewById(R.id.item_author);

        TextView reviewContent = itemView.findViewById(R.id.item_content);
        View viewHolderView;

        public ViewHolder(View itemView) {
            super(itemView);
            viewHolderView = itemView;
        }
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {
        final Review reviewItem = reviewList.get(position);
        final Context context = holder.viewHolderView.getContext();

        holder.reviewAuthor.setText(reviewItem.getAuthor());
        holder.reviewContent.setText(reviewItem.getContent());




    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }


}

