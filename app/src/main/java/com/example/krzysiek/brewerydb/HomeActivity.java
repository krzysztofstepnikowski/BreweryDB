package com.example.krzysiek.brewerydb;


import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;


import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;


import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.example.krzysiek.brewerydb.models.Brewery;
import com.example.krzysiek.brewerydb.models.Datum;
import com.example.krzysiek.brewerydb.network.ApiInterface;
import com.example.krzysiek.brewerydb.ormlite.BeerDataBaseTemplate;
import com.example.krzysiek.brewerydb.ormlite.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Krzysztof Stępnikowski
 *         Przedstawia główny ekran aplikacji
 * @class HomeActivity
 */
public class HomeActivity extends AppCompatActivity {

    /**
     * Lista przechowująca nazwy piw
     */
    private ArrayList<String> simpleBeerList = new ArrayList<String>();

    /**
     * Lista przechowująca zdjęcia piw o rozmiarze: medium
     */
    public static ArrayList<String> beerPhotoMediumUrlsList = new ArrayList<String>();

    /**
     * Lista przechowująca zdjęcia piw o rozmiarze: large
     */
    public static ArrayList<String> beerPhotoLargeUrlsList = new ArrayList<String>();

    /**
     * Lista przechowująca dane o objętości alkoholu w piwie
     */
    public static ArrayList<String> beerABVList = new ArrayList<String>();

    /**
     * Lista przechowująca opis szczegółowy piwa
     */
    public static ArrayList<String> beerDescriptionList = new ArrayList<String>();

    /**
     * Lista przechowująca dane o piwach w trybie offline.
     */
    ArrayList<String> offlineBeers = new ArrayList<String>();

    /**
     * Zmienna statyczna BASE_API_URL
     * przechowuje link do serwisu API
     */
    public static final String BASE_API_URL = "https://api.brewerydb.com/v2";


    /**
     * Zmienna context
     * Przechowuje aktualny motyw widoku
     */
    final Context context = this;

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
     * Zmienna favoriteBeerAdapter
     * Obiekt klasy CardViewAdapter
     */
    private CardViewAdapter favoriteBeerAdapter;
    /**
     * Zmienna progress
     * Obiekt klasy ProgressDialog
     * Wyświetla dialog informujący użytkownika np. o pobieraniu danych z serwisu API.
     */
    private ProgressDialog progress;

    /**
     * Zmienna favoriteBeerToggleButton
     * Obiekt klasy ToggleButton
     * Jest to przycisk znajdujący się w Toolbarze, który ukazuje listę piw dodanych do ulubionych.
     */
    private ToggleButton favoriteBeerToggleButton;

    /**
     * Zmienna searchBeerEditText
     * Obiekt klasy EditText
     * Pole, w którym użytkownik podaje nazwę piwa, które chce wyszukać.
     */
    private EditText searchBeerEditText;

    /**
     * Zmienna mSwipeRefreshLayout
     * Obiekt klasy SwipeRefreshLayout
     * Odpowiada za tzw. mechanizm "Pull to refresh"
     */
    private SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * Zmienna dbHelper
     * Obiekt klasy DatabaseHelper
     * Odpowiada za połączenie z lokalną bazą danych.
     */
    private DatabaseHelper dbHelper;

    /**
     * Zmienna internetAccess typu boolean
     */
    boolean internetAccess = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        isAvailable();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);

        /**
         * Sprawdzenie czy urządzenie mobilne posiada dostęp do Internetu
         */
        if (internetAccess == true) {
            Toast.makeText(getApplicationContext(), "Wykryto dostęp do Internetu", Toast.LENGTH_LONG).show();
            fetchData("Zywiec");
        } else if (internetAccess == false) {

            Toast.makeText(getApplicationContext(), "Brak dostępu do Internetu.\nPraca w trybie offline", Toast.LENGTH_LONG).show();
            try {

                createOfflineList();
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }


    }

    /**
     * Metoda publiczna fetchData()
     * Odpowiada za pobieranie danych z serwisu API
     *
     * @param beerName
     */
    public void fetchData(String beerName) {

        beerDescriptionList.clear();
        beerABVList.clear();
        beerPhotoMediumUrlsList.clear();
        beerPhotoLargeUrlsList.clear();
        progress = ProgressDialog.show(this, "Pobieranie danych...", "Proszę czekać...", true, false, null);

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(BASE_API_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL).build();
        ApiInterface breweryRestInterface = adapter.create(ApiInterface.class);


        /**Metoda success() zaimplementowana z biblioteki Retrofit
         * W przypadku sukcesu połączenia się z serwisem, dane zostają pobierane do odpowiednich list
         * @param breweries
         * @param response
         */
        breweryRestInterface.getBeerReport(beerName, new Callback<Brewery>() {
            @Override
            public void success(Brewery breweries, Response response) {

                dbHelper = (DatabaseHelper) OpenHelperManager.getHelper(getApplication(), DatabaseHelper.class);
                final RuntimeExceptionDao studDao = dbHelper.getStudRuntimeExceptionDao();
                BeerDataBaseTemplate wdt = new BeerDataBaseTemplate();


                //////////////////////////////
                //Dodawanie do bazy
                for (Datum i : breweries.getData()) {
                    if (i.getName() != null && i.getAbv() != null && i.getDescription() != null && i.getLabels() != null) {

                        studDao.createIfNotExists(new BeerDataBaseTemplate(
                                        "" + i.getName(),
                                        i.getAbv(), i.getDescription(), i.getLabels().getMedium().toString(), i.getLabels().getLarge().toString(),
                                        false)
                        );

                    } else if (i.getName() != null && i.getAbv() != null && i.getDescription() == null && i.getLabels() != null) {
                        studDao.createOrUpdate(new BeerDataBaseTemplate("" + i.getName(), i.getAbv(), "Brak danych", i.getLabels().getMedium().toString(), i.getLabels().getLarge().toString(),
                                false));

                    } else if (i.getName() != null && i.getAbv() != null && i.getDescription() == null && i.getLabels() == null) {
                        studDao.createOrUpdate(new BeerDataBaseTemplate("" + i.getName(), i.getAbv(), "Brak danych", "Brak zdjęcia", "Brak zdjęcia", false));

                    } else if (i.getName() != null && i.getAbv() == null && i.getDescription() == null && i.getLabels() == null) {
                        studDao.createOrUpdate(new BeerDataBaseTemplate("" + i.getName(), "Brak danych", "Brak danych", "Brak zdjęcia", "Brak zdjęcia", false));

                    } else if (i.getName() == null && i.getAbv() == null && i.getDescription() == null && i.getLabels() == null) {
                        studDao.createOrUpdate(new BeerDataBaseTemplate("Brak danych", "Brak danych", "Brak danych", "Brak zdjęcia", "Brak zdjęcia", false));

                    }


                    //////////////////////////////

                    if (i.getName() != null) {

                        String beerName = i.getName().toString();
                        simpleBeerList.add(beerName);

                        if (i.getLabels() != null) {

                            String url = i.getLabels().getMedium().toString();
                            beerPhotoMediumUrlsList.add(url);
                        } else {
                            beerPhotoMediumUrlsList.add("Brak zdjęcia");
                        }

                        if (i.getLabels() != null) {
                            String url = i.getLabels().getLarge().toString();
                            beerPhotoLargeUrlsList.add(url);

                        } else {
                            beerPhotoLargeUrlsList.add("Brak zdjęcia");
                        }

                        if (i.getDescription() != null) {
                            String description = i.getDescription().toString();
                            beerDescriptionList.add(description);
                        } else {
                            beerDescriptionList.add("Brak danych");
                        }

                        if (i.getAbv() != null) {
                            String abv = i.getAbv().toString();
                            beerABVList.add(abv);
                        } else {
                            beerABVList.add("Brak danych");
                        }

                        mRecyclerView = (RecyclerView) findViewById(R.id.cardList);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adapter2 = new CardViewAdapter(HomeActivity.this, simpleBeerList);
                        mRecyclerView.setAdapter(adapter2);

                        if (simpleBeerList.size() > 0) {
                            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                @Override
                                public void onRefresh() {
                                    mSwipeRefreshLayout.setRefreshing(true);
                                    refreshContent();
                                }
                            });
                        }
                        progress.hide();

                    }
                }
                progress.hide();
                Log.d("SimpleBeerList size: ", String.valueOf(simpleBeerList.size()));
            }

            /**
             * Metoda failure() zaimplementowana z biblioteki Retrofit
             * Odpowiada za procesy związane z brakiem sukcesu połączenia się z serwisem API
             * Wyświetla stosowny komunikat
             * @param error
             */
            @Override
            public void failure(RetrofitError error) {
                Log.d("Dane: ", error.toString());
                progress.hide();
                Toast.makeText(getApplicationContext(), "Brak dostępu do Internetu.\nPraca w trybie offline", Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<String> getNewBeerData() {
        List<String> newBeerData = new ArrayList<>();

        for (int i = 0; i < simpleBeerList.size(); i++) {
            int randomBeerDataIndex = new Random().nextInt(simpleBeerList.size() - 1);
            newBeerData.add(simpleBeerList.get(randomBeerDataIndex));
        }

        return newBeerData;
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
        getMenuInflater().inflate(R.menu.menu_home, menu);

        if (internetAccess == true) {
            menu.findItem(R.id.searchBeerMenuItem).setVisible(true);
        } else {
            Toast.makeText(context, "Opcja wyszukiwania zablokowana.\nMożliwa tylko w trybie online.", Toast.LENGTH_LONG).show();
            menu.findItem(R.id.searchBeerMenuItem).setVisible(false);

        }

        favoriteBeerToggleButton = (ToggleButton) menu.findItem(R.id.favouriteBeersMenuItem).getActionView().findViewById(R.id.switch_show_protected);
        favoriteBeerToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (favoriteBeerToggleButton.isChecked()) {

                    if (!CardViewAdapter.favoriteBeers.isEmpty()) {

                        mRecyclerView = (RecyclerView) findViewById(R.id.cardList);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adapter2 = new CardViewAdapter(HomeActivity.this, CardViewAdapter.favoriteBeers);
                        mRecyclerView.setAdapter(adapter2);
                    } else {
                        Toast.makeText(context, "Brak ulubionych piw", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    if (internetAccess == true) {
                        mRecyclerView = (RecyclerView) findViewById(R.id.cardList);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adapter2 = new CardViewAdapter(HomeActivity.this, simpleBeerList);
                        mRecyclerView.setAdapter(adapter2);
                    } else {
                        mRecyclerView = (RecyclerView) findViewById(R.id.cardList);
                        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        favoriteBeerAdapter = new CardViewAdapter(HomeActivity.this, offlineBeers);
                        mRecyclerView.setAdapter(favoriteBeerAdapter);
                    }

                }
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

        if (id == R.id.searchBeerMenuItem) {

            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View searchDialog = layoutInflater.inflate(R.layout.dialog_box, null);

            final AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setView(searchDialog);

            searchBeerEditText = (EditText) searchDialog.findViewById(R.id.searchBeerEditText);

            builder.setTitle("Wyszukaj piwo");
            builder.setPositiveButton("Szukaj", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (searchBeerEditText.getText().toString() != null) {
                        simpleBeerList.clear();
                        fetchData(searchBeerEditText.getText().toString());
                    }

                }
            });

            builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Metoda createOfflineList()
     * Odpowiada za tworzenie listy RecyclerView pobierając dane z lokalnej bazy,
     * wówczas gdy aplikacja działa w trybie offline.
     */
    public void createOfflineList() throws SQLException {


        dbHelper = (DatabaseHelper) OpenHelperManager.getHelper(getApplication(), DatabaseHelper.class);
        final RuntimeExceptionDao studDao = dbHelper.getStudRuntimeExceptionDao();
        mSwipeRefreshLayout.setEnabled(false);

        beerDescriptionList.clear();
        beerABVList.clear();
        beerPhotoMediumUrlsList.clear();
        beerPhotoLargeUrlsList.clear();

        int size = 0;
        for (Object obj : studDao.queryForAll()) {
            BeerDataBaseTemplate wdt = (BeerDataBaseTemplate) obj;
            Log.d("Imie piwa z bazy:", wdt.getBeerName().toString());
            String beer = wdt.getBeerName().toString();
            String voltage = wdt.getBeerVoltage().toString();
            String description = wdt.getBeerDescription().toString();
            String beerImageMedium = wdt.getBeerImageMedium().toString();
            String beerImageLarge = wdt.getBeerImageLarge().toString();
            offlineBeers.add(beer);
            size = offlineBeers.size();

            Log.d("Rozmiar nowej: ", String.valueOf(size));


            //JAK DODANE DO BAZY TO POBIERZ PRAWIDŁOWO

            beerPhotoMediumUrlsList.add(beerImageMedium);
            beerPhotoLargeUrlsList.add(beerImageLarge);
            beerABVList.add(voltage);
            beerDescriptionList.add(description);

        }

        if (size == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Uwaga");
            builder.setIcon(R.drawable.ic_info);
            builder.setMessage("Zanim aplikacja zacznie działać offline, pierwsze uruchomienie programu należy zastosować, mając dostęp do Internetu.\n");

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.cardList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        favoriteBeerAdapter = new CardViewAdapter(HomeActivity.this, offlineBeers);
        mRecyclerView.setAdapter(favoriteBeerAdapter);
    }

    /**
     * Metoda isAvailable typu boolean
     * Sprawdza, czy urządzenie posiada dostęp do Internetu poprzez pingowanie ze stroną Google.
     *
     * @return false
     */
    public Boolean isAvailable() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1    www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal == 0);
            if (reachable) {

                return internetAccess = true;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    private void refreshContent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                adapter2 = new CardViewAdapter(context, (ArrayList<String>) getNewBeerData());
                mRecyclerView.setAdapter(adapter2);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

}



