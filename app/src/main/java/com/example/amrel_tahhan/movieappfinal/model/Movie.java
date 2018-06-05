package com.example.amrel_tahhan.movieappfinal.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by Amr El-Tahhan on 2/28/2018.
 */

@Entity (tableName = "favorite_movies")
public class Movie implements Parcelable {
    private String vote_average;

    private String backdrop_path;

    private String adult;
@PrimaryKey @NonNull
    private String id;

    private String title;

    private String overview;

    private String original_language;


    private String release_date;

    private String original_title;

    private String vote_count;

    private String poster_path;

    private String video;

    private String popularity;

    public String getVote_average() {
        return vote_average;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getAdult() {
        return adult;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getOriginal_language() {
        return original_language;
    }


    public String getRelease_date() {
        return release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getVote_count() {
        return vote_count;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getVideo() {
        return video;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public Movie() {
    }

    public Movie(String vote_average, String backdrop_path,  @NonNull String id, String title, String overview, String release_date, String original_title, String vote_count, String poster_path, String video) {
        this.vote_average = vote_average;
        this.backdrop_path = backdrop_path;
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.original_title = original_title;
        this.vote_count = vote_count;
        this.poster_path = poster_path;
        this.video = video;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.vote_average);
        dest.writeString(this.backdrop_path);
        dest.writeString(this.adult);
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.original_language);
        dest.writeString(this.release_date);
        dest.writeString(this.original_title);
        dest.writeString(this.vote_count);
        dest.writeString(this.poster_path);
        dest.writeString(this.video);
        dest.writeString(this.popularity);
    }



    protected Movie(Parcel in) {
        this.vote_average = in.readString();
        this.backdrop_path = in.readString();
        this.adult = in.readString();
        this.id = in.readString();
        this.title = in.readString();
        this.overview = in.readString();
        this.original_language = in.readString();
        this.release_date = in.readString();
        this.original_title = in.readString();
        this.vote_count = in.readString();
        this.poster_path = in.readString();
        this.video = in.readString();
        this.popularity = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}