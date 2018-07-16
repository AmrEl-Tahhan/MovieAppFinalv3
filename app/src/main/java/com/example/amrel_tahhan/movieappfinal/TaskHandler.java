package com.example.amrel_tahhan.movieappfinal;

import com.example.amrel_tahhan.movieappfinal.model.Movie;

import java.util.List;

/**
 * Created by Amr El-Tahhan on 6/19/2018.
 */

public interface TaskHandler {

        void onComplete(List<Movie> data);

}
