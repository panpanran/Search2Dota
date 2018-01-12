package Database.BusinessLogicLayer;
import android.content.Context;
import java.util.List;
import Database.DataAccessLayer.DALMatch;
import Database.Table.MatchTable;

/**
 * Created by panpanr on 7/13/2015.
 */
public class BLLMatch {
    private Context context;
    private DALMatch dalMatch;
    public BLLMatch(Context context){
        this.context = context;
        dalMatch= new DALMatch(context);
    }

    public MatchTable SelectBymatchID(Long matchID) {
        return dalMatch.SelectBymatchID(matchID);
    }

    public int insert(MatchTable matchTable) {
        return dalMatch.insert(matchTable);
    }

    public void delete(Long matchID) {
        dalMatch.delete(matchID);
    }

    public void update(MatchTable matchTable) {
        dalMatch.update(matchTable);
    }
}

