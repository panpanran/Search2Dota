package Database.DataAccessLayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;

import Database.DBHelper;
import Database.Table.FriendTable;
import Database.Table.UserTable;
import Database.Table.UserTable;

/**
 * Created by panpanr on 6/21/2015.
 */
public class DALUser {
    private DBHelper dbHelper;

    public DALUser(Context context) {
        dbHelper = new DBHelper(context);
    }

    public UserTable SelectBySteamIDByStatusByName(Long steamID, String status, String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        UserTable userTable = new UserTable();
        String where = UserTable.KEY_steamid + "= ? and (" + UserTable.KEY_status + " like '%" + status + "%' or " + UserTable.KEY_name + " like '%" + name + "%')";
        String[] whereArgs = new String[]{String.valueOf(steamID)};
        String selectQuery = "SELECT " +
                UserTable.KEY_steamid + "," +
                UserTable.KEY_visibility + "," +
                UserTable.KEY_name + "," +
                UserTable.KEY_status + "," +
                UserTable.KEY_profileurl + "," +
                UserTable.KEY_realname + "," +
                UserTable.KEY_country + "," +
                UserTable.KEY_lastlogin + "," +
                UserTable.KEY_timecreated +
                " FROM " + UserTable.TABLE
                + " WHERE " +
                where;// It's a good practice to use parameter ?, instead of concatenate string


        Cursor cursor = db.rawQuery(selectQuery, whereArgs);

        if (cursor.moveToFirst()) {
            do {
                userTable = new UserTable(
                        cursor.getLong(cursor.getColumnIndex(UserTable.KEY_steamid)),
                        cursor.getString(cursor.getColumnIndex(UserTable.KEY_visibility)),
                        cursor.getString(cursor.getColumnIndex(UserTable.KEY_name)),
                        cursor.getString(cursor.getColumnIndex(UserTable.KEY_lastlogin)),
                        cursor.getString(cursor.getColumnIndex(UserTable.KEY_status)),
                        cursor.getString(cursor.getColumnIndex(UserTable.KEY_profileurl)),
                        cursor.getString(cursor.getColumnIndex(UserTable.KEY_realname)),
                        cursor.getString(cursor.getColumnIndex(UserTable.KEY_timecreated)),
                        cursor.getString(cursor.getColumnIndex(UserTable.KEY_country)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userTable;
    }

    public UserTable SelectBySteamID(Long steamID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                UserTable.KEY_steamid + "," +
                UserTable.KEY_visibility + "," +
                UserTable.KEY_name + "," +
                UserTable.KEY_status + "," +
                UserTable.KEY_profileurl + "," +
                UserTable.KEY_realname + "," +
                UserTable.KEY_country + "," +
                UserTable.KEY_lastlogin + "," +
                UserTable.KEY_timecreated +
                " FROM " + UserTable.TABLE
                + " WHERE " +
                UserTable.KEY_steamid + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        UserTable userTable = new UserTable();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(steamID)});

        if (cursor.moveToFirst()) {
            do {
                userTable = new UserTable(
                        cursor.getLong(cursor.getColumnIndex(UserTable.KEY_steamid)),
                        cursor.getString(cursor.getColumnIndex(UserTable.KEY_visibility)),
                        cursor.getString(cursor.getColumnIndex(UserTable.KEY_name)),
                        cursor.getString(cursor.getColumnIndex(UserTable.KEY_lastlogin)),
                        cursor.getString(cursor.getColumnIndex(UserTable.KEY_status)),
                        cursor.getString(cursor.getColumnIndex(UserTable.KEY_profileurl)),
                        cursor.getString(cursor.getColumnIndex(UserTable.KEY_realname)),
                        cursor.getString(cursor.getColumnIndex(UserTable.KEY_timecreated)),
                        cursor.getString(cursor.getColumnIndex(UserTable.KEY_country)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userTable;
    }

    public int insert(UserTable userTable) {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Inserting Row
        long steamId = db.insert(UserTable.TABLE, null, userTable.contentValues);
        db.close(); // Closing database connection
        return (int) steamId;
    }

    public void delete(Long steamID) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(UserTable.TABLE, UserTable.KEY_steamid + "= ?", new String[]{String.valueOf(steamID)});
        db.close(); // Closing database connection
    }

    public void update(UserTable userTable) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(UserTable.TABLE, userTable.contentValues, UserTable.KEY_steamid + "= ?", new String[]{String.valueOf(userTable.contentValues.get(UserTable.KEY_steamid))});
        db.close(); // Closing database connection
    }
}
