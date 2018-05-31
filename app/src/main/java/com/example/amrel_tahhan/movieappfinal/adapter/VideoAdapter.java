package com.example.amrel_tahhan.movieappfinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amrel_tahhan.movieappfinal.Constants;
import com.example.amrel_tahhan.movieappfinal.Description;
import com.example.amrel_tahhan.movieappfinal.R;
import com.example.amrel_tahhan.movieappfinal.model.Video;

import java.util.ArrayList;
import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>  {
    private List<Video> videoList = new ArrayList<>();







    public void updateVideos(@NonNull final List<Video> videos) {
        this.videoList = videos;
        notifyDataSetChanged();
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final VideoAdapter.ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();
        holder.videoLink.setText(Constants.baseVideo);
        holder.videoLink.append(videoList.get(position).getKey());
         final String videoItem = holder.videoLink.toString();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(videoItem));
                context.startActivity(i);


            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView videoLink = itemView.findViewById(R.id.review_link);



        ViewHolder(View itemView) {
            super(itemView);
        }
    }




}



