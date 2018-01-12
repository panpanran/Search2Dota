package Database.Table;

import android.content.ContentValues;
import android.graphics.Color;

import java.security.KeyPair;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ran.pan on 6/15/2015.
 */
public class UserTable {
    // Labels table name
    public static final String TABLE = "tbUser";
    // Labels Table Columns names
    public static final String KEY_steamid = "SteamID";
    public static final String KEY_visibility = "Visibility";
    public static final String KEY_name = "UserName";
    public static final String KEY_lastlogin = "LastLogin";
    public static final String KEY_status = "Status";
    public static final String KEY_profileurl = "ProfileUrl";
    public static final String KEY_realname = "RealName";
    public static final String KEY_timecreated = "TimeCreated";
    public static final String KEY_country = "Country";

    public ContentValues contentValues;
    public UserTable(){

    }
    public UserTable(
            Long steamID,
            String visibility,
            String name,
            String lastlogin,
            String status,
            String profileurl,
            String realname,
            String timecreated,
            String country) {
        contentValues = new ContentValues();
        contentValues.put(KEY_steamid, steamID);
        contentValues.put(KEY_visibility, visibility);
        contentValues.put(KEY_name, name);
        contentValues.put(KEY_country, country);
        contentValues.put(KEY_lastlogin, lastlogin);
        contentValues.put(KEY_timecreated, timecreated);
        contentValues.put(KEY_profileurl, profileurl);
        contentValues.put(KEY_realname, realname);
        contentValues.put(KEY_status, status);
    }

    public String GetStatus(String status){
        String gStatus ="";
        switch (status){
            case "0":
                gStatus = "Offline";
                break;
            case "1":
                gStatus = "Online";
                break;
            case "2":
                gStatus = "Busy";
                break;
            case "3":
                gStatus = "Away";
                break;
            case "4":
                gStatus = "Snooze";
                break;
            case "5":
                gStatus = "Trading";
                break;
            case "6":
                gStatus = "Playing";
                break;
        }

        return gStatus;
    }

    public int GetColor(String status){
        int color = 0;
        switch (status){
            case "Offline":
                color = Color.RED;
                break;
            case "Online":
                color = Color.GREEN;
                break;
            case "Busy":
                color = Color.BLUE;
                break;
            case "Away":
                color = Color.BLUE;
                break;
            case "Snooze":
                color = Color.GRAY;
                break;
            case "Trading":
                color = Color.YELLOW;
                break;
            case "Playing":
                color = Color.YELLOW;
                break;
        }

        return color;
    }
}
