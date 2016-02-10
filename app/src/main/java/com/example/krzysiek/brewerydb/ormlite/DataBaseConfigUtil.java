package com.example.krzysiek.brewerydb.ormlite;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Krzysztof Stępnikowski
 *         Tworzy plik konfiguracyjny dla bazy OrmLite
 * @class DatabaseConfigUtil
 */
public class DataBaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[]{BeerDataBaseTemplate.class};

    public static void main(String[] args) throws IOException, SQLException {
        writeConfigFile("ormlite_config.txt", classes);
    }
}
