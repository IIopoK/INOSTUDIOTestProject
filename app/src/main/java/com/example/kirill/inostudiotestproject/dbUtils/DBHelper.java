package com.example.kirill.inostudiotestproject.dbUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by iiopok on 02.04.2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table list ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "created text"
                + ");");

        db.execSQL("create table product ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "listId integer,"
                + "isChecked integer,"
                + "number integer"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
