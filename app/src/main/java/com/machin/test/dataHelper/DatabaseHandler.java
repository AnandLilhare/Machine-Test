package com.machin.test.dataHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.machine.test.model.CartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anand on 30/11/16.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "product_shopping";

    // cart item table name
    private static final String TABLE_CONTACTS = "cart_item";

    // cart_item Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "product_name";
    private static final String KEY_PRICE = "product_price";
    private static final String KEY_VENDOR = "vendor_name";
    private static final String KEY_VENDOR_ADDRESS = "vendor_address";
    private static final String KEY_VENDOR_PHONE = "phone_number";
    private static final String KEY_IMAGE = "product_image";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_PRICE + " TEXT,"
                + KEY_VENDOR + " TEXT," + KEY_VENDOR_ADDRESS + " TEXT," + KEY_VENDOR_PHONE + " TEXT," + KEY_IMAGE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new cart item
    public void addCart(CartItem cartItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, cartItem.getProductname());
        values.put(KEY_PRICE, cartItem.getPrice());
        values.put(KEY_VENDOR, cartItem.getVendorname());
        values.put(KEY_VENDOR_ADDRESS, cartItem.getVendoraddress());
        values.put(KEY_VENDOR_PHONE, cartItem.getPhoneNumber());
        values.put(KEY_IMAGE, cartItem.getProductImg());

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }


    // Getting All Cart Item
    public List<CartItem> getAllCartItem() {
        List<CartItem> cartList = new ArrayList<CartItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CartItem cartItem = new CartItem();
                cartItem.setId(Integer.parseInt(cursor.getString(0)));
                cartItem.setProductname(cursor.getString(1));
                cartItem.setPrice(cursor.getString(2));
                cartItem.setVendorname(cursor.getString(3));
                cartItem.setVendoraddress(cursor.getString(4));
                cartItem.setPhoneNumber(cursor.getString(5));
                cartItem.setProductImg(cursor.getString(6));
                // Adding contact to list
                cartList.add(cartItem);
            } while (cursor.moveToNext());
        }

        // return contact list
        return cartList;
    }


    // Deleting single cart item
    public void deleteContact(CartItem cartItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(cartItem.getId())});
        db.close();
    }


    // Getting cart item Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Getting cart item Count
    public int getTotalPrices() {
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        int totalprices = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = cursor.getCount();
        if (count != 0) {
            if (cursor.moveToFirst()) {
                do {
                    try {
                        totalprices = totalprices + Integer.parseInt(cursor.getString(2));
                    } catch (Exception e) {
                        totalprices = 0;
                    }


                } while (cursor.moveToNext());
            }

        }
        return totalprices;
    }
}


