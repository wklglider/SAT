package csci3320.kuilin.sat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuilin on 4/19/15.
 */
public class DatabaseOperations extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    public static final int database_version = 1;
    // Score Table Columns names
    public static final String USER_NAME = "user_name";
    public static final String USER_SCORE = "user_score";
    // Database Name
    public static final String DATABASE_NAME = "user_info";
    // Score table name
    public static final String TABLE_NAME = "reg_info";

    public DatabaseOperations(Context context) {
        super(context, DATABASE_NAME, null, database_version);
        Log.d("Database operations", "Database created");
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + USER_NAME + " TEXT," + USER_SCORE + " Ingeger );";
        db.execSQL(CREATE_SCORE_TABLE);
        Log.d("Database operations", "Table created");

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("Drop table if exists " + TABLE_NAME);
        // Create tables again
        onCreate(db);

    }

    // All Operations (Create, Read, Update, Delete)
    // Adding new user information
    public void putInformation(String name, int score) {
        SQLiteDatabase sql = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME, name); // User name
        cv.put(USER_SCORE, score); // User score
        // Inserting row
        sql.insert(TABLE_NAME, null, cv);
        // Closing database connection
        close();

    }

    // Getting all user information
    public List getInformation() {
        List scoreList = new ArrayList();
        // Select all query
        String selectQuery = "Select from " + TABLE_NAME;
        SQLiteDatabase sql = this.getReadableDatabase();
        Cursor cursor = sql.rawQuery(selectQuery, null);
        // Looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding contact to list
                scoreList.add(cursor.getString(0));
                scoreList.add(cursor.getInt(1));
            } while (cursor.moveToNext());
        }

        // return score list
        return scoreList;
    }
}
