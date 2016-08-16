package com.kalher.henu.popularmovies.MovieDetail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kalher.henu.popularmovies.APIService.ApiClient;
import com.kalher.henu.popularmovies.APIService.MovieApiInterface;
import com.kalher.henu.popularmovies.APIService.POJO.MovieImages;
import com.kalher.henu.popularmovies.APIService.POJO.MovieImagesResult;
import com.kalher.henu.popularmovies.APIService.POJO.PopularMovies;
import com.kalher.henu.popularmovies.Adapters.BackdropAdapter;
import com.kalher.henu.popularmovies.R;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends Activity {
    private RecyclerView ImageList_RecyclerView;
    private HashMap<String,String> genreMap;
    private boolean expand;
    private TextView description;
    private TextView viewMore;
    private int descriptionLineCount;
    private MovieImagesResult MIR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);
        if(Build.VERSION.SDK_INT >= 21){
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(),R.color.statusBarColor));
        }

        setMovieDetail(savedInstanceState);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        try{
            outState.putParcelable("MovieImageList",Parcels.wrap(MIR));
        }catch (NullPointerException e){
            outState.putParcelable("MovieImageList",null);
        }
        super.onSaveInstanceState(outState);
    }

    private void setMovieDetail(Bundle savedInstanceState){
        Intent intent  = getIntent();
        PopularMovies movie = Parcels.unwrap(intent.getBundleExtra("movieObj").getParcelable("movie"));
        String mid = movie.getId();

        genrateGenreList();

        TextView movieTitle = (TextView) findViewById(R.id.movieTitle);
        movieTitle.setText(movie.getTitle());

        TextView movieDetail = (TextView) findViewById(R.id.movieDetail);
        movieDetail.setText(getMovieDetail(movie));

        TextView votes = (TextView) findViewById(R.id.votes);
        votes.setText("Votes " + movie.getVote_count());

        TextView ratings = (TextView) findViewById(R.id.ratings);
        ratings.setText("Ratings " + movie.getVote_average() + "/10");

        RatingBar ratingBar = (RatingBar) findViewById(R.id.movie_ratings);
        ratingBar.setRating(Float.parseFloat(movie.getVote_average())/2);
        // Setting color of rating stars
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.rating_stars), PorterDuff.Mode.SRC_ATOP);

        description = (TextView) findViewById(R.id.description);
        description.setText(movie.getOverview());
        description.setMaxLines(4);

        viewMore = (TextView) findViewById(R.id.viewMore);
        viewMore.setVisibility(View.INVISIBLE);

        description.post(new Runnable() {
            @Override
            public void run() {
                expand = false;
                descriptionLineCount = description.getLineCount();
                if(descriptionLineCount > 4) {
                    viewMore.setText("View More");
                    viewMore.setVisibility(View.VISIBLE);
                }
            }
        });

        viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expand) {
                    description.setMaxLines(Integer.MAX_VALUE);
                    viewMore.setText("View Less");
                    expand = true;
                } else {
                    description.setMaxLines(4);
                    viewMore.setText("View More");
                    expand = false;
                }
            }
        });

        // Setting backdrop images
        ImageList_RecyclerView = (RecyclerView) findViewById(R.id.backdrop_image_list);
        ImageList_RecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ImageList_RecyclerView.setLayoutManager(linearLayoutManager);

        if(savedInstanceState != null){
            MIR = Parcels.unwrap(savedInstanceState.getParcelable("MovieImageList"));
            if(MIR != null){
                BackdropAdapter ba = new BackdropAdapter(MIR.getImageResult().subList(0,10),getApplicationContext());
                ImageList_RecyclerView.setAdapter(ba);
            }else {
                APICall(mid);
            }
        }else {
            APICall(mid);
        }
    }
    private void APICall(String mid){
        MovieApiInterface service = ApiClient.getApiClient();
        Call<MovieImagesResult> call = service.getBackdrops(mid);

        call.enqueue(new Callback<MovieImagesResult>() {
            @Override
            public void onResponse(Call<MovieImagesResult> call, Response<MovieImagesResult> response) {
                MIR = response.body();
                List<MovieImages> MI = MIR.getImageResult().subList(0,10);
                BackdropAdapter ba = new BackdropAdapter(MI,getApplicationContext());
                ImageList_RecyclerView.setAdapter(ba);
            }

            @Override
            public void onFailure(Call<MovieImagesResult> call, Throwable t) {

            }
        });
    }
    private String getMovieDetail(PopularMovies movie){
        String detail;

        String release_date = movie.getRelease_date();
        release_date = release_date.substring(0,4);

        ArrayList<String> genreList = movie.getGenre_ids();
        String genre="";
        int len = genreList.size();
        for(int i=0;i<len;i++){
            genre+=genreMap.get(genreList.get(i))+"/";
        }

        detail = release_date + " . " + genre ;

        return detail;
    }

    private void genrateGenreList(){
        genreMap = new HashMap<String,String>();
        genreMap.put("28","Action");
        genreMap.put("12","Adventure");
        genreMap.put("16","Animation");
        genreMap.put("35","Comedy");
        genreMap.put("80","Crime");
        genreMap.put("99","Documentary");
        genreMap.put("18","Drama");
        genreMap.put("10751","Family");
        genreMap.put("14","Fantasy");
        genreMap.put("10769","Foreign");
        genreMap.put("36","History");
        genreMap.put("27","Horror");
        genreMap.put("10402","Music");
        genreMap.put("9648","Mystery");
        genreMap.put("10749","Romance");
        genreMap.put("878","Science Fiction");
        genreMap.put("10770","TV Movie");
        genreMap.put("53","Thriller");
        genreMap.put("10752","War");
        genreMap.put("37","Western");
    }
}
