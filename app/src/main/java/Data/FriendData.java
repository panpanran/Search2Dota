package Data;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Database.BusinessLogicLayer.BLLFriend;
import Database.BusinessLogicLayer.BLLUser;
import Database.Table.FriendTable;
import Database.Table.UserTable;
import ranpan.dota2search.LoginActivity;

/**
 * Created by panpanr on 6/7/2015.
 */
public class FriendData {

    public static HashMap<String, List<String>> getAll(Context context)
    {
        HashMap<String, List<String>> friendList = new HashMap<String, List<String>>();
        BLLFriend bllFriend = new BLLFriend(context);
        List<FriendTable> friendTableList = bllFriend.SelectListBySteamID(Long.parseLong(LoginActivity.accountID)+ 76561197960265728L);
        for (int i = 0; i < friendTableList.size(); i++) {
            BLLUser bllUser = new BLLUser(context);
            UserTable userTable = bllUser.SelectBySteamID(Long.parseLong(friendTableList.get(i).contentValues.get(FriendTable.KEY_friendsteamid).toString()));
            List<String> playerDetails = new ArrayList<String>();
            Date dateLogin = new Date();
            playerDetails.add(userTable.contentValues.getAsString(UserTable.KEY_steamid));
            dateLogin.setTime(Long.parseLong(userTable.contentValues.getAsString(UserTable.KEY_lastlogin)) * 1000);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            playerDetails.add("<b>" + UserTable.KEY_lastlogin + "</b>" + ": " + df.format(dateLogin));
            playerDetails.add("<b>" + UserTable.KEY_realname + "</b>" + ": " + userTable.contentValues.getAsString(UserTable.KEY_realname));
            playerDetails.add("<b>" + UserTable.KEY_country + "</b>" + ": " + userTable.contentValues.getAsString(UserTable.KEY_country));
            playerDetails.add("<b>" + UserTable.KEY_profileurl + "</b>" + ": " + "<a href=\""+ userTable.contentValues.getAsString(UserTable.KEY_profileurl)+ "\">" + userTable.contentValues.getAsString(UserTable.KEY_profileurl)+"</a>");
            Date dateCreated = new Date();
            try {
                dateCreated.setTime(Long.parseLong(userTable.contentValues.getAsString(UserTable.KEY_timecreated)) * 1000);
            }
            catch (Exception e){
                  e.getMessage();
            }
            playerDetails.add("<b>" + UserTable.KEY_timecreated + "</b>" + ": " + df.format(dateCreated));
            friendList.put(String.valueOf(Long.parseLong(friendTableList.get(i).contentValues.get(FriendTable.KEY_friendsteamid).toString()) - 76561197960265728L), playerDetails);
        }
        return friendList;
    }

    public static HashMap<String, List<String>> getSelected(Context context, String textSearch)
    {
        HashMap<String, List<String>> friendList = new HashMap<String, List<String>>();
        BLLFriend bllFriend = new BLLFriend(context);
        List<FriendTable> friendTableList = bllFriend.SelectListBySteamID(Long.parseLong(LoginActivity.accountID)+ 76561197960265728L);
        for (int i = 0; i < friendTableList.size(); i++) {
            BLLUser bllUser = new BLLUser(context);
            Long friendID = Long.parseLong(friendTableList.get(i).contentValues.get(FriendTable.KEY_friendsteamid).toString());
            UserTable userTable = bllUser.SelectBySteamIDByStatusByName(friendID,textSearch,textSearch);
            List<String> playerDetails = new ArrayList<String>();
            Date dateLogin = new Date();
            try {
                playerDetails.add(userTable.contentValues.getAsString(UserTable.KEY_steamid));
                dateLogin.setTime(Long.parseLong(userTable.contentValues.getAsString(UserTable.KEY_lastlogin)) * 1000);
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                playerDetails.add("<b>" + UserTable.KEY_lastlogin + "</b>" + ": " + df.format(dateLogin));
                playerDetails.add("<b>" + UserTable.KEY_realname + "</b>" + ": " + userTable.contentValues.getAsString(UserTable.KEY_realname));
                playerDetails.add("<b>" + UserTable.KEY_country + "</b>" + ": " + userTable.contentValues.getAsString(UserTable.KEY_country));
                playerDetails.add("<b>" + UserTable.KEY_profileurl + "</b>" + ": " + "<a href=\"" + userTable.contentValues.getAsString(UserTable.KEY_profileurl) + "\">" + userTable.contentValues.getAsString(UserTable.KEY_profileurl) + "</a>");
                Date dateCreated = new Date();
                try {
                    dateCreated.setTime(Long.parseLong(userTable.contentValues.getAsString(UserTable.KEY_timecreated)) * 1000);
                } catch (Exception e) {
                }
                playerDetails.add("<b>" + UserTable.KEY_timecreated + "</b>" + ": " + df.format(dateCreated));
                friendList.put(String.valueOf(Long.parseLong(friendTableList.get(i).contentValues.get(FriendTable.KEY_friendsteamid).toString()) - 76561197960265728L), playerDetails);
            }catch (Exception e){
                e.getMessage();
            }
        }
        return friendList;
    }
}
