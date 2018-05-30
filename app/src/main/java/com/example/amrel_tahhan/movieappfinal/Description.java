package com.example.amrel_tahhan.movieappfinal;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amrel_tahhan.movieappfinal.adapter.MovieAdapter;
import com.example.amrel_tahhan.movieappfinal.adapter.ReviewAdapter;
import com.example.amrel_tahhan.movieappfinal.model.Movie;
import com.example.amrel_tahhan.movieappfinal.model.Results;
import com.example.amrel_tahhan.movieappfinal.model.Review;
import com.example.amrel_tahhan.movieappfinal.retrofit.MyWebService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Description extends AppCompatActivity {
    @BindView(R.id.backdrop_ip)
     ImageView Backdrop;
    @BindView(R.id.original_title)
    TextView originalTitle ;
    @BindView(R.id.plot_synopsis)
    TextView plotSynopsis;
    @BindView(R.id.user_rating)
    TextView userRating;
    @BindView(R.id.release_date)
    TextView releaseDate;
    private Results mResults ;
    // recycler view fields
    @BindView(R.id.review_recycler_view)
    RecyclerView reviewRecyclerView;
    private ReviewAdapter mReviewAdapter;
    private List<Review> mReviewList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        ButterKnife.bind(this);
        mResults = getIntent().getParcelableExtra("movieItem");
        setTitle(mResults.getTitle());
        populateUI();
        //recyclerView
        reviewRecyclerView.setVisibility(View.GONE);
        mReviewAdapter = new ReviewAdapter(mReviewList);
        reviewRecyclerView.setAdapter(mReviewAdapter);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        reviewRecyclerView.setLayoutManager(mLayoutManager);
        retrieveReviews();

    }

    private void retrieveReviews() {
        Retrofit mRetrofit;
        MyWebService mService;

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        mService = mRetrofit.create(MyWebService.class);
        mService.discoverReview(mResults.getId(), Constants.MOVIEDB_APIKEY).enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.body() != null) {
                    mReviewList.clear();

                    mReviewList.addAll(response.body().getReviews());
                    mReviewAdapter.notifyDataSetChanged();
                    reviewRecyclerView.setVisibility(View.VISIBLE);
//                       mProgressView.stopAndGone();
                    Toast.makeText(getApplicationContext(), "response done", Toast.LENGTH_SHORT);
                }


            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Toast.makeText(getApplicationContext(), " failure", Toast.LENGTH_SHORT);

            }
        });
    }

    private void populateUI() {
        originalTitle.setText(mResults.getOriginal_title());
        //set release date
        releaseDate.setText(mResults.getRelease_date());
        userRating.setText(mResults.getVote_average() );
        userRating.append("/10");
        plotSynopsis.setText(mResults.getOverview());
        Picasso.with(this).load(Constants.ROOT_BACKDROP_IMAGE_URL + mResults.getBackdrop_path())
                .error(R.color.colorPrimary).placeholder(R.color.colorAccent).into(Backdrop);
    }


}

