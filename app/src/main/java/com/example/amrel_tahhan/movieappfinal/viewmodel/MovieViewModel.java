package com.example.amrel_tahhan.movieappfinal.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.example.amrel_tahhan.movieappfinal.database.MovieDatabase;
import com.example.amrel_tahhan.movieappfinal.model.Movie;


public class MovieViewModel extends ViewModel {


    private LiveData<Movie> movie;

    public MovieViewModel(MovieDatabase db, int movieId) {
        movie = db.movieDao().loadMovieById(movieId);
    }

    public  LiveData<Movie> getMovie() {
        return movie;
    }
    }
