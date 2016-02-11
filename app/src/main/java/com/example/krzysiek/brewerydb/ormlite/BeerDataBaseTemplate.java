package com.example.krzysiek.brewerydb.ormlite;

import com.j256.ormlite.field.DatabaseField;

/**
 * @author Krzysztof Stępnikowski
 *         Tworzy strukturę bazy danych (schemat).
 * @class BeerDataBaseTemplate
 */
public class BeerDataBaseTemplate {

    @DatabaseField(generatedId = true, unique = true)
    private int id;
    @DatabaseField
    private String beerID;
    @DatabaseField
    private String beerName;
    @DatabaseField
    private String beerVoltage;
    @DatabaseField
    private String beerDescription;
    @DatabaseField
    private String beerImageMedium;
    @DatabaseField
    private String beerImageLarge;
    @DatabaseField
    private boolean beerFav;

    public BeerDataBaseTemplate(int id, String beerID, String beerName, String beerVoltage, String beerDescription, String beerImageMedium, String beerImageLarge, boolean beerFav) {
        this.id = id;
        this.beerID=beerID;
        this.beerName = beerName;
        this.beerVoltage = beerVoltage;
        this.beerDescription = beerDescription;
        this.beerImageMedium = beerImageMedium;
        this.beerImageLarge = beerImageLarge;
        this.beerFav = beerFav;
    }

    public BeerDataBaseTemplate(String beerID,String beerName, String beerVoltage, String beerDescription, String beerImageMedium, String beerImageLarge, boolean beerFav) {
        this.beerID=beerID;
        this.beerName = beerName;
        this.beerVoltage = beerVoltage;
        this.beerDescription = beerDescription;
        this.beerImageMedium = beerImageMedium;
        this.beerImageLarge = beerImageLarge;
        this.beerFav = beerFav;
    }

    public BeerDataBaseTemplate(String beerName) {
        this.beerName = beerName;
    }

    public BeerDataBaseTemplate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeerID() {
        return beerID;
    }

    public void setBeerID(String beerID) {
        this.beerID = beerID;
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

    public String getBeerDescription() {
        return beerDescription;
    }

    public void setBeerDescription(String beerDescription) {
        this.beerDescription = beerDescription;
    }

    public String getBeerImageMedium() {
        return beerImageMedium;
    }

    public void setBeerImageMedium(String beerImageMedium) {
        this.beerImageMedium = beerImageMedium;
    }

    public String getBeerImageLarge() {
        return beerImageLarge;
    }

    public void setBeerImageLarge(String beerImageLarge) {
        this.beerImageLarge = beerImageLarge;
    }

    public boolean isBeerFav() {
        return beerFav;
    }

    public void setBeerFav(boolean beerFav) {
        this.beerFav = beerFav;
    }


}