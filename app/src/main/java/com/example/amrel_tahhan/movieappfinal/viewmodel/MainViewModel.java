package com.example.amrel_tahhan.movieappfinal.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;
import com.example.amrel_tahhan.movieappfinal.database.MovieDatabase;
import com.example.amrel_tahhan.movieappfinal.model.Movie;


import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<Movie>> moviesList;

    public MainViewModel(Application application) {
        super(application);
        MovieDatabase database = MovieDatabase.getInstanse(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        moviesList = database.movieDao().loadAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return moviesList;
    }
}
