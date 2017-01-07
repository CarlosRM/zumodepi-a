package com.example.pr_idi.mydatabaseexample;

/**
 * MySQLiteHelper
 * Created by pr_idi on 10/11/16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_FILMS = "films";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_YEAR_RELEASE = "year_release";
    public static final String COLUMN_DIRECTOR = "director";
    public static final String COLUMN_PROTAGONIST = "protagonist";
    public static final String COLUMN_CRITICS_RATE = "critics_rate";

    private static final String DATABASE_NAME = "films.db";
    private static final int DATABASE_VERSION = 2;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_FILMS + "( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_COUNTRY + " text not null, "
            + COLUMN_YEAR_RELEASE + " integer not null, "
            + COLUMN_DIRECTOR + " text not null, "
            + COLUMN_PROTAGONIST + " text not null, "
            + COLUMN_CRITICS_RATE + " integer"
            + ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static ContentValues createValues(String title, String director, String country, String protagonist, int year, int rate) {
        ContentValues values = new ContentValues();

        // Add data: Note that this method only provides title and director
        // Must modify the method to add the full data
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DIRECTOR, director);

        // Invented data
        values.put(COLUMN_COUNTRY, country);
        values.put(COLUMN_YEAR_RELEASE, year);
        values.put(COLUMN_PROTAGONIST, protagonist);
        values.put(COLUMN_CRITICS_RATE, rate);

        return values;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        ContentValues values = createValues("Blade Runner", "Ridley Scott", "Mars", "Me", 3912, 1);
        database.insert(TABLE_FILMS, null,
                values);
        values = createValues("Rocky Horror Picture Show", "Jim Sharman", "Earth", "you", 2222, 2);
        database.insert(TABLE_FILMS, null,
                values);
        values = createValues("The Godfather", "Francis Ford Coppola", "Jupiter", "he", 4784, 3);
        database.insert(TABLE_FILMS, null,
                values);
        values = createValues("Toy Story", "John Lasseter", "Pluto", "it", 1201, 4);
        database.insert(TABLE_FILMS, null,
                values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILMS);
        onCreate(db);
    }

}