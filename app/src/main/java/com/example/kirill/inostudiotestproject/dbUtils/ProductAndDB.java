package com.example.kirill.inostudiotestproject.dbUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.kirill.inostudiotestproject.objects.ProductObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iiopok on 02.04.2015.
 */
public class ProductAndDB {

    private Context context;


    public ProductAndDB(Context ctx){
        context = ctx;
    }

    public void insertProduct(ProductObject productObject){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", productObject.getName());
        cv.put("listId", productObject.getListId());
        cv.put("isChecked", productObject.getIsChecked()?1:0);
        cv.put("number", productObject.getNumber());

        db.insert("product", null, cv);
        dbHelper.close();
    }

    public void updateProduct(ProductObject productObject){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", productObject.getName());
        cv.put("listId", productObject.getListId());
        cv.put("isChecked", productObject.getIsChecked()?1:0);
        cv.put("number", productObject.getNumber());

        db.update("product", cv, "id = ?", new String[]{String.valueOf(productObject.getId())});
        dbHelper.close();
    }

    public void deleteProduct(ProductObject productObject){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("product", "id = " + productObject.getId(), null);
        dbHelper.close();
    }

    public List<ProductObject> getProductsForList(int listId){
        ArrayList<ProductObject> list = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("product", null, "listId = " + listId, null, null, null, null);
        if(cursor.moveToFirst()){
            int idColIndex = cursor.getColumnIndex("id");
            int nameColIndex = cursor.getColumnIndex("name");
            int listIdColIndex = cursor.getColumnIndex("listId");
            int isCheckedColIndex = cursor.getColumnIndex("isChecked");
            int numberColIndex = cursor.getColumnIndex("number");
            do {
                ProductObject productObject = new ProductObject(cursor.getInt(idColIndex),
                        cursor.getString(nameColIndex),
                        cursor.getInt(listIdColIndex),
                        cursor.getInt(numberColIndex),
                        cursor.getInt(isCheckedColIndex)==1?true:false);
                list.add(productObject);
            }while (cursor.moveToNext());
        }
        cursor.close();
        dbHelper.close();
        return list;
    }

    public void deleteProductsForList(int listId){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("product", null, "listId = " + listId, null, null, null, null);
        if(cursor.moveToFirst()){
            int idColIndex = cursor.getColumnIndex("id");
            do {
                db.delete("product", "id = " + cursor.getInt(idColIndex), null);
            }while (cursor.moveToNext());
        }
        cursor.close();
        dbHelper.close();
    }

    public ProductObject getProduct(int productId) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("product", null, "id = " + productId, null, null, null, null);
        int idColIndex = cursor.getColumnIndex("id");
        int nameColIndex = cursor.getColumnIndex("name");
        int listIdColIndex = cursor.getColumnIndex("listId");
        int isCheckedColIndex = cursor.getColumnIndex("isChecked");
        int numberColIndex = cursor.getColumnIndex("number");
        if(cursor.moveToFirst()) {
            ProductObject productObject = new ProductObject(cursor.getInt(idColIndex),
                    cursor.getString(nameColIndex),
                    cursor.getInt(listIdColIndex),
                    cursor.getInt(numberColIndex),
                    cursor.getInt(isCheckedColIndex) == 1 ? true : false);

            cursor.close();
            dbHelper.close();
            return productObject;
        }
        cursor.close();
        dbHelper.close();
        return null;
    }
}
