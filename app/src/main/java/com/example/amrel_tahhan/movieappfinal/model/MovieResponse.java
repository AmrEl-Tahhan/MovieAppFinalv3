package com.example.amrel_tahhan.movieappfinal.model;

import java.util.List;

/**
 * Created by Amr El-Tahhan on 2/28/2018.
 */

public class MovieResponse
{
    private List<Movie> results;
    private String page;
    private String total_pages;
    private String total_results;


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