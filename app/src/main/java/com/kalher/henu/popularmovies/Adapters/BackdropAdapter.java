package com.kalher.henu.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kalher.henu.popularmovies.APIService.POJO.MovieImages;
import com.kalher.henu.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BackdropAdapter  extends RecyclerView.Adapter<BackdropAdapter.BackdropViewHolder>{

    private List<MovieImages> BackdropList;
    private Context mContext;

    public BackdropAdapter(List<MovieImages> BackdropList, Context mContext){
        this.BackdropList = BackdropList;
        this.mContext = mContext;
    }

    @Override
    public BackdropViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View imageView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.backdrop_image_view, parent, false);
        return new BackdropViewHolder(imageView);
    }

    @Override
    public int getItemCount() {
        return BackdropList.size();
    }

    @Override
    public void onBindViewHolder(BackdropViewHolder holder, int position) {
        MovieImages mImages = BackdropList.get(position);
        String path = mImages.getImagePath();
        Picasso.with(mContext).load(path).into(holder.imageView);
    }

    public class BackdropViewHolder extends RecyclerView.ViewHolder{
        protected ImageView imageView;
        public BackdropViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.backdrop_imageView);
        }
    }
}
