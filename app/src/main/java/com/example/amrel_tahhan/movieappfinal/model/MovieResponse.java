package com.example.amrel_tahhan.movieappfinal.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Amr El-Tahhan on 2/28/2018.
 */
@Entity
public class MovieResponse
{
    @Ignore
    private List<Movie> results;
    @PrimaryKey @NonNull
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


}