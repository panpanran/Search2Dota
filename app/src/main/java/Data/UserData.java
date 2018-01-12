package Data;

import android.content.Context;
import android.text.Html;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Database.BusinessLogicLayer.BLLUser;
import Database.Table.UserTable;
import ranpan.dota2search.LoginActivity;

/**
 * Created by panpanr on 6/30/2015.
 */
public class UserData {
    public static List<String> getInfo(Context context)
    {
        UserTable myInfo = new UserTable();
        BLLUser bllUser = new BLLUser(context);
        UserTable userTable = bllUser.SelectBySteamID(Long.parseLong(LoginActivity.accountID) + 76561197960265728L);

        List<String> playerDetails = new ArrayList<String>();
        Date dateLogin = new Date();
        playerDetails.add(Html.fromHtml("<b>" + UserTable.KEY_name + "</b>") + ": "+userTable.contentValues.getAsString(UserTable.KEY_name));
        playerDetails.add(Html.fromHtml("<b>" + UserTable.KEY_status + "</b>") + ": "+userTable.contentValues.getAsString(UserTable.KEY_status));
        playerDetails.add(Html.fromHtml("<b>" + UserTable.KEY_steamid + "</b>") + ": "+userTable.contentValues.getAsString(UserTable.KEY_steamid));
        playerDetails.add(Html.fromHtml("<b>" + "AccountID" + "</b>") + ": " + String.valueOf(Long.parseLong(userTable.contentValues.get(UserTable.KEY_steamid).toString()) - 76561197960265728L));
        dateLogin.setTime(Long.parseLong(userTable.contentValues.getAsString(UserTable.KEY_lastlogin)) * 1000);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        playerDetails.add(Html.fromHtml("<b>" + UserTable.KEY_lastlogin + "</b>") + ": " + df.format(dateLogin));
        playerDetails.add(Html.fromHtml("<b>" + UserTable.KEY_realname + "</b>") + ": " + userTable.contentValues.getAsString(UserTable.KEY_realname));
        playerDetails.add(Html.fromHtml("<b>" + UserTable.KEY_country + "</b>") + ": " + userTable.contentValues.getAsString(UserTable.KEY_country));
        playerDetails.add(Html.fromHtml("<b>" + UserTable.KEY_profileurl + "</b>") + ": " + Html.fromHtml("<a href=\"" + userTable.contentValues.getAsString(UserTable.KEY_profileurl) + "\">" + userTable.contentValues.getAsString(UserTable.KEY_profileurl) + "</a>"));

        Date dateCreated = new Date();
        try {
            dateCreated.setTime(Long.parseLong(userTable.contentValues.getAsString(UserTable.KEY_timecreated)) * 1000);
        }
        catch (Exception e){

        }
        playerDetails.add(Html.fromHtml("<b>" + UserTable.KEY_timecreated + "</b>") + ": " + df.format(dateCreated));
        return playerDetails;
    }
}
