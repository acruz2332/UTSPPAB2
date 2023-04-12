package com.ppab.utsppab2.api;

public class ApiClient {
    public static final String BASE_URL_API = "https://api.api-ninjas.com/v1/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
