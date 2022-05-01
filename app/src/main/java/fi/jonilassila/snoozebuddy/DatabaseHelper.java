package fi.jonilassila.snoozebuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "people_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "sleepName";
    private static final String COL3 = "sleepDescription";
    private static final String COL4 = "sleepDurationMinutes";
    private static final String COL5 = "sleepDurationHours";
    private static final String COL6 = "sleepStartTime";
    private static final String COL7 = "sleepEndTime";


    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    /**
     * onCreate creates a table with columns (id,sleepName,sleepDescription,sleepDurationMinutes,
     * sleepDurationHours,sleepStartTime and sleepEndTime
     * @param db databaseHelper
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = ("CREATE TABLE " + TABLE_NAME
                + "(ID INTEGER PRIMARY KEY,"
                + COL2 + " TEXT,"
                + COL3 + " TEXT,"
                + COL4 + " NUMBER,"
                + COL5 + " NUMBER,"
                + COL6 + " TEXT,"
                        + COL7 + " TEXT)");

        db.execSQL(createTable);
    }

    /**
     * onUpgrade deletes table and creates a new one after
     * @param db databaseHelper
     * @param i ei kait käytössä
     * @param i1 ei kait käytössä
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * adds data to database table people_table
     * @param item sleep name
     * @param item2 sleep description
     * @param item3 sleep duration minutes
     * @param item4 sleep duration hours
     * @param item5 sleep start time
     * @param item6 sleep end time
     * @return true if data is correctly placed into database
     */
    public boolean addData(String item, String item2, long item3, long item4, String item5, String item6) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        contentValues.put(COL3, item2);
        contentValues.put(COL4, item3);
        contentValues.put(COL5, item4);
        contentValues.put(COL6, item5);
        contentValues.put(COL7, item6);

        Log.d(TAG, "addData: Add " + item + " to " + TABLE_NAME);

        //long result = db.insert(TABLE_NAME, COL1, contentValues);
        long result = db.insert(TABLE_NAME, null, contentValues);

        //If data is incorrectly placed returns -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Gets all data from the database people_table
     * returns all data from the database table people_table
     */
    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
