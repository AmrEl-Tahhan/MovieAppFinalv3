package com.example.amrel_tahhan.movieappfinal.model;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity
public class VideoResponse {

    @SerializedName("id")
    @Expose @PrimaryKey
    private int id;
    @SerializedName("results")
    @Expose @Ignore
    private List<Video> results = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }

}
