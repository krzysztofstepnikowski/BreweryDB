package com.example.krzysiek.brewerydb.network;

import com.example.krzysiek.brewerydb.models.Brewery;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Krzysztof Stępnikowski on 2016-01-26.
 */
public interface ApiInterface {
    @GET("/search?hasImages=Y&type=beer&key=91f597f8c1b60ad0d7695849317d4d11&format=json")
    void getBeerReport(@Query("q") String name,Callback<Brewery> callback);



}