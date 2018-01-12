package Database.Table;

import android.content.ContentValues;
import android.text.format.Time;

/**
 * Created by panpanr on 7/11/2015.
 */
public class MatchTable {
    // Labels table name
    public static final String TABLE = "tbMatch";
    // Labels Table Columns names
    public static final String KEY_matchid = "MatchID";
    public static final String KEY_starttime = "StartTime";
    public static final String KEY_lobby = "Lobby";
    public static final String KEY_mode = "Mode";
    public static final String KEY_duration = "Duration";
    public static final String KEY_result = "Result";

    public ContentValues contentValues;
    public MatchTable(){

    }
    public MatchTable(
            Long matchid,
            String starttime,
            String lobby,
            String mode,
            Long duration,
            String result
    ) {
        contentValues = new ContentValues();
        contentValues.put(KEY_matchid, matchid);
        contentValues.put(KEY_starttime, starttime);
        contentValues.put(KEY_lobby, lobby);
        contentValues.put(KEY_mode, mode);
        contentValues.put(KEY_result, result);
        contentValues.put(KEY_duration, duration);
    }
}
