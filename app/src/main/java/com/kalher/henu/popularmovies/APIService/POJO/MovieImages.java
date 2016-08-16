package com.kalher.henu.popularmovies.APIService.POJO;


import org.parceler.Parcel;

@Parcel
public class MovieImages {
    private String Base_Image_Path = "http://image.tmdb.org/t/p/w780";
    private String file_path;

    public MovieImages(){}
    public MovieImages(String file_path){
        this.file_path = file_path;
    }
    public String  getImagePath(){
            return Base_Image_Path + file_path;
        }
}
