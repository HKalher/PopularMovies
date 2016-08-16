package com.kalher.henu.popularmovies;

import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kalher.henu.popularmovies.APIService.ApiClient;
import com.kalher.henu.popularmovies.APIService.MovieApiInterface;
import com.kalher.henu.popularmovies.APIService.POJO.PopularMovieResult;
import com.kalher.henu.popularmovies.Adapters.MyAdapter;

import org.parceler.Parcels;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityFragment extends Fragment {

    private RecyclerView recyclerView;
    private PopularMovieResult result;
    private Toast toast;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        toast = Toast.makeText(getContext(),"Fetching data from server",Toast.LENGTH_SHORT);
        recyclerView = (RecyclerView) getView().findViewById(R.id.cardGrid);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),getColumns());
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        if(savedInstanceState != null){
            PopularMovieResult movieResult = Parcels.unwrap(savedInstanceState.getParcelable("parced_movieList"));
            if(movieResult != null){
                MyAdapter adapter = new MyAdapter(movieResult.getMovieList(), getContext());
                recyclerView.setAdapter(adapter);
            }else {
                APICall("popular_movies");
            }
        }else {
            APICall("popular_movies");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        try{
            outState.putParcelable("parced_movieList",Parcels.wrap(new PopularMovieResult(result.getMovieList())));
        }catch (NullPointerException e){
            outState.putParcelable("parced_movieList",Parcels.wrap(new PopularMovieResult(null)));
        }
        super.onSaveInstanceState(outState);
    }

    public void APICall(String load){
        toast.show();
        MovieApiInterface service = ApiClient.getApiClient();
        Call<PopularMovieResult> call;
        switch (load){
            case ("popular_movies"):
                call = service.getPopularMovieResult();
                break;
            case ("highest_rated"):
                call = service.getHighestRatedMovieResult();
                break;
            default:
                call = service.getPopularMovieResult();
        }

        call.enqueue(new Callback<PopularMovieResult>() {
            @Override
            public void onResponse(Call<PopularMovieResult> call, Response<PopularMovieResult> response) {
                result = response.body();
                MyAdapter adapter = new MyAdapter(result.getMovieList(), getContext());
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<PopularMovieResult> call, Throwable t) {

            }
        });
    }

    public int getColumns(){
        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            return 3;
        }else {
            return 2;
        }
    }

}
