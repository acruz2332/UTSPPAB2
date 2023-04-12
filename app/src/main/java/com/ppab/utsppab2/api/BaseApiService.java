package com.ppab.utsppab2.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    @Headers({"X-Api-Key: 1akeCSbWzxVeiknRMSEdyw==d4fZBmW9rfTRFE4V"})
    @GET("geocoding")
    Call<List<MapData>> mapReqApi(@Query("city") String city,
                                  @Query("country") String country);

}
