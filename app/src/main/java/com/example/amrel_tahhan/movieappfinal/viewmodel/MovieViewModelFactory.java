package com.example.amrel_tahhan.movieappfinal.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import com.example.amrel_tahhan.movieappfinal.database.MovieDatabase;


public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    MovieDatabase db;
    String movieid;

    public MovieViewModelFactory(MovieDatabase db, String movieid) {
        this.db = db;
        this.movieid = movieid;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new MovieViewModel(db, movieid);
    }
}
