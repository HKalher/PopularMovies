package com.kalher.henu.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kalher.henu.popularmovies.APIService.POJO.PopularMovies;
import com.kalher.henu.popularmovies.MovieDetail.MovieDetailActivity;
import com.kalher.henu.popularmovies.R;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<PopularMovies> MovieList;
    private Context mContext ;  // it is advisable to add a context variable as a class field

    public MyAdapter(){}

    public MyAdapter(List<PopularMovies> MovieList, Context mContext) {
        this.MovieList = MovieList;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        int listsize =(MovieList == null) ? 0 : MovieList.size();
        return listsize;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final PopularMovies movieObj = MovieList.get(position);
        Picasso.with(mContext).load(movieObj.getPoster_path()).into(holder.imageView);
        holder.title.setText(MovieList.get(position).getTitle());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        protected TextView title;
        protected ImageView imageView;
        public MyViewHolder(View v){
            super(v);
            imageView = (ImageView) v.findViewById(R.id.movieImage);
            title = (TextView) v.findViewById(R.id.movieName);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartDetailActivity(v,getAdapterPosition());
                }
            });
        }
    }

    public void StartDetailActivity(View view,int position){
        Intent detailActivityIntent = new Intent(mContext, MovieDetailActivity.class);
        detailActivityIntent.putExtra("current_position",position);
        Bundle movieObj = new Bundle();
        movieObj.putParcelable("movie", Parcels.wrap(MovieList.get(position)));
        detailActivityIntent.putExtra("movieObj",movieObj);

        mContext.startActivity(detailActivityIntent);
    }
}
