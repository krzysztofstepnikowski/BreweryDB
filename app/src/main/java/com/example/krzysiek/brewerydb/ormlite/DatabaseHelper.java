package com.example.krzysiek.brewerydb.ormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * @author Krzysztof Stępnikowski
 *         Odpowiedzialna za połączenie i stworzenie lokalnej bazy danych.
 * @class DatabaseHelper
 */
public class DatabaseHelper  extends OrmLiteSqliteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "brewerydb";
    private static final int DATABASE_VERSION =  2;


    private RuntimeExceptionDao studRuntimeDAO = null;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, BeerDataBaseTemplate.class);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, BeerDataBaseTemplate.class, true);
            onCreate(sqLiteDatabase,connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public RuntimeExceptionDao getStudRuntimeExceptionDao(){
        if(studRuntimeDAO == null){
            studRuntimeDAO = getRuntimeExceptionDao(BeerDataBaseTemplate.class);
        }
        return studRuntimeDAO;

    }

}