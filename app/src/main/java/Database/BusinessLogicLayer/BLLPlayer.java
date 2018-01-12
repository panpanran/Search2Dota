package Database.BusinessLogicLayer;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import Data.MatchData;
import Database.DataAccessLayer.DALPlayer;
import Database.Table.MatchTable;
import Database.Table.PlayerTable;

/**
 * Created by panpanr on 8/2/2015.
 */
public class BLLPlayer {
    private Context context;
    private DALPlayer dalPlayer;
    public BLLPlayer(Context context){
        this.context = context;
        dalPlayer= new DALPlayer(context);
    }


    public List<PlayerTable> SelectListByaccountID(String accountID) {
        return dalPlayer.SelectListByaccountID(accountID);
    }

    public List<PlayerTable> SelectListBymatchID(Long matchID) {
        return dalPlayer.SelectListBymatchID(matchID);
    }

    public PlayerTable SelectListByplayerid(int playerid) {
        return dalPlayer.SelectListByplayerid(playerid);
    }

    public List<PlayerTable> SelectListBySameMatch(int accountID1, int accountID2) {
        return dalPlayer.SelectListBySameMatch(accountID1, accountID1);
    }

    public List<PlayerTable> SelectListByaccountIDBylobbyBystartTimeByhero(String accountID, String txtSearch) {
        BLLPlayer bllPlayer = new BLLPlayer(context);
        List<PlayerTable> playerTableList = bllPlayer.SelectListByaccountID(accountID);
        List<PlayerTable> playerList = new ArrayList<>();

        for(int i = 0;i<playerTableList.size();i++){
            PlayerTable playerTable = playerTableList.get(i);
            BLLMatch bllMatch = new BLLMatch(context);
            MatchTable matchTable = bllMatch.SelectBymatchID(playerTable.contentValues.getAsLong(PlayerTable.KEY_matchid));
            if(playerTable.contentValues.getAsString(PlayerTable.KEY_hero).toLowerCase().contains(txtSearch.toLowerCase())
                    || matchTable.contentValues.getAsString(MatchTable.KEY_lobby).toLowerCase().contains(txtSearch.toLowerCase())
                    || matchTable.contentValues.getAsString(MatchTable.KEY_starttime).toLowerCase().contains(txtSearch.toLowerCase())){
                playerList.add(playerTable);
            }
        }
        return playerList;
    }

    public PlayerTable SelectListByaccountIDByMostRecent(String accountID) {
        return dalPlayer.SelectListByaccountIDByMostRecent(accountID);
    }

    public PlayerTable SelectListBymatchIDByaccountID(Long matchID, String accountID) {
        return dalPlayer.SelectListBymatchIDByaccountID(matchID,accountID);
    }

    public int insert(PlayerTable playerTable) {
        return dalPlayer.insert(playerTable);
    }

    public void delete(Long accountID) {
        dalPlayer.delete(accountID);
    }

    public void update(PlayerTable playerTable) {
        dalPlayer.update(playerTable);
    }
}
