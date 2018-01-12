package Database.BusinessLogicLayer;

import android.content.Context;

import java.util.List;

import Database.DataAccessLayer.DALFriend;
import Database.Table.FriendTable;

/**
 * Created by panpanr on 7/2/2015.
 */
public class BLLFriend {
    private Context context;
    private DALFriend dalFriend;
    public BLLFriend(Context context){
        this.context = context;
        dalFriend= new DALFriend(context);
    }


    public List<FriendTable> SelectAll() {
        return dalFriend.SelectAll();
    }

    public List<FriendTable> SelectListBySteamID(Long steamID) {
        return  dalFriend.SelectListBySteamID(steamID);
    }

    public int insert(FriendTable friendTable) {
        return dalFriend.insert(friendTable);
    }

    public void delete(FriendTable friendTable) {
        dalFriend.delete(friendTable);
    }

    public void update(FriendTable friendTable) {
        dalFriend.update(friendTable);
    }
}
