package com.example.foodlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "food_db";
    private static final int DATABASE_VERSION = 1;

    // Table name and column names
    public static final String TABLE_FOOD = "food";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_IMAGE_URI = "image_uri";

    // Create table statement


    public DbHelper(@Nullable  Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE " + TABLE_FOOD + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_NUMBER + " TEXT, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_PRICE + " TEXT, " +
                COLUMN_IMAGE_URI + " TEXT" +
                ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed and recreate the table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        onCreate(db);
    }

    public void addFood(FoodModel food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, food.getName());
        values.put(COLUMN_DESCRIPTION, food.getDescription());
        values.put(COLUMN_NUMBER, food.getNumber());
        values.put(COLUMN_LOCATION, food.getLocation());
        values.put(COLUMN_PRICE, food.getPrice());
        values.put(String.valueOf(COLUMN_IMAGE_URI), food.getImageUri());
        db.insert(TABLE_FOOD, null, values);
        db.close();
    }
    public void updateFormData(FoodModel food, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, food.getName());
        values.put(COLUMN_DESCRIPTION, food.getDescription());
        values.put(COLUMN_NUMBER, food.getNumber());
        values.put(COLUMN_LOCATION, food.getLocation());
        values.put(COLUMN_PRICE, food.getPrice());
        values.put(COLUMN_IMAGE_URI, food.getImageUri());
        db.update(TABLE_FOOD, values, COLUMN_ID + "=?", new String[]{String.valueOf(food.getId())}); // Corrected here
        db.close();
        Toast.makeText(context, "Data Updated Successfully", Toast.LENGTH_LONG).show();
    }


    // Method to get all food items
    public List<FoodModel> getAllFood() {
        List<FoodModel> foodList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_FOOD;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                FoodModel food = new FoodModel( cursor.getLong(0),cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4),cursor.getString(5),cursor.getString(6));

                foodList.add(food);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foodList;
    }


    public  void deletetaskbyId(String task_Id)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        db.delete(TABLE_FOOD,COLUMN_ID+"=?",new String[]{task_Id});
        db.close();
    }

    public FoodModel getFoodById(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_NUMBER, COLUMN_LOCATION, COLUMN_PRICE, COLUMN_IMAGE_URI};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.query(TABLE_FOOD, columns, selection, selectionArgs, null, null, null);
        FoodModel food = null;
        if (cursor != null && cursor.moveToFirst()) {
            food = new FoodModel(cursor.getLong(0),
                    cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4),cursor.getString(5),cursor.getString(6)
            );
            cursor.close();
        }
        db.close();
        return food;
    }




}
