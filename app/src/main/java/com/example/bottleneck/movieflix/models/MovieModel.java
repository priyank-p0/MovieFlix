package com.example.bottleneck.movieflix.models;

/**
 * Created by priyankpatel on 1/12/16.
 */
public class MovieModel {




    private String poster_path;

    private int id;
    private int favPosition=0;


    public MovieModel() {
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }



    public  int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getFavPosition() {
        return favPosition;
    }

    public void setFavPosition(int favPosition) {
        this.favPosition = favPosition;
    }
}
