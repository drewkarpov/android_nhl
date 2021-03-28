package com.example.nhl.network;

import com.example.nhl.model.User;
import com.example.nhl.model.UserDto;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JSONUserApi {
    @PUT("/player/{id}/change")
     Call<JsonObject> changeUser(@Body User user, @Path("id") String id);

    @GET("/players")
     Call<List<User>> getUsers();

    @POST("/add/player")
    Call<JsonObject> postUser(@Body UserDto user);

    @DELETE("/player/{id}/delete")
    Call<JsonObject> deleteUser(@Path("id") String id);

    @GET("/users/statistic")
    Call<JsonObject> usersStatistic();

}

