package com.example.amrel_tahhan.movieappfinal;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amrel_tahhan.movieappfinal.adapter.ReviewAdapter;
import com.example.amrel_tahhan.movieappfinal.adapter.VideoAdapter;
import com.example.amrel_tahhan.movieappfinal.database.MovieDatabase;
import com.example.amrel_tahhan.movieappfinal.model.Movie;
import com.example.amrel_tahhan.movieappfinal.model.ReviewResponse;
import com.example.amrel_tahhan.movieappfinal.model.VideoResponse;
import com.example.amrel_tahhan.movieappfinal.retrofit.MyWebService;
import com.example.amrel_tahhan.movieappfinal.viewmodel.AppExecutors;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Description extends AppCompatActivity {

    private Movie mMovie;

    @BindView(R.id.backdrop_ip)
    ImageView Backdrop;

    @BindView(R.id.original_title)
    TextView originalTitle;

    @BindView(R.id.plot_synopsis)
    TextView plotSynopsis;

    @BindView(R.id.user_rating)
    TextView userRating;

    @BindView(R.id.release_date)
    TextView releaseDate;


    // recycler view fields
    @BindView(R.id.review_recycler_view)
    RecyclerView reviewRecyclerView;
    @BindView(R.id.video_recycler_view)
    RecyclerView videoRecyclerView;

    ReviewAdapter mReviewAdapter;
    VideoAdapter mVideoAdapter;

    private Unbinder unbinder;
    final Movie favoriteMovie = new Movie(mMovie.getVote_average(), mMovie.getBackdrop_path(), mMovie.getId(), mMovie.getTitle()
            , mMovie.getOverview(), mMovie.getRelease_date(), mMovie.getOriginal_title(),
            mMovie.getVote_count(), mMovie.getPoster_path(), mMovie.getVideo());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        unbinder = ButterKnife.bind(this);
        mMovie = getIntent().getParcelableExtra("movieItem");
        setTitle(mMovie.getTitle());
        populateUI();
        //recyclerView
        retrieveVideos();
        retrieveReviews();
        initRecyclerView();
        initVidRecyclerView();
    }

    private void initRecyclerView() {
        reviewRecyclerView.setVisibility(View.GONE);
        mReviewAdapter = new ReviewAdapter();
        reviewRecyclerView.setAdapter(mReviewAdapter);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setNestedScrollingEnabled(false);
    }

    private void initVidRecyclerView() {
        videoRecyclerView.setVisibility(View.GONE);
        mVideoAdapter = new VideoAdapter();
        videoRecyclerView.setAdapter(mVideoAdapter);
        videoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void retrieveReviews() {
        Retrofit mRetrofit;
        MyWebService mService;

        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        mService = mRetrofit.create(MyWebService.class);
        mService.discoverReview(mMovie.getId().toString(), Constants.MOVIEDB_APIKEY).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {

                final ReviewResponse body = response.body();

                if (body != null) {

                    mReviewAdapter.updateReviews(body.getResults());
                    Log.d("AmrreviewResponse", body.getResults().toString());
                    reviewRecyclerView.setVisibility(View.VISIBLE);
//                       mProgressView.stopAndGone();
                    Toast.makeText(getApplicationContext(), "response done", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), " failure", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void retrieveVideos() {
        Retrofit vRetrofit;
        MyWebService vService;

        vRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        vService = vRetrofit.create(MyWebService.class);
        vService.discoverTrailer(mMovie.getId(), Constants.MOVIEDB_APIKEY).enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(@NonNull Call<VideoResponse> call, @NonNull Response<VideoResponse> response) {

                final VideoResponse body = response.body();

                if (body != null) {

                    mVideoAdapter.updateVideos(body.getResults());
                    Log.d("vidResponse", body.getResults().toString());
                    videoRecyclerView.setVisibility(View.VISIBLE);
//                       mProgressView.stopAndGone();
                    Toast.makeText(getApplicationContext(), "video response done", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), " failure", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void populateUI() {
        originalTitle.setText(mMovie.getOriginal_title());
        //set release date
        releaseDate.setText(mMovie.getRelease_date());
        userRating.setText(mMovie.getVote_average());
        userRating.append("/10");
        plotSynopsis.setText(mMovie.getOverview());
        Picasso.with(this).load(Constants.ROOT_BACKDROP_IMAGE_URL + mMovie.getBackdrop_path())
                .error(R.color.colorPrimary).placeholder(R.color.colorAccent).into(Backdrop);
    }

    @Override
    protected void onDestroy() {

        unbinder.unbind();

        super.onDestroy();
    }



    public void saveFavorite() {
//        final Movie favoriteMovie = new Movie(mMovie.getVote_average(), mMovie.getBackdrop_path(), mMovie.getId(), mMovie.getTitle()
//                , mMovie.getOverview(), mMovie.getRelease_date(), mMovie.getOriginal_title(),
//                mMovie.getVote_count(), mMovie.getPoster_path(), mMovie.getVideo());
        final MovieDatabase database = MovieDatabase.getInstanse(this);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.movieDao().insertMovie(favoriteMovie);

            }
        });
    }

    public void deleteFavorite() {
//        final Movie favoriteMovie = new Movie(mMovie.getVote_average(), mMovie.getBackdrop_path(), mMovie.getId(), mMovie.getTitle()
//                , mMovie.getOverview(), mMovie.getRelease_date(), mMovie.getOriginal_title(),
//                mMovie.getVote_count(), mMovie.getPoster_path(), mMovie.getVideo());

        final MovieDatabase database = MovieDatabase.getInstanse(this);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.movieDao().deleteMovie(favoriteMovie);

            }
        });
    }

    public void onFabClickHandler(View view) {

        final MovieDatabase database = MovieDatabase.getInstanse(this);
        if (mMovie.getId().equals(database.movieDao().loadMovieById(Integer.valueOf(mMovie.getId()))))
            deleteFavorite();
        else {
            saveFavorite();
        Toast.makeText(getApplicationContext(), "movie saved", Toast.LENGTH_SHORT).show();}
    }
}
