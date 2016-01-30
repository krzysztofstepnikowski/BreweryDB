package com.example.krzysiek.brewerydb;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by SlawomirKustra on 30.01.2016.
 */
public class BeerDataBaseTemplate {


    //Database Field for Table WeatherDbTemplate
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String beerName;
    @DatabaseField
    private String beerVoltage;
    @DatabaseField
    private boolean beerFav;
    public BeerDataBaseTemplate(int id, String beerName, String beerVoltage, boolean beerFav) {
        this.id = id;
        this.beerName = beerName;
        this.beerVoltage = beerVoltage;
        this.beerFav = beerFav;
    }

    public BeerDataBaseTemplate(String beerName, String beerVoltage, boolean beerFav) {
        this.beerName = beerName;
        this.beerVoltage = beerVoltage;
        this.beerFav = beerFav;
    }

    public BeerDataBaseTemplate() {
    }



    public String getBeerName() {
        return beerName;
    }

    public void setBeerName(String beerName) {
        this.beerName = beerName;
    }

    public String getBeerVoltage() {
        return beerVoltage;
    }

    public void setBeerVoltage(String beerVoltage) {
        this.beerVoltage = beerVoltage;
    }

    public boolean isBeerFav() {
        return beerFav;
    }

    public void setBeerFav(boolean beerFav) {
        this.beerFav = beerFav;
    }











}