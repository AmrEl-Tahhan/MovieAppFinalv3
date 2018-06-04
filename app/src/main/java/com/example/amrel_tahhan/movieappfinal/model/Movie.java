package com.example.amrel_tahhan.movieappfinal.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Amr El-Tahhan on 2/28/2018.
 */
public class Movie implements Parcelable {
    private String vote_average;

    private String backdrop_path;

    private String adult;

    private String id;

    private String title;

    private String overview;

    private String original_language;

    private String[] genre_ids;

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

    public String[] getGenre_ids() {
        return genre_ids;
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
        dest.writeStringArray(this.genre_ids);
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
        this.genre_ids = in.createStringArray();
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