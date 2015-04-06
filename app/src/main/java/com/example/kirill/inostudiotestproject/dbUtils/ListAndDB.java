package com.example.kirill.inostudiotestproject.dbUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kirill.inostudiotestproject.objects.ListObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iiopok on 02.04.2015.
 */
public class ListAndDB {

    private Context context;


    public ListAndDB(Context ctx){
        context = ctx;
    }

    public void insertList(ListObject listObject){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", listObject.getName());
        cv.put("created", listObject.getCreated());

        db.insert("list", null, cv);
        dbHelper.close();
    }

    public void updateList(ListObject listObject){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", listObject.getName());
        cv.put("created", listObject.getCreated());

        db.update("list", cv, "id = ?", new String[]{String.valueOf(listObject.getId())});
        dbHelper.close();
    }

    public void deleteList(ListObject listObject){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("list", "id = " + listObject.getId(), null);
        dbHelper.close();
    }

    public List<ListObject> getLists(){
        ArrayList<ListObject> list = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("list", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            int idColIndex = cursor.getColumnIndex("id");
            int nameColIndex = cursor.getColumnIndex("name");
            int createdColIndex = cursor.getColumnIndex("created");
            do {
                ListObject listObject = new ListObject(cursor.getInt(idColIndex),
                        cursor.getString(nameColIndex),
                        cursor.getString(createdColIndex));
                list.add(listObject);
            }while (cursor.moveToNext());
        }
        cursor.close();
        dbHelper.close();
        return list;
    }

    public ListObject getList(int listId){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("list", null, "id = " + listId, null, null, null, null);

        int idColIndex = cursor.getColumnIndex("id");
        int nameColIndex = cursor.getColumnIndex("name");
        int createdColIndex = cursor.getColumnIndex("created");
        if(cursor.moveToFirst()){
            ListObject listObject = new ListObject(cursor.getInt(idColIndex),
                    cursor.getString(nameColIndex),
                    cursor.getString(createdColIndex));
            cursor.close();
            dbHelper.close();
            return listObject;
        }
        cursor.close();
        dbHelper.close();
        return null;

    }
}
