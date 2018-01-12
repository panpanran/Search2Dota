package Database.DataAccessLayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Database.DBHelper;
import Database.Table.SearchTable;

/**
 * Created by panpanr on 6/27/2015.
 */
public class DALSearch {private DBHelper dbHelper;

    public DALSearch(Context context) {
        dbHelper = new DBHelper(context);
    }

    public List<SearchTable> SelectRecent() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
//                SearchTable.KEY_searchid + "," +
                SearchTable.KEY_steamid + "," +
                SearchTable.KEY_lasttime + "," +
                SearchTable.KEY_searchtimes +
                " FROM " + SearchTable.TABLE+
                " Order by " +SearchTable.KEY_lasttime+ " DESC LIMIT 10";// It's a good practice to use para
        List<SearchTable> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new SearchTable(
//                        cursor.getInt(cursor.getColumnIndex(SearchTable.KEY_searchid)),
                        cursor.getLong(cursor.getColumnIndex(SearchTable.KEY_steamid)),
                        cursor.getInt(cursor.getColumnIndex(SearchTable.KEY_searchtimes))));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public List<SearchTable> SelectAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
//                SearchTable.KEY_searchid + "," +
                SearchTable.KEY_steamid + "," +
                SearchTable.KEY_lasttime + "," +
                SearchTable.KEY_searchtimes +
                " FROM " + SearchTable.TABLE;// It's a good practice to use para
        SearchTable searchTable = new SearchTable();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor = db.rawQuery(selectQuery, null);
        List<SearchTable> list = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                searchTable = new SearchTable(
                        cursor.getInt(cursor.getColumnIndex(SearchTable.KEY_searchid)),
                        cursor.getLong(cursor.getColumnIndex(SearchTable.KEY_steamid)),
                        cursor.getInt(cursor.getColumnIndex(SearchTable.KEY_searchtimes)));
                list.add(searchTable);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public SearchTable SelectBySteamID(Long steamID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
//                SearchTable.KEY_searchid + "," +
                SearchTable.KEY_steamid + "," +
                SearchTable.KEY_lasttime + "," +
                SearchTable.KEY_searchtimes +
                " FROM " + SearchTable.TABLE
                + " WHERE " +
                SearchTable.KEY_steamid + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        SearchTable searchTable = new SearchTable();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(steamID)});

        if (cursor.moveToFirst()) {
            do {
                searchTable = new SearchTable(
//                        cursor.getInt(cursor.getColumnIndex(SearchTable.KEY_searchid)),
                        cursor.getLong(cursor.getColumnIndex(SearchTable.KEY_steamid)),
                        cursor.getInt(cursor.getColumnIndex(SearchTable.KEY_searchtimes)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return searchTable;
    }

    public int insert(SearchTable searchTable) {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Inserting Row
        long steamId = db.insert(SearchTable.TABLE, null, searchTable.contentValues);
        db.close(); // Closing database connection
        return (int) steamId;
    }

    public void delete(String steamID) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(SearchTable.TABLE, SearchTable.KEY_steamid + "= ?", new String[] { String.valueOf(steamID) });
        db.close(); // Closing database connection
    }

    public void update(SearchTable searchTable) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(SearchTable.TABLE, searchTable.contentValues, SearchTable.KEY_steamid + "= ?", new String[]{String.valueOf(searchTable.contentValues.get(SearchTable.KEY_steamid))});
        db.close(); // Closing database connection
    }
}
