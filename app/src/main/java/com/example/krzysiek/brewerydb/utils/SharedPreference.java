package com.example.krzysiek.brewerydb.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.krzysiek.brewerydb.models.Datum;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Krzysztof Stępnikowski on 2016-01-31.
 */
public class SharedPreference {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Beers_Favorite";

    public SharedPreference() {
        super();
    }

    public void saveFavourites(Context context, List<Datum> favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);
        editor.commit();
    }

    public ArrayList<Datum> loadFavorites(Context context) {
        SharedPreferences settings;
        List<Datum> favorites;

        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();

            Datum[] favoriteItems = gson.fromJson(jsonFavorites, Datum[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Datum>(favorites);
        } else {
            return null;
        }

        return (ArrayList<Datum>) favorites;
    }

    public void addFavourite(Context context, Datum beerCardViewItem) {
        ArrayList<Datum> favorites = loadFavorites(context);

        if (favorites == null) {
            favorites = new ArrayList<Datum>();
        }

        favorites.add(beerCardViewItem);

        saveFavourites(context, favorites);
    }

    public void removeFavorite(Context context, Datum beerCardViewItem) {
        ArrayList<Datum> favorites = loadFavorites(context);

        if (favorites != null) {
            favorites.remove(beerCardViewItem);
            saveFavourites(context, favorites);
        }
    }


}
