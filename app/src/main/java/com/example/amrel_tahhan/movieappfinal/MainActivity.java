package com.example.amrel_tahhan.movieappfinal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.example.amrel_tahhan.movieappfinal.adapter.MovieAdapter;
import com.example.amrel_tahhan.movieappfinal.database.MovieDatabase;
import com.example.amrel_tahhan.movieappfinal.model.MovieResponse;
import com.example.amrel_tahhan.movieappfinal.model.Movie;
import com.example.amrel_tahhan.movieappfinal.retrofit.MyWebService;
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
	@BindView(R.id.recycler_view)
	RecyclerView recyclerView;
	private MovieAdapter movieAdapter;
	private List<Movie> mItemList = new ArrayList<>();
	private String mSort = Constants.SORT_POPULAR;
	@BindView(R.id.toolbar)
	Toolbar toolbar;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
		if (isOnline()) {
			initRecyclerView(mItemList);
			requestData(mItemList);
		} else {
			Toast.makeText(this,"Please Check your Internet",Toast.LENGTH_SHORT).show();

		}
	}

	private void initRecyclerView(List<Movie> mItemList) {
		recyclerView.setVisibility(View.GONE);
		movieAdapter = new MovieAdapter(mItemList);
		recyclerView.setAdapter(movieAdapter);
		final StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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
		}
        else if (item.getItemId()==R.id.fav_button) {
			MovieDatabase mDb =  MovieDatabase.getInstanse(this);
             LiveData<List<Movie>> fItemList =  mDb.movieDao().loadAllMovies();
			fItemList.observe(this, new Observer<List<Movie>>() {
				@Override
				public void onChanged(@Nullable List<Movie> movies) {
					initRecyclerView(movies);

				}
			});
			return true;

		}

		else {
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
								mSort = (which == 0) ? Constants.SORT_POPULAR : Constants.SORT_HIGHEST_RATED;
								requestData(mItemList);
								dialog.dismiss();
							}
						});
		builder.create().show();
	}

	private void requestData(final List<Movie> mItemList) {
		Retrofit mRetrofit;
		MyWebService mService;
		mRetrofit = new Retrofit.Builder()
				.baseUrl(Constants.BASE_API_URL)
				.addConverterFactory(GsonConverterFactory.create(new Gson()))
				.build();

		mService = mRetrofit.create(MyWebService.class);
		mService.discoverMovie(Constants.MOVIEDB_APIKEY, mSort).enqueue(new Callback<MovieResponse>() {
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
}

