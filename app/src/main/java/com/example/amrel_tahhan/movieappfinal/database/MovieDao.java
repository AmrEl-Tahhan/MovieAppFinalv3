package com.example.amrel_tahhan.movieappfinal.database;

import android.arch.persistence.room.Dao;
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
    @Query("Select * From MovieResponse ")
    List<Movie> gettAllFavouriteMovies() ;

    @Query("Select * From Movie Where id = :movieID")
    Movie getsingleMovie (String movieID);

    @Query("Select * From ReviewResponse Where id = :movieID")
    List<Review> getAllReviews (String movieID);

    @Query("Select * From MovieResponse Where id = :movieID")
    List<Video> getAllTrailers (String movieID);

    @Insert()
    void addMovie(Movie movie);

    @Insert()
    void addreviews (ReviewResponse reviewResponse);

    @Insert()
    void addTrailers (VideoResponse videoResponse);


}
