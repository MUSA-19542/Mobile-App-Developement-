package Helper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Domain.CartModel;

public class CartDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cart_database";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_CART = "cart";
    private static final String KEY_ID = "id";
    private static final String KEY_FOOD_ID = "food_id";
    private static final String KEY_ITEM_NAME = "item_name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_NUMBER_ORDER = "number_order";

    public CartDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_FOOD_ID + " INTEGER," +
                KEY_ITEM_NAME + " TEXT," +
                KEY_PRICE + " INTEGER," +
                KEY_IMAGE + " BLOB," +
                KEY_NUMBER_ORDER + " INTEGER" +
                ")";
        db.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    public void addItemToCart(CartModel cartItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FOOD_ID, cartItem.getFood_id());
        values.put(KEY_ITEM_NAME, cartItem.getItem_name());
        values.put(KEY_PRICE, cartItem.getPrice());
        values.put(KEY_IMAGE, cartItem.getImage());
        values.put(KEY_NUMBER_ORDER, cartItem.getNumberOrder());
        db.insert(TABLE_CART, null, values);
        db.close();
    }

    public List<CartModel> getAllItems() {
        List<CartModel> itemList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CART;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                CartModel cartItem = new CartModel(
                        cursor.getLong(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getBlob(4),
                        cursor.getInt(5)
                );
                itemList.add(cartItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }

    public int getNumberOfOrders(long foodId) {
        int numberOfOrders = 0;
        String selectQuery = "SELECT " + KEY_NUMBER_ORDER + " FROM " + TABLE_CART +
                " WHERE " + KEY_FOOD_ID + " = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(foodId)});
        if (cursor.moveToFirst()) {
            numberOfOrders = cursor.getInt(cursor.getColumnIndex(KEY_NUMBER_ORDER));
        }
        cursor.close();
        db.close();
        return numberOfOrders;
    }

    public void setNumberOfOrders(long foodId, int numberOfOrders) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NUMBER_ORDER, numberOfOrders);
        db.update(TABLE_CART, values,
                KEY_FOOD_ID + " = ?",
                new String[]{String.valueOf(foodId)});
        db.close();
    }

    public void deleteCartItem(long foodId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART,
                KEY_FOOD_ID + " = ?",
                new String[]{String.valueOf(foodId)});
        db.close();
    }

    public boolean existAlready(long foodId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_CART +
                " WHERE " + KEY_FOOD_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(foodId)});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }
    public Double getTotalFee() {
        SQLiteDatabase db = this.getReadableDatabase();
        Double totalFee = 0.0;

        String query = "SELECT * FROM " + TABLE_CART;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int price = cursor.getInt(cursor.getColumnIndex(KEY_PRICE));
                int numberOrder = cursor.getInt(cursor.getColumnIndex(KEY_NUMBER_ORDER));
                totalFee += (price * numberOrder);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return totalFee;
    }

    public int getTotalItemCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalCount = 0;

        String query = "SELECT SUM(" + KEY_NUMBER_ORDER + ") FROM " + TABLE_CART;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            totalCount = cursor.getInt(0);
        }

        cursor.close();
        return totalCount;
    }

    public void setPriceForFood(long foodId, int price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PRICE, price);
        db.update(TABLE_CART, values,
                KEY_FOOD_ID + " = ?",
                new String[]{String.valueOf(foodId)});
        db.close();
    }


}

