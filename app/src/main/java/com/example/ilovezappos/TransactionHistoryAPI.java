package com.example.ilovezappos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TransactionHistoryAPI {

    @GET("api/v2/transactions/btcusd/")
    Call<List<Transaction>> getTransaction();

    @GET("api/v2/ticker_hour/btcusd/")
    Call<PriceAlert> getPriceCheck();
}
