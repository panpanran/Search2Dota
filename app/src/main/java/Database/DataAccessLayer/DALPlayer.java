package Database.DataAccessLayer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Database.DBHelper;
import Database.Table.PlayerTable;

/**
 * Created by panpanr on 8/2/2015.
 */
public class DALPlayer {
    private DBHelper dbHelper;

    public DALPlayer(Context context) {
        dbHelper = new DBHelper(context);
    }

    public PlayerTable SelectListByaccountIDByMostRecent(String accountID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                PlayerTable.KEY_playerid + "," +
                PlayerTable.KEY_matchid + "," +
                PlayerTable.KEY_accountid + "," +
                PlayerTable.KEY_hero + "," +
                PlayerTable.Key_radiant + "," +
                PlayerTable.Key_level + "," +
                PlayerTable.Key_herodamage + "," +
                PlayerTable.Key_towerdamage + "," +
                PlayerTable.Key_gold + "," +
                PlayerTable.Key_kills + "," +
                PlayerTable.Key_deaths + "," +
                PlayerTable.Key_assists +
                " FROM " + PlayerTable.TABLE
                + " WHERE " +
                PlayerTable.KEY_accountid + " = '"+String.valueOf(accountID)+"' Order by " + PlayerTable.KEY_matchid+ " DESC Limit 1";;// It's a good practice to use parameter ?, instead of concatenate string

        PlayerTable matchTable = new PlayerTable();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                matchTable = new PlayerTable(
                        cursor.getInt(cursor.getColumnIndex(PlayerTable.KEY_playerid)),
                        cursor.getLong(cursor.getColumnIndex(PlayerTable.KEY_matchid)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.KEY_accountid)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.KEY_hero)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_radiant)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_level)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_herodamage)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_towerdamage)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_gold)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_kills)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_deaths)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_assists)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return matchTable;
    }

    public List<PlayerTable> SelectListByaccountID(String accountID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                PlayerTable.KEY_playerid + "," +
                PlayerTable.KEY_matchid + "," +
                PlayerTable.KEY_accountid + "," +
                PlayerTable.KEY_hero + "," +
                PlayerTable.Key_radiant + "," +
                PlayerTable.Key_level + "," +
                PlayerTable.Key_herodamage + "," +
                PlayerTable.Key_towerdamage + "," +
                PlayerTable.Key_gold + "," +
                PlayerTable.Key_kills + "," +
                PlayerTable.Key_deaths + "," +
                PlayerTable.Key_assists +
                " FROM " + PlayerTable.TABLE
                + " WHERE " +
                PlayerTable.KEY_accountid + " = '"+String.valueOf(accountID)+"' Order by " + PlayerTable.KEY_matchid+ " DESC";;// It's a good practice to use parameter ?, instead of concatenate string

        List<PlayerTable> list = new ArrayList();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new PlayerTable(
                        cursor.getInt(cursor.getColumnIndex(PlayerTable.KEY_playerid)),
                        cursor.getLong(cursor.getColumnIndex(PlayerTable.KEY_matchid)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.KEY_accountid)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.KEY_hero)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_radiant)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_level)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_herodamage)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_towerdamage)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_gold)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_kills)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_deaths)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_assists))));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public PlayerTable SelectListBymatchIDByaccountID(Long matchID, String accountID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String where =PlayerTable.KEY_matchid + " = "+ String.valueOf(matchID)+ " and " + PlayerTable.KEY_accountid + " = '"+ String.valueOf(accountID) + "'";
        String selectQuery = "SELECT  " +
                PlayerTable.KEY_playerid + "," +
                PlayerTable.KEY_matchid + "," +
                PlayerTable.KEY_accountid + "," +
                PlayerTable.KEY_hero + "," +
                PlayerTable.Key_radiant + "," +
                PlayerTable.Key_level + "," +
                PlayerTable.Key_herodamage + "," +
                PlayerTable.Key_towerdamage + "," +
                PlayerTable.Key_gold + "," +
                PlayerTable.Key_kills + "," +
                PlayerTable.Key_deaths + "," +
                PlayerTable.Key_assists +
                " FROM " + PlayerTable.TABLE
                + " WHERE " +
                where;// It's a good practice to use parameter ?, instead of concatenate string

        PlayerTable matchTable = new PlayerTable();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                matchTable = new PlayerTable(
                        cursor.getInt(cursor.getColumnIndex(PlayerTable.KEY_playerid)),
                        cursor.getLong(cursor.getColumnIndex(PlayerTable.KEY_matchid)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.KEY_accountid)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.KEY_hero)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_radiant)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_level)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_herodamage)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_towerdamage)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_gold)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_kills)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_deaths)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_assists)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return matchTable;
    }

    public List<PlayerTable> SelectListBymatchID(Long matchID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                PlayerTable.KEY_playerid + "," +
                PlayerTable.KEY_matchid + "," +
                PlayerTable.KEY_accountid + "," +
                PlayerTable.KEY_hero + "," +
                PlayerTable.Key_radiant + "," +
                PlayerTable.Key_level + "," +
                PlayerTable.Key_herodamage + "," +
                PlayerTable.Key_towerdamage + "," +
                PlayerTable.Key_gold + "," +
                PlayerTable.Key_kills + "," +
                PlayerTable.Key_deaths + "," +
                PlayerTable.Key_assists +
                " FROM " + PlayerTable.TABLE
                + " WHERE " +
                PlayerTable.KEY_matchid + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        List<PlayerTable> list = new ArrayList();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(matchID)});

        if (cursor.moveToFirst()) {
            do {
                list.add(new PlayerTable(
                        cursor.getInt(cursor.getColumnIndex(PlayerTable.KEY_playerid)),
                        cursor.getLong(cursor.getColumnIndex(PlayerTable.KEY_matchid)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.KEY_accountid)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.KEY_hero)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_radiant)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_level)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_herodamage)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_towerdamage)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_gold)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_kills)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_deaths)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_assists))));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public PlayerTable SelectListByplayerid(int playerid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                PlayerTable.KEY_playerid + "," +
                PlayerTable.KEY_matchid + "," +
                PlayerTable.KEY_accountid + "," +
                PlayerTable.KEY_hero + "," +
                PlayerTable.Key_radiant + "," +
                PlayerTable.Key_level + "," +
                PlayerTable.Key_herodamage + "," +
                PlayerTable.Key_towerdamage + "," +
                PlayerTable.Key_gold + "," +
                PlayerTable.Key_kills + "," +
                PlayerTable.Key_deaths + "," +
                PlayerTable.Key_assists +
                " FROM " + PlayerTable.TABLE
                + " WHERE " +
                PlayerTable.KEY_playerid + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        PlayerTable playerTable = new PlayerTable();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(playerid)});

        if (cursor.moveToFirst()) {
            do {
                playerTable = new PlayerTable(
                        cursor.getInt(cursor.getColumnIndex(PlayerTable.KEY_playerid)),
                        cursor.getLong(cursor.getColumnIndex(PlayerTable.KEY_matchid)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.KEY_accountid)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.KEY_hero)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_radiant)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_level)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_herodamage)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_towerdamage)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_gold)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_kills)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_deaths)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_assists)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return playerTable;
    }

    public PlayerTable SelectMaxByMatch(String field,int matchID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                PlayerTable.KEY_playerid + "," +
                PlayerTable.KEY_matchid + "," +
                PlayerTable.KEY_accountid + "," +
                PlayerTable.KEY_hero + "," +
                PlayerTable.Key_radiant + "," +
                PlayerTable.Key_level + "," +
                PlayerTable.Key_herodamage + "," +
                PlayerTable.Key_towerdamage + "," +
                PlayerTable.Key_gold + "," +
                PlayerTable.Key_kills + "," +
                PlayerTable.Key_deaths + "," +
                PlayerTable.Key_assists +
                " FROM " + PlayerTable.TABLE
                + " WHERE " +
                PlayerTable.KEY_matchid + "=" + String.valueOf(matchID)
                + " GROUP BY " +
                PlayerTable.KEY_playerid + "," +
                PlayerTable.KEY_matchid + "," +
                field + " HAVING " + field + "= MAX(" + field + ")";// It's a good practice to use parameter ?, instead of concatenate string

        PlayerTable matchTable = new PlayerTable();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                matchTable = new PlayerTable(
                        cursor.getInt(cursor.getColumnIndex(PlayerTable.KEY_playerid)),
                        cursor.getLong(cursor.getColumnIndex(PlayerTable.KEY_matchid)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.KEY_accountid)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.KEY_hero)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_radiant)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_level)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_herodamage)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_towerdamage)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_gold)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_kills)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_deaths)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_assists)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return matchTable;
    }

    public List<PlayerTable> SelectListBySameMatch(int accountID1,int accountID2) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =
                "SELECT * FROM "+
                        "(SELECT * FROM " + PlayerTable.TABLE + " WHERE " + PlayerTable.KEY_accountid + "=" + String.valueOf(accountID1)+") A"
                        +" INNER JOIN " +
                        "(SELECT * FROM " + PlayerTable.TABLE + " WHERE " + PlayerTable.KEY_accountid + "=" + String.valueOf(accountID2)+") B"
                        + " ON A." + PlayerTable.KEY_matchid + "= B." + PlayerTable.KEY_matchid;// It's a good practice to use parameter ?, instead of concatenate string

        List<PlayerTable> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new PlayerTable(
                        cursor.getInt(cursor.getColumnIndex(PlayerTable.KEY_playerid)),
                        cursor.getLong(cursor.getColumnIndex(PlayerTable.KEY_matchid)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.KEY_accountid)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.KEY_hero)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_radiant)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_level)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_herodamage)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_towerdamage)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_gold)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_kills)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_deaths)),
                        cursor.getString(cursor.getColumnIndex(PlayerTable.Key_assists))));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public int insert(PlayerTable matchTable) {
        //Open connection to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Inserting Row
        long steamId = db.insert(PlayerTable.TABLE, null, matchTable.contentValues);
        db.close(); // Closing database connection
        return (int) steamId;
    }

    public void delete(Long matchID) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(PlayerTable.TABLE, PlayerTable.KEY_matchid + "= ?", new String[]{String.valueOf(matchID)});
        db.close(); // Closing database connection
    }

    public void update(PlayerTable matchTable) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(PlayerTable.TABLE, matchTable.contentValues, PlayerTable.KEY_matchid + "= ?", new String[]{String.valueOf(matchTable.contentValues.get(PlayerTable.KEY_matchid))});
        db.close(); // Closing database connection
    }
}
