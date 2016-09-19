package com.example.suketurastogi.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "inventory.db";
    public static final String TABLE_NAME = "products_table";

    public static final String COL_PRODUCT_NAME = "PRODUCT_NAME";
    public static final String COL_PRODUCT_PRICE = "PRODUCT_PRICE";
    public static final String COL_PRODUCT_QUANTITY = "PRODUCT_QUANTITY";
    public static final String COL_PRODUCT_SALE = "PRODUCT_SALE";
    public static final String COL_PRODUCT_IMAGE_PATH = "PRODUCT_IMAGE_PATH";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,PRODUCT_NAME TEXT,PRODUCT_PRICE INTEGER,PRODUCT_QUANTITY INTEGER,PRODUCT_SALE INTEGER,PRODUCT_IMAGE_PATH TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertData(String productName, Integer productPrice, Integer productQuantity, Integer productSale, String productImagePath) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PRODUCT_NAME, productName);
        contentValues.put(COL_PRODUCT_PRICE, productPrice);
        contentValues.put(COL_PRODUCT_QUANTITY, productQuantity);
        contentValues.put(COL_PRODUCT_SALE, productSale);
        contentValues.put(COL_PRODUCT_IMAGE_PATH, productImagePath);

        db.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor getAllData() {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);

        return res;
    }

    public boolean updateProductQuantity(String id, Integer productQuantity) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_PRODUCT_QUANTITY, productQuantity);

        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});

        return true;
    }

    public boolean updateProductSale(String id, Integer productQuantity, Integer productSale) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_PRODUCT_QUANTITY, productQuantity);
        contentValues.put(COL_PRODUCT_SALE, productSale);

        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});

        return true;
    }

    public Integer deleteData(String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});

    }
}