package com.example.krzysiek.brewerydb.ormlite;

import android.database.SQLException;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;

/**
 * Created by Krzysztof Stępnikowski on 2016-01-30.
 */
public class DataBaseUtilConfig extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[]{BreweryDb.class};

    public static void main(String[] args) throws IOException, SQLException, java.sql.SQLException {
        writeConfigFile("ormlite_config.txt", classes);
    }
}
