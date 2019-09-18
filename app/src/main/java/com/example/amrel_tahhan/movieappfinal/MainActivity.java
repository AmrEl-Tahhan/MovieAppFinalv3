package com.example.amrel_tahhan.movieappfinal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.example.amrel_tahhan.movieappfinal.adapter.MovieAdapter;
import com.example.amrel_tahhan.movieappfinal.database.MovieDatabase;
import com.example.amrel_tahhan.movieappfinal.model.MovieResponse;
import com.example.amrel_tahhan.movieappfinal.model.Movie;
import com.example.amrel_tahhan.movieappfinal.retrofit.MyWebService;
import com.example.amrel_tahhan.movieappfinal.viewmodel.MainViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
// sources that I've used
//1st http://hendiware.16mb.com/android-webservices-%D8%A8%D8%A7%D9%84%D8%B9%D8%B1%D8%A8%D9%8A%D8%A9-retrofit/
//     2nd   http://jakewharton.github.io/butterknife/
// 3rd https://www.youtube.com/watch?v=THadGrPeSJM
// 4th https://www.youtube.com/watch?v=kmUGLURRPkI&t=9s

public class MainActivity extends AppCompatActivity {
    public static final int MAX_WIDTH_COL_DP = 200;
    Parcelable mListState;
    public static final String ARRAY_TAG = "array";
    public static final String POSOTION_TAG = "position";
    public static final String SORT_TAG = "sort";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> mItemList = new ArrayList<>();
    private String mSort = Constants.SORT_POPULAR;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    MainViewModel mainViewModel;

    private boolean navPopular = true, navTopRated, navFavorite;

    Context context;
    GridLayoutManager mLayoutManager;

//    TaskHandler taskHandler = new TaskHandler() {
//        @Override
//        public void onComplete(List<Movie> data) {
//            MainActivity.this.mItemList = data;
//            movieAdapter.update(data);
//        }
//    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
        if (savedInstanceState != null){
            int position = savedInstanceState.getInt(POSOTION_TAG);
            Log.i("tag", "onCreate: " +position);
            mItemList = savedInstanceState.getParcelableArrayList(ARRAY_TAG);
            initRecyclerView(mItemList);
            recyclerView.scrollToPosition(position);
            mSort = savedInstanceState.getString(SORT_TAG);

        }
        else {
            if (isOnline()) {

                if (navPopular) {
                    requestMostPopular();
                }
                if (navFavorite) requestFavourites();
                if (navTopRated) requestTopRated(mItemList);


            } else {
                Toast.makeText(this, "Please Check your Internet", Toast.LENGTH_SHORT).show();

            }

        }






    }


    private void initRecyclerView(List<Movie> mItemList) {
        recyclerView.setVisibility(View.GONE);
        movieAdapter = new MovieAdapter(mItemList);
        recyclerView.setAdapter(movieAdapter);
        mLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        //change span dynamically based on screen width
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int viewWidth = recyclerView.getMeasuredWidth();
                        float cardViewWidth = MAX_WIDTH_COL_DP * getResources().getDisplayMetrics().density;
                        int newSpanCount = Math.max(2, (int) Math.floor(viewWidth / cardViewWidth));
                        mLayoutManager.setSpanCount(newSpanCount);
                        mLayoutManager.requestLayout();


                    }
                });
        recyclerView.setVisibility(View.VISIBLE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sort) {
            showSortDialog();
            return true;
        } else if (item.getItemId() == R.id.fav_button) {
            requestFavourites();
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showSortDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.sort_by)
                .setSingleChoiceItems(
                        new String[]{getString(R.string.main_sort_most_popular),
                                getString((R.string.main_sort_highest_rated))},
                        (mSort.equals(Constants.SORT_POPULAR)) ? 0 : 1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        requestMostPopular();
                                        mSort = Constants.SORT_POPULAR;
                                        break;
                                    case 1:
                                        requestTopRated(mItemList);
                                        mSort = Constants.SORT_HIGHEST_RATED;

                                        break;

                                }
                                dialog.dismiss();
                            }
                        });
        builder.create().show();
    }

    private void requestFavourites() {
        navPopular = false;
        navTopRated = false;
        navFavorite = true;
        mainViewModel = new MainViewModel(getApplication());
        mainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                initRecyclerView(movies);
            }
        });

    }

    private void requestMostPopular() {
        navPopular = true;
        navTopRated = false;
        navFavorite = false;
        Retrofit mRetrofit;
        MyWebService mService;
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        mService = mRetrofit.create(MyWebService.class);
        mService.discoverPopularMovie(Constants.MOVIEDB_APIKEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {

                MovieResponse body = response.body();

                if (body != null) {

                    mItemList.clear();
                    mItemList.addAll(body.getResults());
                    initRecyclerView(mItemList);
                    movieAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "response done", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), " failure", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void requestTopRated(final List<Movie> mItemList) {
        navPopular = false;
        navTopRated = true;
        navFavorite = false;


        Retrofit mRetrofit;
        MyWebService mService;
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        mService = mRetrofit.create(MyWebService.class);
        mService.discoverTopRatedMovie(Constants.MOVIEDB_APIKEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {

                MovieResponse body = response.body();

                if (body != null) {

                    mItemList.clear();
                    mItemList.addAll(body.getResults());
                    initRecyclerView(mItemList);
                    movieAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "response done", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), " failure", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



    @Override
    public void onSaveInstanceState(Bundle outState ) {
        if (navFavorite){
            mainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movies) {
                    mItemList.clear();
                    mItemList.addAll(movies);
                }
            });
        }
        outState.putParcelableArrayList(ARRAY_TAG,mItemList);
        outState.putInt(POSOTION_TAG,recyclerView.computeVerticalScrollRange());
        outState.putString(SORT_TAG,mSort);
        super.onSaveInstanceState(outState);

    }





}



