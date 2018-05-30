package com.example.amrel_tahhan.movieappfinal.retrofit;

import com.example.amrel_tahhan.movieappfinal.model.Movie;
import com.example.amrel_tahhan.movieappfinal.model.Review;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by Amr El-Tahhan on 2/28/2018.
 */

public interface MyWebService {

        String MY_SERVICE_PAYLOAD = "myServicePayload";

        @GET("movie/popular")
        Call<Movie> discoverPopularMovie(@Query("api_key") String apiKey);

        @GET("movie/top_rated")
        Call<Movie> discoverTopRatedMovie(@Query("api_key") String apiKey);
        @GET("discover/movie")
        Call<Movie> discoverMovie(@Query("api_key") String apiKey, @Query("sort_by") String sortBy);

        @GET("movie/{id}/reviews")
        Call<Review> discoverReview(@Path("id") String id,@Query("api_key") String apiKey);

}
