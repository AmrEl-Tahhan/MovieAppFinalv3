package com.example.amrel_tahhan.movieappfinal.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Amr El-Tahhan on 2/28/2018.
 */
public class MovieResponse implements Parcelable {
    private List<Movie> results;
    private String page;
    private String total_pages;
    private String total_results;


    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
    }

    public List<Movie> getResults ()
    {
        return results;
    }
    public String getPage ()
    {
        return page;
    }
    public String getTotal_pages ()
    {
        return total_pages;
    }
    public String getTotal_results ()
    {
        return total_results;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.results);
        dest.writeString(this.page);
        dest.writeString(this.total_pages);
        dest.writeString(this.total_results);
    }

    public MovieResponse() {
    }

    protected MovieResponse(Parcel in) {
        this.results = in.createTypedArrayList(Movie.CREATOR);
        this.page = in.readString();
        this.total_pages = in.readString();
        this.total_results = in.readString();
    }

    public static final Parcelable.Creator<MovieResponse> CREATOR = new Parcelable.Creator<MovieResponse>() {
        @Override
        public MovieResponse createFromParcel(Parcel source) {
            return new MovieResponse(source);
        }

        @Override
        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };
}