package com.example.nhl.network;

import com.example.nhl.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JSONUserApi {
    @PUT("/user/change/{id}")
     Call<List<User>> changeUser(@Body User user, @Path("id") String id);

    @GET("/users")
     Call<List<User>> searchUsers(@Query("value")  String value);

    @GET("/users/")
     Call<List<User>> getUsers();

    @POST("/user/add")
    Call<User> postUser(@Body User user);

    @DELETE("/user/delete/{id}")
    Call<List<User>> deleteUser(@Path("id") String id);



}

