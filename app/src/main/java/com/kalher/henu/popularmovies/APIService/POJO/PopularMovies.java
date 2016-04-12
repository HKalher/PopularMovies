package com.kalher.henu.popularmovies.APIService.POJO;

import org.parceler.Parcel;

import java.util.ArrayList;


@Parcel
public class PopularMovies {
    public static final String IMAGE_PATH = "http://image.tmdb.org/t/p/w342";
    private String poster_path;
    private String title;
    private String overview;
    private String backdrop_Path;
    private String id;
    private String vote_count;
    private String vote_average;
    private String release_date;
    private ArrayList<String> genre_ids;

    public PopularMovies(){

    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return IMAGE_PATH + poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdrop_path() { return IMAGE_PATH  + backdrop_Path; }

    public String getId(){ return id; }

    public String getVote_count(){
        return vote_count;
    }

    public String getVote_average(){
        return vote_average;
    }

    public String getRelease_date(){
        return release_date;
    }

    public ArrayList<String> getGenre_ids(){
        return genre_ids;
    }
}
