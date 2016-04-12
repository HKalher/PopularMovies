package com.kalher.henu.popularmovies.APIService;

import com.kalher.henu.popularmovies.APIService.POJO.MovieImagesResult;
import com.kalher.henu.popularmovies.APIService.POJO.PopularMovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieApiInterface {
    @GET("movie/popular")
    Call<PopularMovieResult> getPopularMovieResult();

    @GET("movie/top_rated")
    Call<PopularMovieResult> getHighestRatedMovieResult();

    @GET("movie/{movie_id}/images")
    Call<MovieImagesResult> getBackdrops(@Path("movie_id") String id);
}
