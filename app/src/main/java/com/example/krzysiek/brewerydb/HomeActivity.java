package com.example.krzysiek.brewerydb;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;



import com.example.krzysiek.brewerydb.models.Brewery;
import com.example.krzysiek.brewerydb.models.Datum;
import com.example.krzysiek.brewerydb.network.ApiInterface;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HomeActivity extends AppCompatActivity {

    public static ArrayList<String> simpleBeerList = new ArrayList<String>();
    public static ArrayList<String> beerPhotoMediumUrlsList = new ArrayList<String>();
    public static ArrayList<String> beerPhotoLargeUrlsList = new ArrayList<String>();
    public static ArrayList<String> beerABVList = new ArrayList<String>();
    public static ArrayList<String> beerDescriptionList = new ArrayList<String>();
    public static final String BASE_API_URL = "https://api.brewerydb.com/v2";

    private RecyclerView mRecyclerView;
    private CardViewAdapter adapter2;
    private ProgressDialog progress;

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

                for (Datum i : breweries.getData()) {


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


}
