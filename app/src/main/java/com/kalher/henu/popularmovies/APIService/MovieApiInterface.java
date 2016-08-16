package com.kalher.henu.popularmovies.APIService;

import com.kalher.henu.popularmovies.APIService.POJO.MovieImagesResult;
import com.kalher.henu.popularmovies.APIService.POJO.PopularMovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieApiInterface {    // This interface is used to define all our API endpoints
    @GET("movie/popular")
    Call<PopularMovieResult> getPopularMovieResult();  // PopularMovieResult is a class, getMovieResult a new method declared here, and will be called from onCreate in MainActivity to call the api

    @GET("movie/top_rated")
    Call<PopularMovieResult> getHighestRatedMovieResult();

    @GET("movie/{movie_id}/images")
    Call<MovieImagesResult> getBackdrops(@Path("movie_id") String id);
}
/** Here, each endpoint specifies an annotation of the HTTP method
  (GET, POST, PUT, DELETE etc.). And each of these methods will be
   used to initiate the network call **/
