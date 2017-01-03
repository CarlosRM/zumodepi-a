package com.example.pr_idi.mydatabaseexample;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

public class Predictor extends ContentProvider {

    private FilmData filmData;
    private static final String[] columns = {"_ID", SearchManager.SUGGEST_COLUMN_TEXT_1, SearchManager.SUGGEST_COLUMN_INTENT_DATA};

    public Predictor() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        filmData = new FilmData(getContext());
        filmData.open();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        System.out.println("Query " + selectionArgs[0]);
        MatrixCursor searchResult = new MatrixCursor(columns);
        if (selectionArgs[0].length() > 0) {
            Cursor cursor = filmData.getFilmsContain(MySQLiteHelper.COLUMN_PROTAGONIST, selectionArgs[0]);
            cursor.moveToFirst();
            int id = 0;
            while (!cursor.isAfterLast()) {
                Object[] columnValues = {id, cursor.getString(0), cursor.getString(0)};
                System.out.println(cursor.getString(0));
                searchResult.addRow(columnValues);
                cursor.moveToNext();
                ++id;
            }
        }
        return searchResult;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
