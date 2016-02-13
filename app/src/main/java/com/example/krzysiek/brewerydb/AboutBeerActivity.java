package com.example.krzysiek.brewerydb;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.example.krzysiek.brewerydb.ormlite.BeerDataBaseTemplate;
import com.example.krzysiek.brewerydb.ormlite.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Krzysztof Stępnikowski
 *         Przedstawia szczegóły piwa.
 * @class AboutBeerActivity
 */
public class AboutBeerActivity extends AppCompatActivity {

    /**
     * Zmienna imageViewBeerDetails
     * Obiekt klasy ImageView
     * Wyświetla zdjęcię piwa w rozmiarze: large
     */
    private ImageView imageViewBeerDetails;

    /**
     * Zmienna abvBeerTextViewDetails
     * Obiekt klasy TextView
     * Wyświetla zawartość objętości alkoholu w piwie
     */
    private TextView abvBeerTextViewDetails;

    /**
     * Zmienna descriptionBeerTextViewDetails
     * Obiekt klasy TextView
     * Wyświetla opis piwa
     */
    private TextView descriptionBeerTextViewDetails;

    /**
     * Zmienna addToFavoriteDetailsButton
     * Obiekt klasy ToggleButton
     * Odpowiada za akcję dodawania/usuwania piwa z "ulubionych"
     */
    private ToggleButton addToFavoriteDetailsButton;

    /**
     * Zmienna favoriteToggleButton
     * Obiekt klasy ToggleButton
     * Jest to przycisk znajdujący się w Toolbarze, który ukazuje listę piw dodanych do ulubionych
     */
    private ToggleButton favoriteToggleButton;

    /**
     * Zmienna mRecyclerView
     * Obiekt klasy RecyclerView
     */
    private RecyclerView mRecyclerView;

    /**
     * Zmienna adapter2
     * Obiekt klasy CardViewAdapter
     */
    private CardViewAdapter adapter2;

    /**
     * Zmienna context
     * Przechowuje aktualny motyw widoku
     */
    private Context context = this;

    /**
     * Zmienna dbHelper
     * Obiekt klasy DatabaseHelper
     * Odpowiada za połączenie z lokalną bazą danych.
     */
    private DatabaseHelper dbHelper;


    /**
     * Zmienna isBeerLikeFavorite typu boolean.
     */
    boolean isBeerLikeFavorite = false;



    /**
     * Metoda onCreate()
     * Tworzy widok drugiej aktywności.
     * Przedstawia szczegóły na temat wybranego piwa.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_beer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();


        String imageBeer = extras.getString("imageBeer");
        final String nameBeer = extras.getString("nameBeer");
        String abvBeer = extras.getString("abvBeer");
        String descriptionBeer = extras.getString("descriptionBeer");


        getSupportActionBar().setTitle(nameBeer);

        imageViewBeerDetails = (ImageView) findViewById(R.id.imageViewBeerDetails);
        Picasso.with(imageViewBeerDetails.getContext())
                .load(imageBeer)
                .into(imageViewBeerDetails);


        abvBeerTextViewDetails = (TextView) findViewById(R.id.abvBeerTextViewDeitals);

        if (!abvBeer.equals("Brak danych")) {
            abvBeerTextViewDetails.setText(abvBeer + "%");
        } else {
            abvBeerTextViewDetails.setText(abvBeer);
        }

        descriptionBeerTextViewDetails = (TextView) findViewById(R.id.descriptionBeerDetailsTextView);
        descriptionBeerTextViewDetails.setText(descriptionBeer);

        addToFavoriteDetailsButton = (ToggleButton) findViewById(R.id.addToFavouriteDetailsButton);


        dbHelper = (DatabaseHelper) OpenHelperManager.getHelper(context, DatabaseHelper.class);
        final RuntimeExceptionDao studDao = dbHelper.getStudRuntimeExceptionDao();
        QueryBuilder<BeerDataBaseTemplate, String> queryBuilder = studDao.queryBuilder();

        try {
            List<BeerDataBaseTemplate> beerLikeListFavorites = queryBuilder.where().eq("BEERDATABASETEMPLATE_TABLE_BEER_FAVORITE", true).and()
                    .eq("BEERDATABASETEMPLATE_TABLE_BEER_NAME", nameBeer).query();


            if (!beerLikeListFavorites.isEmpty()) {
                isBeerLikeFavorite = true;
            } else {
                isBeerLikeFavorite = false;
            }


            if (isBeerLikeFavorite == true) {

                addToFavoriteDetailsButton.setBackgroundResource(R.color.addToFavouriteButton);
                addToFavoriteDetailsButton.setText("Usuń z ulubionych");
                addToFavoriteDetailsButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (buttonView.isChecked()) {
                            buttonView.setBackgroundResource(R.color.colorPrimaryDark);
                            addToFavoriteDetailsButton.setTextOn("Dodaj do ulubionych");

                            UpdateBuilder<BeerDataBaseTemplate, String> updateBuilder = studDao.updateBuilder();
                            try {
                                updateBuilder.updateColumnValue("BEERDATABASETEMPLATE_TABLE_BEER_FAVORITE", false);
                                updateBuilder.where().eq("BEERDATABASETEMPLATE_TABLE_BEER_NAME", nameBeer);
                                updateBuilder.update();


                                Toast.makeText(context, "Usunięto z ulubionych", Toast.LENGTH_SHORT).show();
                                CardViewAdapter.favoriteBeers.remove(nameBeer);


                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            Log.d("Favorite size= ", String.valueOf(CardViewAdapter.favoriteBeers.size()));
                        } else {
                            buttonView.setBackgroundResource(R.color.addToFavouriteButton);
                            addToFavoriteDetailsButton.setTextOff("Usuń z ulubionych");

                            UpdateBuilder<BeerDataBaseTemplate, String> updateBuilder = studDao.updateBuilder();

                            try {

                                updateBuilder.updateColumnValue("BEERDATABASETEMPLATE_TABLE_BEER_FAVORITE", true);
                                updateBuilder.where().eq("BEERDATABASETEMPLATE_TABLE_BEER_NAME", nameBeer);
                                updateBuilder.update();

                                Toast.makeText(context, "Dodano do ulubionych", Toast.LENGTH_SHORT).show();


                                if (nameBeer != null) {
                                    CardViewAdapter.favoriteBeers.add(nameBeer);
                                } else {
                                    CardViewAdapter.favoriteBeers.add("Brak danych");
                                }


                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            Log.d("Favorite size= ", String.valueOf(CardViewAdapter.favoriteBeers.size()));
                        }
                    }

                });
            } else {

                addToFavoriteDetailsButton.setBackgroundResource(R.color.colorPrimaryDark);
                addToFavoriteDetailsButton.setText("Dodaj do ulubionych");
                addToFavoriteDetailsButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (buttonView.isChecked()) {
                            buttonView.setBackgroundResource(R.color.addToFavouriteButton);
                            addToFavoriteDetailsButton.setTextOn("Usuń z ulubionych");

                            UpdateBuilder<BeerDataBaseTemplate, String> updateBuilder = studDao.updateBuilder();

                            try {

                                updateBuilder.updateColumnValue("BEERDATABASETEMPLATE_TABLE_BEER_FAVORITE", true);
                                updateBuilder.where().eq("BEERDATABASETEMPLATE_TABLE_BEER_NAME", nameBeer);
                                updateBuilder.update();

                                Toast.makeText(context, "Dodano do ulubionych", Toast.LENGTH_SHORT).show();


                                if (nameBeer != null) {
                                    CardViewAdapter.favoriteBeers.add(nameBeer);
                                } else {
                                    CardViewAdapter.favoriteBeers.add("Brak danych");
                                }


                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            Log.d("Favorite size= ", String.valueOf(CardViewAdapter.favoriteBeers.size()));
                        } else {
                            buttonView.setBackgroundResource(R.color.colorPrimaryDark);
                            addToFavoriteDetailsButton.setTextOff("Dodaj do ulubionych");

                            UpdateBuilder<BeerDataBaseTemplate, String> updateBuilder = studDao.updateBuilder();
                            try {
                                updateBuilder.updateColumnValue("BEERDATABASETEMPLATE_TABLE_BEER_FAVORITE", false);
                                updateBuilder.where().eq("BEERDATABASETEMPLATE_TABLE_BEER_NAME", nameBeer);
                                updateBuilder.update();


                                Toast.makeText(context, "Usunięto z ulubionych", Toast.LENGTH_SHORT).show();
                                CardViewAdapter.favoriteBeers.remove(nameBeer);


                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            Log.d("Favorite size= ", String.valueOf(CardViewAdapter.favoriteBeers.size()));


                        }
                    }
                });


            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(AboutBeerActivity.this,HomeActivity.class);
        startActivity(intent);
    }
}




