package com.example.krzysiek.brewerydb.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Krzysztof Stępnikowski on 2016-01-30.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "brewerydb";
    private static final int DATABASE_VERSION = 1;
    private RuntimeExceptionDao studRuntimeDAO = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, BreweryDb.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource, BreweryDb.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public RuntimeExceptionDao getStudRuntimeExceptionDao() {
        if (studRuntimeDAO == null) {
            studRuntimeDAO = getRuntimeExceptionDao(BreweryDb.class);
        }
        return studRuntimeDAO;

    }
}
