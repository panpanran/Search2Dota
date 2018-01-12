package Database.BusinessLogicLayer;

import android.content.Context;

import Database.DataAccessLayer.DALUser;
import Database.Table.UserTable;

/**
 * Created by panpanr on 6/21/2015.
 */
public class BLLUser {
    private Context context;
    private DALUser dalUser;

    public BLLUser(Context context) {
        this.context = context;
        dalUser = new DALUser(context);
    }


    public UserTable SelectBySteamID(Long steamID) {
        return dalUser.SelectBySteamID(steamID);
    }

    public UserTable SelectBySteamIDByStatusByName(Long steamID,String status,String name) {
        return  dalUser.SelectBySteamIDByStatusByName(steamID, status, name);
    }

    public int insert(UserTable userTable) {
        return dalUser.insert(userTable);
    }

    public void delete(Long steamID) {
        dalUser.delete(steamID);
    }

    public void update(UserTable userTable) {
        dalUser.update(userTable);
    }
}
