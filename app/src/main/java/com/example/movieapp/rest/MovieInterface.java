package com.example.movieapp.rest;

import com.example.movieapp.movieModel.MovieResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieInterface {
    @GET("/3/movie/{category}")
    Call<MovieResponse> getMovie(
            @Path("category") String category,
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") int page
    );
}
