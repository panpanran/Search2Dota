package Database.Table;

import android.content.ContentValues;

import java.sql.Timestamp;

/**
 * Created by panpanr on 6/27/2015.
 */
public class SearchTable {
    public static final String TABLE = "tbSearch";
    // Labels Table Columns names
    public static final String KEY_searchid = "SearchID";
    public static final String KEY_steamid = "SteamID";
    public static final String KEY_searchtimes = "SearchTimes";
    public static final String KEY_lasttime = "LastTime";

    public ContentValues contentValues;
    public SearchTable(){

    }
    public SearchTable(
            Integer searchID,
            Long steamID,
            Integer searchtimes){
        contentValues = new ContentValues();
        contentValues.put(KEY_searchid, searchID);
        contentValues.put(KEY_steamid, steamID);
        contentValues.put(KEY_searchtimes, searchtimes);
        contentValues.put(KEY_lasttime, System.currentTimeMillis());
    }
    public SearchTable(
            Long steamID,
            Integer searchtimes) {
        contentValues = new ContentValues();
        contentValues.put(KEY_steamid, steamID);
        contentValues.put(KEY_searchtimes, searchtimes);
        contentValues.put(KEY_lasttime, System.currentTimeMillis());
    }
}
