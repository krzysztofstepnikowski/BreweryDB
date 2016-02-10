package com.example.krzysiek.brewerydb.network;

import com.example.krzysiek.brewerydb.models.Brewery;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * @author Krzysztof Stępnikowski
 *         łączy się z serwisem API i pobiera ich nazwy.
 * @interface ApiInterface
 */
public interface ApiInterface {
    @GET("/search?hasImages=Y&type=beer&key=91f597f8c1b60ad0d7695849317d4d11&format=json")
    void getBeerReport(@Query("q") String name, Callback<Brewery> callback);


}