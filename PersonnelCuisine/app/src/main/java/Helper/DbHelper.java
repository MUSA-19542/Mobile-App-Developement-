package Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.personnelcuisine.Activity.UserDataModel;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserDatabase";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_IMAGE = "image";

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_USERS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME
            + " text not null, " + COLUMN_EMAIL + " text not null, "
            + COLUMN_ADDRESS + " text not null, "
            + COLUMN_PASSWORD + " text not null, "
            + COLUMN_IMAGE + " blob not null);";

    public DbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        Log.d("DbHelper", "Database created successfully");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(String name, String email, String password, String address, byte[] image) {
        Log.d("DbHelper", "User Added successfully");

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_IMAGE, image);

        // Inserting Row
        database.insert(TABLE_USERS, null, values);
        database.close();
        Log.d("DbHelper", "User Added successfully");


    }


    public List<UserDataModel> getAllUsers() {
        List<UserDataModel> users = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = database.query(TABLE_USERS, null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    UserDataModel user = new UserDataModel();
                    user.setId(cursor.getLong(cursor.getColumnIndexOrThrow("id")));
                    user.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                    user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                    user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
                    user.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));
                    user.setImage(cursor.getBlob(cursor.getColumnIndexOrThrow("image"))); // Assuming "image" is the column name for the image
                    users.add(user);
                } while (cursor.moveToNext());
            }
        }
        finally {
            if (cursor != null) cursor.close(); // Close the cursor
            if (database != null) database.close(); // Close the database
        }

        return users;
    }


    public boolean signIn(String email, String password) {
        SQLiteDatabase database = this.getReadableDatabase();

        // Define the columns you want to retrieve
        String[] projection = {
                COLUMN_ID
        };

        // Define the selection criteria
        String selection = COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {email, password};

        // Query the database
        Cursor cursor = database.query(
                TABLE_USERS,  // The table to query
                projection,   // The array of columns to return (null means all)
                selection,    // The columns for the WHERE clause
                selectionArgs,  // The values for the WHERE clause
                null,         // Don't group the rows
                null,         // Don't filter by row groups
                null          // The sort order
        );

        // If cursor is not empty, it means a matching user exists
        boolean signInSuccessful = cursor.getCount() > 0;

        // Close the cursor and database
        cursor.close();
        database.close();

        // Return whether sign-in was successful
        return signInSuccessful;
    }

    public long getUserIdByEmail(String email) {
        long userId = -1; // Default value if user is not found

        SQLiteDatabase database = this.getReadableDatabase();
        String[] projection = {COLUMN_ID};
        String selection = COLUMN_EMAIL + "=?";
        String[] selectionArgs = {email};

        Cursor cursor = database.query(TABLE_USERS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getLong(0);
            cursor.close();
        }
        database.close();
        return userId;
    }


    public UserDataModel getUserById(long userId) {
        SQLiteDatabase database = this.getReadableDatabase();
        UserDataModel user = null;

        // Define the columns you want to retrieve
        String[] projection = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_EMAIL,
                COLUMN_PASSWORD,
                COLUMN_ADDRESS,
                COLUMN_IMAGE
        };

        // Define the selection criteria
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        // Query the database
        Cursor cursor = database.query(
                TABLE_USERS,  // The table to query
                projection,   // The array of columns to return
                selection,    // The columns for the WHERE clause
                selectionArgs,  // The values for the WHERE clause
                null,         // Don't group the rows
                null,         // Don't filter by row groups
                null          // The sort order
        );

        // If cursor is not empty, move to the first row and create the user object
        if (cursor != null && cursor.moveToFirst()) {
            user = new UserDataModel();
            user.setId(cursor.getLong(0));
            user.setName(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setAddress(cursor.getString(4));
            user.setImage(cursor.getBlob(5));
            cursor.close();
        }

        database.close();

        return user;
    }
    public void updateUser(long userId, String name, String email, String password, String address, byte[] image) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_IMAGE, image);

        // Define the selection criteria
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        // Update the row
        int count = database.update(TABLE_USERS, values, selection, selectionArgs);
        database.close();

        if (count == 0) {
            Log.d("DbHelper", "User with ID " + userId + " not found");
        } else {
            Log.d("DbHelper", "User with ID " + userId + " updated successfully");
        }

        database.close();
    }


}
