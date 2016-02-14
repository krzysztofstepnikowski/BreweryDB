package com.example.krzysiek.brewerydb;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private TextView voltageBeerTextViewDetails;

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


        final String nameBeer = extras.getString("nameBeer");
        getSupportActionBar().setTitle(nameBeer);

        dbHelper = (DatabaseHelper) OpenHelperManager.getHelper(context, DatabaseHelper.class);
        final RuntimeExceptionDao studDao = dbHelper.getStudRuntimeExceptionDao();
        QueryBuilder<BeerDataBaseTemplate, String> queryBuilder = studDao.queryBuilder();

        try {
            List<BeerDataBaseTemplate> beerLikeListFavorites = queryBuilder.where().eq("BEERDATABASETEMPLATE_TABLE_BEER_FAVORITE", true).and()
                    .eq("BEERDATABASETEMPLATE_TABLE_BEER_NAME", nameBeer).query();

            final List<BeerDataBaseTemplate> list = queryBuilder.where().eq("BEERDATABASETEMPLATE_TABLE_BEER_NAME", nameBeer).query();


            if (!list.isEmpty()) {
                Log.d("Lista", list.get(0).getBeerName());

            }

            imageViewBeerDetails = (ImageView) findViewById(R.id.imageViewBeerDetails);
            Picasso.with(this)
                    .load(list.get(0).getBeerImageLarge().toString())
                    .placeholder(R.drawable.icon_beer)
                    .into(imageViewBeerDetails);


            voltageBeerTextViewDetails = (TextView) findViewById(R.id.abvBeerTextViewDeitals);

            if (!list.get(0).getBeerVoltage().equals("Brak danych")) {
                voltageBeerTextViewDetails.setText(list.get(0).getBeerVoltage() + "%");
            } else {
                voltageBeerTextViewDetails.setText(list.get(0).getBeerVoltage());
            }

            descriptionBeerTextViewDetails = (TextView) findViewById(R.id.descriptionBeerDetailsTextView);
            descriptionBeerTextViewDetails.setText(list.get(0).getBeerDescription());

            addToFavoriteDetailsButton = (ToggleButton) findViewById(R.id.addToFavouriteDetailsButton);


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
                                updateBuilder.where().eq("BEERDATABASETEMPLATE_TABLE_BEER_NAME", list.get(0).getBeerName());
                                updateBuilder.update();


                                Toast.makeText(context, "Usunięto z ulubionych", Toast.LENGTH_SHORT).show();
                                CardViewAdapter.favoriteBeers.remove(list.get(0).getBeerName());


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
                                updateBuilder.where().eq("BEERDATABASETEMPLATE_TABLE_BEER_NAME", list.get(0).getBeerName());
                                updateBuilder.update();

                                Toast.makeText(context, "Dodano do ulubionych", Toast.LENGTH_SHORT).show();


                                if (nameBeer != null) {
                                    CardViewAdapter.favoriteBeers.add(list.get(0).getBeerName());
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
                                updateBuilder.where().eq("BEERDATABASETEMPLATE_TABLE_BEER_NAME", list.get(0).getBeerName());
                                updateBuilder.update();

                                Toast.makeText(context, "Dodano do ulubionych", Toast.LENGTH_SHORT).show();


                                if (list.get(0).getBeerName() != null) {
                                    CardViewAdapter.favoriteBeers.add(list.get(0).getBeerName());
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
                                updateBuilder.where().eq("BEERDATABASETEMPLATE_TABLE_BEER_NAME", list.get(0).getBeerName());
                                updateBuilder.update();


                                Toast.makeText(context, "Usunięto z ulubionych", Toast.LENGTH_SHORT).show();
                                CardViewAdapter.favoriteBeers.remove(list.get(0).getBeerName());


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

        Intent intent = new Intent(AboutBeerActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.aboutApp) {

            final SpannableString serviceUrlSpannableString = new SpannableString("http://www.brewerydb.com/");
            final TextView textView = new TextView(this);
            textView.setText("BreweryDB\nVersion 1.0\n\nCopyright ⓒ 2016\nKrzysztof Stępnikowski\n\n" +
                    "Aplikacja zawiera dane o piwach pobranych z serwisu " + serviceUrlSpannableString + "\n\nWszelkie prawa zastrzeżone.");
            textView.setAutoLinkMask(RESULT_OK);
            textView.setMovementMethod(LinkMovementMethod.getInstance());

            Linkify.addLinks(serviceUrlSpannableString, Linkify.WEB_URLS);
            AlertDialog alertDialog = new AlertDialog.Builder(AboutBeerActivity.this)
                    .setTitle("O programie")
                    .setCancelable(false)
                    .setIcon(R.drawable.icon_beer)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setView(textView).show();
            alertDialog.show();


        }

        return super.onOptionsItemSelected(item);
    }
}




