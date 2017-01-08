package com.example.pr_idi.mydatabaseexample;

/**
 * FilmData
 * Created by pr_idi on 10/11/16.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class FilmData {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    // Here we only select Title and Director, must select the appropriate columns
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_DIRECTOR, MySQLiteHelper.COLUMN_COUNTRY,
            MySQLiteHelper.COLUMN_CRITICS_RATE,MySQLiteHelper.COLUMN_PROTAGONIST,
            MySQLiteHelper.COLUMN_YEAR_RELEASE};

    public FilmData(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Film createFilm(String title, String director, String country, String protagonist, int year, int rate) {
        ContentValues values = MySQLiteHelper.createValues(title, director, country, protagonist, year, rate);
        Log.d("Creating", "Creating " + title + " " + director);

        // Actual insertion of the data using the values variable
        long insertId = database.insert(MySQLiteHelper.TABLE_FILMS, null,
                values);

        // Main activity calls this procedure to create a new film
        // and uses the result to update the listview.
        // Therefore, we need to get the data from the database
        // (you can use this as a query example)
        // to feed the view.

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Film newFilm = cursorToFilm(cursor);

        // Do not forget to close the cursor
        cursor.close();

        // Return the book
        return newFilm;
    }

    public void updateRatingFilm(Film film, int rating) {
        film.setCritics_rate(rating);
        ContentValues contentValues = new ContentValues();
        contentValues.put(MySQLiteHelper.COLUMN_CRITICS_RATE, rating);

        String[] args = {Long.toString(film.getId())};
        database.update(MySQLiteHelper.TABLE_FILMS, contentValues, MySQLiteHelper.COLUMN_ID + " = ?", args);
    }

    public void deleteFilm(Film film) {
        long id = film.getId();
        System.out.println("Film deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_FILMS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Film> getFilms(String columnName, String name) {
        List<Film> films = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS, null, columnName + "= '" + name +"'", null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film film = cursorToFilm(cursor);
            films.add(film);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return films;
    }

    public List<Film> getFilmsContain(String columnName, String name, String order) {
        List<Film> films = new ArrayList<>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS, allColumns, columnName + " LIKE  '%" + name + "%'", null, null, null, order + " COLLATE NOCASE");
        //Cursor cursor = database.rawQuery("SELECT * FROM "+MySQLiteHelper.TABLE_FILMS+" WHERE "+columnName + " LIKE  '%" + name + "%' ORDER BY " +order+" COLLATE NOCASE;",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film film = cursorToFilm(cursor);
            films.add(film);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return films;
    }


    public Cursor getFilmsContain(String columnName, String selection) {
        String[] columns = {columnName};
        return database.query(true, MySQLiteHelper.TABLE_FILMS, columns, columnName + " LIKE '%" + selection + "%'", null, null, null, null, null);
    }

    public List<Film> getAllFilms(String order) {
        List<Film> comments = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FILMS,
                allColumns, null, null, null, null, order + " COLLATE NOCASE ASC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Film comment = cursorToFilm(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Film cursorToFilm(Cursor cursor) {
        Film film = new Film();
        film.setId(cursor.getLong(0));
        film.setTitle(cursor.getString(1));
        film.setDirector(cursor.getString(2));
        film.setCountry(cursor.getString(3));
        film.setYear(cursor.getInt(6));
        film.setProtagonist(cursor.getString(5));
        film.setCritics_rate(cursor.getInt(4));
        return film;
    }
}