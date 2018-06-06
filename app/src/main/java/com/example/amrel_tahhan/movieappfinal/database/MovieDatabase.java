package com.example.amrel_tahhan.movieappfinal.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.amrel_tahhan.movieappfinal.model.Movie;
import com.example.amrel_tahhan.movieappfinal.model.MovieResponse;
import com.example.amrel_tahhan.movieappfinal.model.Review;
import com.example.amrel_tahhan.movieappfinal.model.ReviewResponse;
import com.example.amrel_tahhan.movieappfinal.model.Video;
import com.example.amrel_tahhan.movieappfinal.model.VideoResponse;

/**
 * Created by Amr El-Tahhan on 6/4/2018.
 */
@Database(entities = {Movie.class, Review.class, Video.class}
        , version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "MovieDB";
    private static MovieDatabase Instanse = null;

    public static MovieDatabase getInstanse(Context context) {
        if (Instanse == null) {
            synchronized (MovieDatabase.class) {
                Instanse = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        return Instanse;
    }

    public abstract MovieDao movieDao() ;

}
