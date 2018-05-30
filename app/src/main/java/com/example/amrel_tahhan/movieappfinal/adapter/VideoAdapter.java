//package com.example.amrel_tahhan.movieappfinal.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.amrel_tahhan.movieappfinal.R;
//import com.example.amrel_tahhan.movieappfinal.model.Video;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
//
//    private List<Video> videoList = new ArrayList<>();
//
//
//    public VideoAdapter(List<Video> videos) {
//        videos = videoList;
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView reviewAuthor = itemView.findViewById(R.id.item_author);
//
//        TextView reviewContent = itemView.findViewById(R.id.item_content);
//        View viewHolderView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            viewHolderView = itemView;
//        }
//    }
//
//    @Override
//    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.review_item, parent, false);
//        VideoAdapter.ViewHolder viewHolder = new VideoAdapter.ViewHolder(v);
//
//        return viewHolder;
//
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        final Video reviewItem = reviewList.get(position);
//        final Context context = holder.viewHolderView.getContext();
//
//        holder.reviewAuthor.setText(reviewItem.getAuthor());
//        holder.reviewContent.setText(reviewItem.getContent());
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return videoList.size();
//    }
//
//
//}
//
//
//
