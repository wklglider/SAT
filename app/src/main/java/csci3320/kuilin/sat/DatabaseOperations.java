package csci3320.kuilin.sat;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kuilin on 4/19/15.
 */
public class DatabaseOperations extends SQLiteOpenHelper {
    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE " + TableData.TableInfo.TABLE_NAME + "(" + TableData.TableInfo.USER_NAME + " TEXT," + TableData.TableInfo.USER_SCORE + " TEXT );";

    public DatabaseOperations(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, TableData.TableInfo.DATABASE_NAME, null, database_version);
        Log.d("Database operations", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d("Database operations", "Table created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void putInfomation(DatabaseOperations dop, String name, String score) {
        SQLiteDatabase sql = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableData.TableInfo.USER_NAME, name);
        cv.put(TableData.TableInfo.USER_SCORE, score);
        long k = sql.insert(TableData.TableInfo.TABLE_NAME, null, cv);
        Log.d("Database operations", "One row inserted");

    }
}
