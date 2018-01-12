package Database.DataAccessLayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Database.DBHelper;
import Database.Table.FriendTable;

/**
 * Created by panpanr on 6/28/2015.
 */
public class DALFriend {
    private DBHelper dbHelper;

    public DALFriend(Context context) {
        dbHelper = new DBHelper(context);
    }

    public List<FriendTable> SelectAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                FriendTable.KEY_friendid + "," +
                FriendTable.KEY_steamid + "," +
                FriendTable.KEY_friendsteamid + "," +
                FriendTable.KEY_relationship + "," +
                FriendTable.KEY_friend_since +
                " FROM " + FriendTable.TABLE;// It's a good practice to use parameter ?, instead of concatenate string

        List<FriendTable> list = new ArrayList();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new FriendTable(
                        cursor.getInt(cursor.getColumnIndex(FriendTable.KEY_friendid)),
                        cursor.getLong(cursor.getColumnIndex(FriendTable.KEY_steamid)),
                        cursor.getLong(cursor.getColumnIndex(FriendTable.KEY_friendsteamid)),
                        cursor.getString(cursor.getColumnIndex(FriendTable.KEY_relationship)),
                        cursor.getLong(cursor.getColumnIndex(FriendTable.KEY_friend_since))));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public List<FriendTable> SelectListBySteamID(Long steamID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                FriendTable.KEY_friendid + "," +
                FriendTable.KEY_steamid + "," +
                FriendTable.KEY_friendsteamid + "," +
                FriendTable.KEY_relationship + "," +
                FriendTable.KEY_friend_since +
                " FROM " + FriendTable.TABLE
                +  " WHERE " +
                FriendTable.KEY_steamid + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        List<FriendTable> list = new ArrayList();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(steamID)});

        if (cursor.moveToFirst()) {
            do {
                list.add(new FriendTable(
                        cursor.getInt(cursor.getColumnIndex(FriendTable.KEY_friendid)),
                        cursor.getLong(cursor.getColumnIndex(FriendTable.KEY_steamid)),
                        cursor.getLong(cursor.getColumnIndex(FriendTable.KEY_friendsteamid)),
                        cursor.getString(cursor.getColumnIndex(FriendTable.KEY_relationship)),
                        cursor.getLong(cursor.getColumnIndex(FriendTable.KEY_friend_since))));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public int insert(FriendTable friendTable) {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Inserting Row
        long steamId = db.insert(FriendTable.TABLE, null, friendTable.contentValues);
        db.close(); // Closing database connection
        return (int) steamId;
    }

    public void delete(FriendTable friendTable) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        String where = FriendTable.KEY_steamid + "= ? and " + FriendTable.KEY_friendsteamid + "=?";
        String[] whereArgs = new String[] {String.valueOf(friendTable.contentValues.get(FriendTable.KEY_steamid)), String.valueOf(friendTable.contentValues.get(FriendTable.KEY_friendsteamid))};
        db.delete(FriendTable.TABLE, where, whereArgs);
        db.close(); // Closing database connection
    }

    public void update(FriendTable friendTable) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        String where = FriendTable.KEY_steamid + "= ? and " + FriendTable.KEY_friendsteamid + "=?";
        String[] whereArgs = new String[] {String.valueOf(friendTable.contentValues.get(FriendTable.KEY_steamid)), String.valueOf(friendTable.contentValues.get(FriendTable.KEY_friendsteamid))};
        db.update(FriendTable.TABLE, friendTable.contentValues, where, whereArgs);
        db.close(); // Closing database connection
    }
}
