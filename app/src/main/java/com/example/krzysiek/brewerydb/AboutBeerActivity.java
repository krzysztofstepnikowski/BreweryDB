package com.example.krzysiek.brewerydb;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.krzysiek.brewerydb.models.Brewery;
import com.example.krzysiek.brewerydb.models.Datum;
import com.example.krzysiek.brewerydb.network.ApiInterface;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AboutBeerActivity extends AppCompatActivity {

    private ImageView imageViewBeerDetails;
    private TextView nameBeerTextView;
    private TextView descriptionBeerTextView;
    private Button addToFavouriteDetailsButton;
    public static ArrayList<String> descriptionBeer = new ArrayList<String>();
    public static final String BASE_API_URL = "https://api.brewerydb.com/v2";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_beer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        Bundle extras = getIntent().getExtras();

        String nameBeer = extras.getString("nameBeer");
        Bitmap bmp = (Bitmap) extras.getParcelable("imageBeerBitMap");

        imageViewBeerDetails = (ImageView) findViewById(R.id.imageViewBeerDetails);
        imageViewBeerDetails.setImageBitmap(bmp);

        nameBeerTextView = (TextView) findViewById(R.id.nameBeerTextViewDeitals);
        nameBeerTextView.setText(nameBeer);

        addToFavouriteDetailsButton = (Button) findViewById(R.id.addToFavouriteDetailsButton);

        addToFavouriteDetailsButton.setTag(1);


        addToFavouriteDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int status = (Integer) v.getTag();

                if (status == 1) {

                    addToFavouriteDetailsButton.setText("Usuń z ulubionych");
                    addToFavouriteDetailsButton.setBackgroundResource(R.color.addToFavouriteButton);
                    v.setTag(0);
                } else {
                    addToFavouriteDetailsButton.setText("Dodaj do ulubionych");
                    addToFavouriteDetailsButton.setBackgroundResource(R.color.colorPrimaryDark);
                    v.setTag(1);
                }
            }
        });


    }


}


