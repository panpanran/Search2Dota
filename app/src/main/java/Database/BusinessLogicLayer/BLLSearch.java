package Database.BusinessLogicLayer;

import android.content.Context;

import java.util.List;

import Database.DataAccessLayer.DALSearch;
import Database.Table.SearchTable;

/**
 * Created by panpanr on 7/2/2015.
 */
public class BLLSearch {
    private Context context;
    DALSearch dalSearch;
    public BLLSearch(Context context) {
        this.context = context;
        dalSearch = new DALSearch(context);
    }

    public List<SearchTable> SelectRecent() {
        return dalSearch.SelectRecent();
    }

    public List<SearchTable> SelectAll() {
        return dalSearch.SelectAll();
    }

    public SearchTable SelectBySteamID(Long steamID) {
        return dalSearch.SelectBySteamID(steamID);
    }

    public int insert(SearchTable searchTable) {
        return dalSearch.insert(searchTable);
    }

    public void delete(String steamID) {
        dalSearch.delete(steamID);
    }

    public void update(SearchTable searchTable) {
        dalSearch.update(searchTable);
    }
}
