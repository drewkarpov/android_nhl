package com.example.nhl;

import com.example.nhl.model.Score;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface JSONScoreApi {

    @GET("/user/{id}/scores")
    Call<List<Score>> getScoresById(@Path("id") String userId);

    @POST("/user/{id}/score/add")
    Call<List<Score>> setScore(@Path("id") int id, @Body Score score);


}

