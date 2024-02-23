package Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.personnelcuisine.Activity.FoodDataModel;

import java.util.ArrayList;
import java.util.List;

public class CDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FoodDatabase";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_Food = "food";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DESCRIPTION = "description";

    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_IMAGE = "image";


    private static final String DATABASE_CREATE = "create table "
            + TABLE_Food + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME
            + " text not null, " + COLUMN_PRICE + " integer not null, "
            + COLUMN_DESCRIPTION + " text not null, "
            + COLUMN_TYPE + " text not null, "
            + COLUMN_IMAGE + " blob  not null);";

    public CDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
        Log.d("DbHelper", "Database created successfully");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Food);
        onCreate(db);
    }

    //dbHelper.addFood(name, Price, description, Type, imageByteArray);
    public void addFood(String name, int Price, String description, String Type, byte[] image) {
        Log.d("DbHelper", "User Added successfully");

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, Price);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_TYPE, Type);
        values.put(COLUMN_IMAGE, image);

        // Inserting Row
        database.insert(TABLE_Food, null, values);
        database.close();
        Log.d("DbHelper", "User Added successfully");


    }



    public List<FoodDataModel> getAllFood() {
        List<FoodDataModel> foods = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = database.query(TABLE_Food, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    FoodDataModel food = new FoodDataModel();
                    food.setId(cursor.getLong(0));
                    food.setName(cursor.getString(1));
                    food.setPrice(cursor.getInt(2));
                    food.setDescription(cursor.getString(3));
                    food.setType(cursor.getString(4));

                    // Retrieve the image as byte array
                    byte[] imageByteArray = cursor.getBlob(5);
                    food.setImage(imageByteArray);

                    foods.add(food);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close(); // Close the cursor
            if (database != null) database.close(); // Close the database
        }

        return foods;
    }




    public  void deletefoodbyId(String task_Id)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        db.delete(TABLE_Food,COLUMN_ID+"=?",new String[]{task_Id});
        db.close();
    }

    public synchronized void close() {
        super.close();
        // Close any other resources here if needed
    }

    public void updateFood(long id, String name, int price, String description, String type, byte[] image) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_TYPE, type);
        values.put(COLUMN_IMAGE, image);

        database.update(TABLE_Food, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        database.close();
    }
    public FoodDataModel getFoodById(long id) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;
        FoodDataModel food = null;

        try {
            cursor = database.query(TABLE_Food, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                food = new FoodDataModel();
                food.setId(cursor.getLong(0));
                food.setName(cursor.getString(1));
                food.setPrice(cursor.getInt(2));
                food.setDescription(cursor.getString(3));
                food.setType(cursor.getString(4));

                // Retrieve the image as byte array
                byte[] imageByteArray = cursor.getBlob(5);
                food.setImage(imageByteArray);
            }
        }
        finally {
            if (cursor != null) cursor.close(); // Close the cursor
            if (database != null) database.close(); // Close the database
        }


        return food;
    }
    public List<FoodDataModel> getFoodByType(String foodType) {
        List<FoodDataModel> foods = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String selection = COLUMN_TYPE + "=?";
            String[] selectionArgs = { foodType };

            cursor = database.query(TABLE_Food, null, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    FoodDataModel food = new FoodDataModel();
                    food.setId(cursor.getLong(0));
                    food.setName(cursor.getString(1));
                    food.setPrice(cursor.getInt(2));
                    food.setDescription(cursor.getString(3));
                    food.setType(cursor.getString(4));

                    // Retrieve the image as byte array
                    byte[] imageByteArray = cursor.getBlob(5);
                    food.setImage(imageByteArray);

                    foods.add(food);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close(); // Close the cursor
            if (database != null) database.close(); // Close the database
        }

        return foods;
    }


}
