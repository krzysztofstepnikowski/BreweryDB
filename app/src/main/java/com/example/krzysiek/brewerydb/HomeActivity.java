package com.example.krzysiek.brewerydb;


import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;


import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import android.widget.ToggleButton;


import com.example.krzysiek.brewerydb.models.Brewery;
import com.example.krzysiek.brewerydb.models.Datum;
import com.example.krzysiek.brewerydb.network.ApiInterface;
import com.example.krzysiek.brewerydb.ormlite.BeerDataBaseTemplate;
import com.example.krzysiek.brewerydb.ormlite.DatabaseHelper;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.mobsandgeeks.saripaar.Validator;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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


    final Context context = this;
    private RecyclerView mRecyclerView;
    private CardViewAdapter adapter2;
    private CardViewAdapter favoriteBeerAdapter;
    private ProgressDialog progress;
    private ToggleButton mSwitchShowSecure;


    EditText searchBeerEditText;


    DatabaseHelper dbHelper;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        fetchData("Zywiec");


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void fetchData(String beerName) {

        progress = ProgressDialog.show(this, "Pobieranie danych...", "Proszę czekać...", true, false, null);

        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(BASE_API_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL).build();
        ApiInterface breweryRestInterface = adapter.create(ApiInterface.class);


        breweryRestInterface.getBeerReport(beerName, new Callback<Brewery>() {
            @Override
            public void success(Brewery breweries, Response response) {

                DatabaseHelper dbHelper;
                dbHelper = (DatabaseHelper) OpenHelperManager.getHelper(getApplication(), DatabaseHelper.class);
                final RuntimeExceptionDao studDao = dbHelper.getStudRuntimeExceptionDao();
                //////////////////////////////
                int j = 0;
                for (Object obj : studDao.queryForAll()) {
                    BeerDataBaseTemplate wdt = (BeerDataBaseTemplate) obj;
                    Log.d("Imie piwa z bazy:", wdt.getBeerName().toString());
                }


                //Dodaj do bazy
                for (Datum i : breweries.getData()) {
                    if (i.getName() != null && i.getAbv() != null) {
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

        mSwitchShowSecure = (ToggleButton) menu.findItem(R.id.favouriteBeersMenuItem).getActionView().findViewById(R.id.switch_show_protected);
        mSwitchShowSecure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (mSwitchShowSecure.isChecked()) {

                    SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    Set<String> urlBeerSet = pref.getStringSet("urlBeersSet", null);
                    ArrayList<String> urlBeerList = new ArrayList<String>(urlBeerSet);

                    Set<String> nameBeerSet = pref.getStringSet("nameBeerSet", null);
                    ArrayList<String> nameBeerList = new ArrayList<String>(nameBeerSet);

                    //progress = ProgressDialog.show(getApplicationContext(), "Pobieranie danych...", "Proszę czekać...", true, false, null);

                    mRecyclerView = (RecyclerView) findViewById(R.id.cardList);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    favoriteBeerAdapter = new CardViewAdapter(HomeActivity.this, nameBeerList);
                    mRecyclerView.setAdapter(favoriteBeerAdapter);
                    progress.hide();
                } else {
                    mRecyclerView = (RecyclerView) findViewById(R.id.cardList);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter2 = new CardViewAdapter(HomeActivity.this, simpleBeerList);
                    mRecyclerView.setAdapter(adapter2);
                }


            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.searchBeerMenuItem) {

            final Validator validator = new Validator(this);

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


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Home Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.krzysiek.brewerydb/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Home Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.krzysiek.brewerydb/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}



