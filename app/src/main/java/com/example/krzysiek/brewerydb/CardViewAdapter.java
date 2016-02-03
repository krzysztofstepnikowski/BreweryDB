package com.example.krzysiek.brewerydb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.BreweryViewHolder> {


    private List<String> dataSource;
    private Context context;
    private ArrayList<String> urlBeersList = new ArrayList<String>();
    private ArrayList<String> nameBeerList = new ArrayList<String>();


    public CardViewAdapter(Context context, ArrayList<String> dataSource) {
        this.context = context;
        this.dataSource = dataSource;
    }


    @Override
    public BreweryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.beer_row, parent, false);


        BreweryViewHolder breweryViewHolder = new BreweryViewHolder(view);

        return breweryViewHolder;
    }

    @Override
    public void onBindViewHolder(final BreweryViewHolder holder, final int position) {

        holder.nameBeerTextView.setText(dataSource.get(position).toString());
        holder.imageViewBeer.setImageResource(R.drawable.icon_beer);
        final String urlMedium = HomeActivity.beerPhotoMediumUrlsList.get(position);
        String urlLarge = HomeActivity.beerPhotoLargeUrlsList.get(position);
        String abv = HomeActivity.beerABVList.get(position);
        final String description = HomeActivity.beerDescriptionList.get(position);
        final Context context = holder.imageViewBeer.getContext();
        Picasso.with(context)
                .load(urlMedium)
                .placeholder(R.drawable.icon_beer)
                .error(R.drawable.icon_beer)
                .into(holder.imageViewBeer);


        holder.addToFavouriteToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                Set<String> urlBeerSet = new HashSet<String>();
                Set<String> nameBeerSet = new HashSet<String>();

                if (buttonView.isChecked()) {
                    buttonView.setBackgroundResource(R.color.addToFavouriteButton);

                    buttonView.setTag("addFavoiteBeer");
                    urlBeersList.add(urlMedium);
                    nameBeerList.add(dataSource.get(position).toString());

                    urlBeerSet.addAll(urlBeersList);
                    editor.putStringSet("urlBeersSet", urlBeerSet);

                    nameBeerSet.addAll(nameBeerList);
                    editor.putStringSet("nameBeerSet", nameBeerSet);

                    Log.d("ZapiszurlBeerSet", String.valueOf(urlBeerSet.size()));
                    Log.d("ZapisznameBeer", String.valueOf(nameBeerSet.size()));

                    editor.commit();


                } else {

                    buttonView.setBackgroundResource(R.color.colorPrimaryDark);
                    buttonView.setTag("removeFavoriteBeer");
                    urlBeersList.remove(urlMedium);
                    nameBeerList.remove(dataSource.get(position).toString());

                    urlBeerSet.addAll(urlBeersList);
                    editor.putStringSet("urlBeerSet", urlBeerSet);

                    nameBeerSet.addAll(nameBeerList);
                    editor.putStringSet("nameBeerSet", nameBeerSet);


                    Log.d("UsunurlBeerSet", String.valueOf(urlBeerSet.size()));
                    Log.d("UsunnameBeer", String.valueOf(nameBeerSet.size()));

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        Log.d("Rozmiar listy: ", String.valueOf(dataSource.size()));

        return dataSource.size();
    }

    public class BreweryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameBeerTextView;
        private ImageView imageViewBeer;
        private ToggleButton addToFavouriteToggleButton;
        private Context context;
        public CardView mCardView;

        public BreweryViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            mCardView = (CardView) itemView.findViewById(R.id.cardView);
            itemView.setOnClickListener(this);
            nameBeerTextView = (TextView) itemView.findViewById(R.id.nameBeerTextView);
            imageViewBeer = (ImageView) itemView.findViewById(R.id.imageViewBeer);
            addToFavouriteToggleButton = (ToggleButton) itemView.findViewById(R.id.addToFavouriteButton);
        }


        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context, AboutBeerActivity.class);
            intent.putExtra("imageBeer", HomeActivity.beerPhotoLargeUrlsList.get(getAdapterPosition()));
            intent.putExtra("nameBeer", nameBeerTextView.getText().toString());
            intent.putExtra("abvBeer", HomeActivity.beerABVList.get(getAdapterPosition()));
            intent.putExtra("descriptionBeer", HomeActivity.beerDescriptionList.get(getAdapterPosition()));
            context.startActivity(intent);


        }


    }
}
