package com.example.videoreels.api;

import com.example.videoreels.model.videoreels.VideoModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface apiInterface {

    @GET("api/videos/")
    Call<VideoModel> getReelData(@Query("key") String apiKey,@Query(value = "q",encoded = true) String query );

}
