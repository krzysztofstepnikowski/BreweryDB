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
import android.widget.Toast;
import android.widget.ToggleButton;


import com.example.krzysiek.brewerydb.ormlite.BeerDataBaseTemplate;
import com.example.krzysiek.brewerydb.ormlite.DatabaseHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.squareup.picasso.Picasso;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit.http.Query;

/**
 * @author Krzysztof Stępnikowski
 * @class CardViewAdapter
 * Odpowiada za wygląd pojedynczego elementu RecyclerView
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.BreweryViewHolder> {


    /**
     * Zmienna dataSource
     * Lista przechowująca nazwy piw
     */
    private List<String> dataSource;

    /**
     * Zmienna context
     * Przechowuje aktualny motyw widoku
     */
    private Context context;

    /**
     * Lista przechowująca ulubione piwa
     */
    public static ArrayList<String> favoriteBeers = new ArrayList<String>();

    /**
     * Konstruktor klasy CardViewAdapter
     *
     * @param context
     * @param dataSource
     */
    public CardViewAdapter(Context context, ArrayList<String> dataSource) {
        this.context = context;
        this.dataSource = dataSource;
    }

    /**
     * Metoda onCreateViewHolder typu BrewerViewHolder
     * Odpowiada za widok pojedynczego elementu listy RecyclerView
     *
     * @param parent
     * @param viewType
     * @return breweryViewHolder
     */
    @Override
    public BreweryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.beer_row, parent, false);


        BreweryViewHolder breweryViewHolder = new BreweryViewHolder(view);

        return breweryViewHolder;
    }

    /**
     * Metoda onBindViewHolder
     *
     * @param holder
     * @param position Odpowiada za wypełnienie elementów listy RecyclerView prawidłowymi danymi: zdjęcie piwa i jego nazwę
     */
    @Override
    public void onBindViewHolder(final BreweryViewHolder holder, final int position) {

        holder.nameBeerTextView.setText(dataSource.get(position).toString());
        holder.imageViewBeer.setImageResource(R.drawable.icon_beer);
        final String urlMedium = HomeActivity.beerPhotoMediumUrlsList.get(position);
        final Context context = holder.imageViewBeer.getContext();
        Picasso.with(context)
                .load(urlMedium)
                .placeholder(R.drawable.icon_beer)
                .error(R.drawable.icon_beer)
                .into(holder.imageViewBeer);


        holder.addToFavouriteToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                DatabaseHelper dbHelper;
                dbHelper = (DatabaseHelper) OpenHelperManager.getHelper(context, DatabaseHelper.class);
                final RuntimeExceptionDao studDao = dbHelper.getStudRuntimeExceptionDao();
                UpdateBuilder<BeerDataBaseTemplate, String> updateBuilder = studDao.updateBuilder();
                BeerDataBaseTemplate wdt = new BeerDataBaseTemplate();


                if (buttonView.isChecked()) {


                    buttonView.setBackgroundResource(R.color.addToFavouriteButton);

                    try {

                        updateBuilder.updateColumnValue("beerFav", true);
                        updateBuilder.where().eq("beerName", dataSource.get(position).toString());
                        updateBuilder.update();

                        Toast.makeText(context, "Dodano do ulubionych", Toast.LENGTH_SHORT).show();


                        if (dataSource.get(position).toString() != null) {
                            favoriteBeers.add(dataSource.get(position).toString());
                        } else {
                            favoriteBeers.add("Brak danych");
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    Log.d("Favorite size= ", String.valueOf(favoriteBeers.size()));


                } else {
                    buttonView.setBackgroundResource(R.color.colorPrimaryDark);

                    try {


                        updateBuilder.updateColumnValue("beerFav", false);
                        updateBuilder.where().eq("beerName",dataSource.get(position).toString());
                        updateBuilder.update();

                        Toast.makeText(context, "Usunięto z ulubionych", Toast.LENGTH_SHORT).show();
                        favoriteBeers.remove(dataSource.get(position));


                        Log.d("Favorite size= ", String.valueOf(favoriteBeers.size()));

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }


                }
            }
        });


    }

    /**
     * Metoda getItemCount()
     * Metoda zwraca aktualny rozmiar listy, w której znajdują się nazwy piw
     *
     * @return dataSource.size()
     */
    @Override
    public int getItemCount() {
        Log.d("Rozmiar listy: ", String.valueOf(dataSource.size()));

        return dataSource.size();
    }

    /**
     * @author Krzysztof Stępnikowski
     *         Tworzy widok pojedynczego elementu listy RecyclerView
     * @class BreweryViewHolder
     */
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


        /**
         * Metoda onClick()
         * Odpowiada za akcję wywołaną poprzez kliknięcie na pojedynczy element listy.
         * Wysyła odpowiednie dane do drugiego Intenta, w którym znajdują się szczegóły o danym piwie.
         *
         * @param v
         */
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
