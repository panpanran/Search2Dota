package Database.DataAccessLayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Database.DBHelper;
import Database.Table.FriendTable;
import Database.Table.MatchTable;
import Database.Table.PlayerTable;
import Database.Table.UserTable;

/**
 * Created by panpanr on 7/12/2015.
 */
public class DALMatch {
    private DBHelper dbHelper;

    public DALMatch(Context context) {
        dbHelper = new DBHelper(context);
    }

    public MatchTable SelectBymatchID(Long matchID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                MatchTable.KEY_matchid + "," +
                MatchTable.KEY_starttime + "," +
                MatchTable.KEY_lobby + "," +
                MatchTable.KEY_mode + "," +
                MatchTable.KEY_result + "," +
                MatchTable.KEY_duration +
                " FROM " + MatchTable.TABLE
                + " WHERE " +
                MatchTable.KEY_matchid + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        MatchTable matchTable = new MatchTable();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(matchID)});

        if (cursor.moveToFirst()) {
            do {
                matchTable = new MatchTable(
                        cursor.getLong(cursor.getColumnIndex(MatchTable.KEY_matchid)),
                        cursor.getString(cursor.getColumnIndex(MatchTable.KEY_starttime)),
                        cursor.getString(cursor.getColumnIndex(MatchTable.KEY_lobby)),
                        cursor.getString(cursor.getColumnIndex(MatchTable.KEY_mode)),
                        cursor.getLong(cursor.getColumnIndex(MatchTable.KEY_duration)),
                        cursor.getString(cursor.getColumnIndex(MatchTable.KEY_result)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return matchTable;
    }

    public int insert(MatchTable matchTable) {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Inserting Row
        long matchId = db.insert(MatchTable.TABLE, null, matchTable.contentValues);
        db.close(); // Closing database connection
        return (int) matchId;
    }

    public void delete(Long matchID) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(MatchTable.TABLE, MatchTable.KEY_matchid + "= ?", new String[]{String.valueOf(matchID)});
        db.close(); // Closing database connection
    }

    public void update(MatchTable matchTable) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(MatchTable.TABLE, matchTable.contentValues, MatchTable.KEY_matchid + "= ?", new String[]{String.valueOf(matchTable.contentValues.get(MatchTable.KEY_matchid))});
        db.close(); // Closing database connection
    }
}
