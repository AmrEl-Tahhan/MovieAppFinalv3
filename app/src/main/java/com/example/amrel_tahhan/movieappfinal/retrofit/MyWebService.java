package com.example.amrel_tahhan.movieappfinal.retrofit;

import com.example.amrel_tahhan.movieappfinal.model.MovieResponse;
import com.example.amrel_tahhan.movieappfinal.model.ReviewResponse;
import com.example.amrel_tahhan.movieappfinal.model.VideoResponse;

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
    Call<MovieResponse> discoverPopularMovie(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MovieResponse> discoverTopRatedMovie(@Query("api_key") String apiKey);



    @GET("movie/{id}/reviews")
    Call<ReviewResponse> discoverReview(@Path("id") String id, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<VideoResponse> discoverTrailer(@Path("id") String id, @Query("api_key") String apiKey);

}
