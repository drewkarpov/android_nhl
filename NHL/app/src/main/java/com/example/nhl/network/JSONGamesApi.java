package com.example.nhl.network;

import com.example.nhl.model.Game;
import com.example.nhl.model.GameDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface JSONGamesApi {

    @GET("/player/{id}/games")
    Call<List<Game>> getScoresById(@Path("id") String userId);

    @POST("/player/{id}/game/add")
    Call<Game> setScore(@Path("id") String id, @Body GameDto game);


}

