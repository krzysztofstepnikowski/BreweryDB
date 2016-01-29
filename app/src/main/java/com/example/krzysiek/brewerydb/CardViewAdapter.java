package com.example.krzysiek.brewerydb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.BreweryViewHolder> {


    private List<String> dataSource;
    public String url;
    public String abv;

    public CardViewAdapter(Context context, List<String> dataSource){
        this.dataSource=dataSource;
    }

    @Override
    public BreweryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.beer_row, parent, false);

        BreweryViewHolder breweryViewHolder = new BreweryViewHolder(view);

        return breweryViewHolder;
    }

    @Override
    public void onBindViewHolder(final BreweryViewHolder holder, int position) {

        holder.nameBeerTextView.setText(dataSource.get(position).toString());
        holder.imageViewBeer.setImageResource(R.drawable.icon_beer);
        url = HomeActivity.beerPhotoUrls.get(position);
        Context context = holder.imageViewBeer.getContext();
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.icon_beer)
                .error(R.drawable.icon_beer)
                .into(holder.imageViewBeer);


        holder.addToFavouriteButton.setTag(1);


        holder.addToFavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int status = (Integer) v.getTag();

                if (status == 1) {
                    holder.addToFavouriteButton.setText("Usuń z ulubionych");
                    holder.addToFavouriteButton.setBackgroundResource(R.color.addToFavouriteButton);
                    v.setTag(0);
                } else {
                    holder.addToFavouriteButton.setText("Dodaj do ulubionych");
                    holder.addToFavouriteButton.setBackgroundResource(R.color.colorPrimaryDark);
                    v.setTag(1);
                }


            }
        });

    }


    @Override
    public int getItemCount() {
        Log.d("Rozmiar listy: ", String.valueOf(dataSource.size()));

        return dataSource.size();
    }

    public class BreweryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView nameBeerTextView;
        private ImageView imageViewBeer;
        private Button addToFavouriteButton;
        private Context context;

        public BreweryViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
            nameBeerTextView = (TextView)itemView.findViewById(R.id.nameBeerTextView);
            imageViewBeer = (ImageView)itemView.findViewById(R.id.imageViewBeer);
            addToFavouriteButton = (Button)itemView.findViewById(R.id.addToFavouriteButton);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context,AboutBeerActivity.class);
            intent.putExtra("nameBeer", nameBeerTextView.getText().toString());
            intent.putExtra("abv",abv);
            context.startActivity(intent);

        }
    }
}
