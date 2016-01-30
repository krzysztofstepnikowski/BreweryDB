package com.example.krzysiek.brewerydb.ormlite;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Krzysztof Stępnikowski on 2016-01-30.
 */
public class BreweryDb {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String namedb;
    @DatabaseField
    private String voltdb;
    @DatabaseField
    private String descriptiondb;

    public BreweryDb() {
    }

    public BreweryDb(int id, String namedb, String voltdb, String descriptiondb) {

        this.id = id;
        this.namedb = namedb;
        this.voltdb = voltdb;
        this.descriptiondb = descriptiondb;
    }

    public BreweryDb(String namedb, String voltdb, String descriptiondb) {
        this.namedb = namedb;
        this.voltdb = voltdb;
        this.descriptiondb = descriptiondb;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamedb() {
        return namedb;
    }

    public void setNamedb(String namedb) {
        this.namedb = namedb;
    }

    public String getVoltdb() {
        return voltdb;
    }

    public void setVoltdb(String voltdb) {
        this.voltdb = voltdb;
    }

    public String getDescriptiondb() {
        return descriptiondb;
    }

    public void setDescriptiondb(String descriptiondb) {
        this.descriptiondb = descriptiondb;
    }
}
