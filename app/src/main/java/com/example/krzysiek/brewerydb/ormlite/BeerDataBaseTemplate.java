package com.example.krzysiek.brewerydb.ormlite;

import com.j256.ormlite.field.DatabaseField;

/**
 * @author Krzysztof Stępnikowski
 *         Tworzy strukturę bazy danych (model).
 * @class BeerDataBaseTemplate
 */
public class BeerDataBaseTemplate {

    public static final String COLUMN_LOCAL_ID = "BEERDATABASETEMPLATE_TABLE_LOCAL_ID";
    public static final String COLUMN_BEER_ID = "BEERDATABASETEMPLATE_TABLE_BEER_ID";
    public static final String COLUMN_BEER_NAME = "BEERDATABASETEMPLATE_TABLE_BEER_NAME";
    public static final String COLUMN_BEER_VOLTAGE = "BEERDATABASETEMPLATE_TABLE_BEER_VOLTAGE";
    public static final String COLUMN_BEER_DESCRIPTION = "BEERDATABASETEMPLATE_TABLE_BEER_DESCRIPTION";
    public static final String COLUMN_BEER_IMAGE_MEDIUM = "BEERDATABASETEMPLATE_TABLE_BEER_IMAGE_MEDIUM";
    public static final String COLUMN_BEER_IMAGE_LARGE = "BEERDATABASETEMPLATE_TABLE_BEER_IMAGE_LARGE";
    public static final String COLUMN_BEER_FAVORITE = "BEERDATABASETEMPLATE_TABLE_BEER_FAVORITE";


    @DatabaseField(generatedId = true, unique = true, columnName = COLUMN_LOCAL_ID)
    private int id;
    @DatabaseField(columnName = COLUMN_BEER_ID)
    private String webBeerID;
    @DatabaseField(columnName = COLUMN_BEER_NAME)
    private String beerName;
    @DatabaseField(columnName = COLUMN_BEER_VOLTAGE)
    private String beerVoltage;
    @DatabaseField(columnName = COLUMN_BEER_DESCRIPTION)
    private String beerDescription;
    @DatabaseField(columnName = COLUMN_BEER_IMAGE_MEDIUM)
    private String beerImageMedium;
    @DatabaseField(columnName = COLUMN_BEER_IMAGE_LARGE)
    private String beerImageLarge;
    @DatabaseField(columnName = COLUMN_BEER_FAVORITE)
    private boolean beerFav;

    public BeerDataBaseTemplate(int id, String beerName, String beerVoltage, String beerDescription, String beerImageMedium,
                                String beerImageLarge, boolean beerFav) {
        this.id = id;
        this.beerName = beerName;
        this.beerVoltage = beerVoltage;
        this.beerDescription = beerDescription;
        this.beerImageMedium = beerImageMedium;
        this.beerImageLarge = beerImageLarge;
        this.beerFav = beerFav;
    }

    public BeerDataBaseTemplate(String beerName, String beerVoltage, String beerDescription, String beerImageMedium,
                                String beerImageLarge, boolean beerFav, String webBeerID) {
        this.beerName = beerName;
        this.beerVoltage = beerVoltage;
        this.beerDescription = beerDescription;
        this.beerImageMedium = beerImageMedium;
        this.beerImageLarge = beerImageLarge;
        this.beerFav = beerFav;
        this.webBeerID = webBeerID;
    }


    public BeerDataBaseTemplate() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getWebBeerID() {
        return webBeerID;
    }

    public void setWebBeerID(String webBeerID) {
        this.webBeerID = webBeerID;
    }

}