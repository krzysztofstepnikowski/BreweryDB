package com.example.krzysiek.brewerydb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.example.krzysiek.brewerydb.models.Brewery;
import com.example.krzysiek.brewerydb.models.Datum;
import com.example.krzysiek.brewerydb.network.ApiInterface;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<String> simpleBeerList = new ArrayList<String>();
    public static ArrayList<String> beerPhotoMediumUrlsList = new ArrayList<String>();
    public static ArrayList<String> beerPhotoLargeUrlsList = new ArrayList<String>();
    public static ArrayList<String> beerABVList = new ArrayList<String>();
    public static ArrayList<String> beerDescriptionList = new ArrayList<String>();
    public static final String BASE_API_URL = "https://api.brewerydb.com/v2";

    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";


    private RecyclerView mRecyclerView;
    private CardViewAdapter adapter2;
    private ProgressDialog progress;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        fetchData();

    }

    public void fetchData() {

        progress = ProgressDialog.show(this, "Pobieranie danych...", "Proszę czekać...", true, false, null);

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(BASE_API_URL)
                .setLogLevel(retrofit.RestAdapter.LogLevel.FULL).build();
        ApiInterface breweryRestInterface = adapter.create(ApiInterface.class);

        breweryRestInterface.getBeerReport(new Callback<Brewery>() {
            @Override
            public void success(Brewery breweries, Response response) {



                DatabaseHelper dbHelper;
                dbHelper = (DatabaseHelper) OpenHelperManager.getHelper(getApplication(), DatabaseHelper.class);
                final RuntimeExceptionDao studDao = dbHelper.getStudRuntimeExceptionDao();
                //////////////////////////////
                int j =0;
                for(Object obj : studDao.queryForAll()) {
                    BeerDataBaseTemplate wdt = (BeerDataBaseTemplate) obj;
                    Log.d("Imie piwa z bazy:", wdt.getBeerName().toString());
                }

                //Dodaj do bazy
                for (Datum i : breweries.getData()) {
                    if(i.getName()!=null&&i.getAbv()!=null){
                        studDao.create(new BeerDataBaseTemplate(
                                        "" + i.getName(),
                                        i.getAbv(),
                                        false)
                        );
                        List<BeerDataBaseTemplate> list2a = studDao.queryForAll();
                        Log.d("baza: ", list2a.get(j).toString());
                        j++;
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
                        progress.hide();




                    }
                }






            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("Dane: ", error.toString());
                progress.hide();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

//        if (id == R.id.favouriteBeersMenuItem) {
//            Intent intent = new Intent(this, FavouriteBeersActivity.class);
//            startActivity(intent);
//        }

        return super.onOptionsItemSelected(item);
    }

    public void createOrmLiteDB() {
        dbHelper = (DatabaseHelper) OpenHelperManager.getHelper(getApplicationContext(), DatabaseHelper.class);
        final RuntimeExceptionDao runtimeExceptionDao = dbHelper.getStudRuntimeExceptionDao();
        List<BeerDataBaseTemplate> breweryDbList = runtimeExceptionDao.queryForAll();

        int breweryDbListSize = breweryDbList.size();

        //List<BreweryDb> breweryDbList2 = runtimeExceptionDao.queryForEq("namedb", );


    }
}
