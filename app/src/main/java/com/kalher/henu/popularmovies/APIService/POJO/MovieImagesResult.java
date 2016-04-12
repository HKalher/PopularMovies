package com.kalher.henu.popularmovies.APIService.POJO;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Kumar on 4/8/2016.
 */
@Parcel
public class MovieImagesResult {
    @SerializedName("backdrops")
    List<MovieImages> ImagesResult;

    public MovieImagesResult(){}

    public MovieImagesResult(List<MovieImages> ImagesResult){
        this.ImagesResult = ImagesResult;
    }
    public List<MovieImages> getImageResult(){
        return ImagesResult;
    }
}
