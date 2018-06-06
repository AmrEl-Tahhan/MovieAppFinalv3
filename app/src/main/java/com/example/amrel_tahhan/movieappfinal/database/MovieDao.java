package com.example.amrel_tahhan.movieappfinal.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.amrel_tahhan.movieappfinal.model.Movie;
import com.example.amrel_tahhan.movieappfinal.model.Review;
import com.example.amrel_tahhan.movieappfinal.model.ReviewResponse;
import com.example.amrel_tahhan.movieappfinal.model.Video;
import com.example.amrel_tahhan.movieappfinal.model.VideoResponse;

import java.util.List;

/**
 * Created by Amr El-Tahhan on 6/4/2018.
 */
@Dao
public interface MovieDao {


    @Query("SELECT * FROM favorite_movies ORDER BY title")
    LiveData<List<Movie>> loadAllMovies();

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * FROM favorite_movies WHERE id = :id")
    LiveData<Movie> loadMovieById(int id);

    @Query("SELECT * FROM favorite_movies WHERE id = :id")
    Movie loadMovieById2(int id);
}



