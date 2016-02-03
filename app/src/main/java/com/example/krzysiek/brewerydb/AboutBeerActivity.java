package com.example.krzysiek.brewerydb;


import android.content.Context;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.squareup.picasso.Picasso;




public class AboutBeerActivity extends AppCompatActivity {

    private ImageView imageViewBeerDetails;
    private TextView abvBeerTextViewDetails;
    private TextView descriptionBeerTextViewDetails;
    private ToggleButton addToFavouriteDetailsButton;

    private ToggleButton mSwitchShowSecure;

    final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_beer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();


        String imageBeer = extras.getString("imageBeer");
        String nameBeer = extras.getString("nameBeer");
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

        addToFavouriteDetailsButton = (ToggleButton) findViewById(R.id.addToFavouriteDetailsButton);
        addToFavouriteDetailsButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Dodano do ulubionych", Toast.LENGTH_SHORT).show();
                    buttonView.setBackgroundResource(R.color.addToFavouriteButton);

                } else {

                    buttonView.setBackgroundResource(R.color.colorPrimaryDark);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);

        mSwitchShowSecure = (ToggleButton) menu.findItem(R.id.favouriteBeersMenuItem).getActionView().findViewById(R.id.switch_show_protected);
        mSwitchShowSecure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}


