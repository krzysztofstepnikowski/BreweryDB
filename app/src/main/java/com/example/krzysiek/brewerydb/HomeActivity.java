package com.example.krzysiek.brewerydb;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;


import com.example.krzysiek.brewerydb.models.Brewery;
import com.example.krzysiek.brewerydb.models.Datum;
import com.example.krzysiek.brewerydb.network.ApiInterface;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HomeActivity extends AppCompatActivity     {

    private ArrayList<String> simpleBeerList = new ArrayList<String>();
    public static ArrayList<String> beerPhotoUrls = new ArrayList<>();
    public static final String BASE_API_URL = "https://api.brewerydb.com/v2";

    private RecyclerView mRecyclerView;
    private CardViewAdapter adapter2;



    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        fetchData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void fetchData(){

        progress = ProgressDialog.show(this, "Pobieranie danych...", "Proszę czekać...", true, false, null);

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(BASE_API_URL)
                .setLogLevel(retrofit.RestAdapter.LogLevel.FULL).build();
        ApiInterface breweryRestInterface = adapter.create(ApiInterface.class);

        breweryRestInterface.getBeerReport(new Callback<Brewery>() {
            @Override
            public void success(Brewery breweries, Response response) {

                for (Datum i : breweries.getData()) {

                    // Trzeba obsłużyć nulle i jeśli są to przypisać im jakiś string żeby lista się iterowała ok.
                    // picasso sam obsłuży błędne linki
                    // przy nullach apka się crashuje (a niestety nie każdy browar ma zdjęcie ;/ )

                    if (i.getName() != null) {

                        String beerName = i.getName().toString();
                        simpleBeerList.add(beerName);

                        if(i.getLabels()!=null){

                            String url = i.getLabels().getMedium().toString();
                            beerPhotoUrls.add(url);

                        }


                        else{
                            beerPhotoUrls.add("Pusty zawartość zmiennej"); //pusty String
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
