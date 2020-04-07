package com.example.cryptobag.com.example.cryptobag.Entities;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CoinService {
    @GET("api/tickers/")
    Call<CoinLoreResponse> getCoins();
}
