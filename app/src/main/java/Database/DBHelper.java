package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Database.Table.FriendTable;
import Database.Table.MatchTable;
import Database.Table.PlayerTable;
import Database.Table.UserTable;
import Database.Table.SearchTable;
import Database.Table.UserTable;

/**
 * Created by ran.pan on 6/16/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION = 44;

    // Database Name
    private static final String DATABASE_NAME = "dbDota2.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        String createUser = "CREATE TABLE " + UserTable.TABLE  + "("
                + UserTable.KEY_steamid  + " LONG PRIMARY KEY,"
                + UserTable.KEY_visibility + " TEXT, "
                + UserTable.KEY_name + " TEXT, "
                + UserTable.KEY_lastlogin + " TEXT, "
                + UserTable.KEY_country + " TEXT, "
                + UserTable.KEY_profileurl + " TEXT, "
                + UserTable.KEY_realname + " TEXT, "
                + UserTable.KEY_status + " TEXT, "
                + UserTable.KEY_timecreated + " TEXT )";
        db.execSQL(createUser);

        String createSearch = "CREATE TABLE " + SearchTable.TABLE  + "("
                + SearchTable.KEY_steamid  + " LONG,"
                + SearchTable.KEY_lasttime + " LONG,"
                + SearchTable.KEY_searchtimes + " INTEGER,"
                + "FOREIGN KEY("+ SearchTable.KEY_steamid +") REFERENCES "+ UserTable.TABLE + "(" + UserTable.KEY_steamid + ") )";
        db.execSQL(createSearch);

        String createFriend = "CREATE TABLE " + FriendTable.TABLE  + "("
                + FriendTable.KEY_friendid  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FriendTable.KEY_steamid  + " LONG,"
                + FriendTable.KEY_friendsteamid  + " LONG,"
                + FriendTable.KEY_relationship + " TEXT,"
                + FriendTable.KEY_friend_since + " LONG,"
                + "FOREIGN KEY("+ FriendTable.KEY_steamid +") REFERENCES "+ UserTable.TABLE + "(" + UserTable.KEY_steamid + ") )";
        db.execSQL(createFriend);

        String createMatch = "CREATE TABLE " + MatchTable.TABLE  + "("
                + MatchTable.KEY_matchid  + " LONG,"
                + MatchTable.KEY_starttime  + " TEXT,"
                + MatchTable.KEY_duration  + " LONG,"
                + MatchTable.KEY_lobby + " TEXT,"
                + MatchTable.KEY_result + " TEXT,"
                + MatchTable.KEY_mode + " TEXT )";
        db.execSQL(createMatch);

        String createPlayer = "CREATE TABLE " + PlayerTable.TABLE  + "("
                + PlayerTable.KEY_playerid  + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PlayerTable.KEY_matchid  + " LONG,"
                + PlayerTable.KEY_accountid  + " TEXT,"
                + PlayerTable.KEY_hero + " TEXT,"
                + PlayerTable.Key_radiant + " TEXT,"
                + PlayerTable.Key_level + " TEXT,"
                + PlayerTable.Key_herodamage + " TEXT,"
                + PlayerTable.Key_towerdamage + " TEXT,"
                + PlayerTable.Key_gold + " TEXT,"
                + PlayerTable.Key_kills + " TEXT,"
                + PlayerTable.Key_deaths + " TEXT,"
                + PlayerTable.Key_assists + " TEXT,"
                + "FOREIGN KEY("+ PlayerTable.KEY_matchid +") REFERENCES "+ MatchTable.TABLE + "(" + MatchTable.KEY_matchid + ") )";
        db.execSQL(createPlayer);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + PlayerTable.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SearchTable.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FriendTable.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MatchTable.TABLE);
        // Create tables again
        onCreate(db);

    }

}
