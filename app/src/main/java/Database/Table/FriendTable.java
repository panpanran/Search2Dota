package Database.Table;

import android.content.ContentValues;

/**
 * Created by panpanr on 6/28/2015.
 */
public class FriendTable {
    // Labels table name
    public static final String TABLE = "tbFriend";
    // Labels Table Columns names
    public static final String KEY_friendid = "FriendID";
    public static final String KEY_steamid = "SteamID";
    public static final String KEY_friendsteamid = "FriendSteamID";
    public static final String KEY_relationship = "Relationship";
    public static final String KEY_friend_since = "Friend_since";

    public ContentValues contentValues;
    public FriendTable(){

    }
    public FriendTable(
            Long steamid,
            Long friendsteamid,
            String relationship,
            Long friend_since) {
        contentValues = new ContentValues();
        contentValues.put(KEY_steamid, steamid);
        contentValues.put(KEY_friendsteamid, friendsteamid);
        contentValues.put(KEY_relationship, relationship);
        contentValues.put(KEY_friend_since, friend_since);
    }

    public FriendTable(
            Integer friendid,
            Long steamid,
            Long friendsteamid,
            String relationship,
            Long friend_since) {
        contentValues = new ContentValues();
        contentValues.put(KEY_friendid, friendid);
        contentValues.put(KEY_steamid, steamid);
        contentValues.put(KEY_friendsteamid, friendsteamid);
        contentValues.put(KEY_relationship, relationship);
        contentValues.put(KEY_friend_since, friend_since);
    }
}
