package com.example.krzysiek.brewerydb;


import android.content.Context;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.j256.ormlite.stmt.UpdateBuilder;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;

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
     * Zmienna context
     * Przechowuje aktualny motyw widoku
     */
    final Context context = this;


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

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
        addToFavoriteDetailsButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                DatabaseHelper dbHelper;
                dbHelper = (DatabaseHelper) OpenHelperManager.getHelper(context, DatabaseHelper.class);
                final RuntimeExceptionDao studDao = dbHelper.getStudRuntimeExceptionDao();
                UpdateBuilder<BeerDataBaseTemplate, String> updateBuilder = studDao.updateBuilder();
                BeerDataBaseTemplate wdt = new BeerDataBaseTemplate();

                if (buttonView.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Dodano do ulubionych", Toast.LENGTH_SHORT).show();
                    buttonView.setBackgroundResource(R.color.addToFavouriteButton);

                    try {

                        updateBuilder.updateColumnValue("beerFav", true);
                        updateBuilder.where().eq("beerName", nameBeer);
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


                    try {
                        updateBuilder.updateColumnValue("beerFav", false);
                        updateBuilder.where().eq("beerName", nameBeer);
                        updateBuilder.update();

                        Toast.makeText(context, "Usunięto z ulubionych", Toast.LENGTH_SHORT).show();
                        CardViewAdapter.favoriteBeers.remove(nameBeer);


                        Log.d("Favorite size= ", String.valueOf(CardViewAdapter.favoriteBeers.size()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }
            }
        });

    }

    /**
     * Metoda typu boolean onCreateOptionsMenu()
     * Odpowiada za wygląd menu.
     *
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);

        favoriteToggleButton = (ToggleButton) menu.findItem(R.id.favouriteBeersMenuItem).getActionView().findViewById(R.id.switch_show_protected);
        favoriteToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });


        return true;
    }

    /**
     * Metoda typu boolean onOptionsItemSelected
     * Odpowiada za akcję przycisków znajdujących się w Toolbarze.
     *
     * @param item
     * @return super.onOptionsItemSelected(item)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}


