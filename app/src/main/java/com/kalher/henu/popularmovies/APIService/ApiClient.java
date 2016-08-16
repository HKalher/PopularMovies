package com.kalher.henu.popularmovies.APIService;


import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static final String BaseUrl = "https://api.themoviedb.org/3/";

    public static MovieApiInterface getApiClient(){

        // Api key interceptor
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                HttpUrl originalHttpUrl = originalRequest.url();
                HttpUrl mUrl = originalHttpUrl.newBuilder()
                        .addQueryParameter("api_key","b99f54d84b337288971f1cca257d4a7f")
                        .build();
                Request.Builder requestBuilder  = originalRequest.newBuilder()
                        .url(mUrl);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieApiInterface service = retrofit.create(MovieApiInterface.class);
        return service;
    }
}
