package JSON2Data;

import JSon.FriendJSON;
import JSon.HistoryJSON;
import Urls.Urls;
import Urls.UrlsFactory;

/**
 * Created by panpanr on 6/5/2015.
 */
public class JSON2MatchHistory extends JSON2Data {
    public JSON2MatchHistory() {
        super();
    }
    @Override
    public void GetData(String accountID)
    {
        Urls dota2url = UrlsFactory.GetDota2Url("MatchHistoryUrl");
        String url = dota2url.GetUrl(accountID);
        HistoryJSON json = new HistoryJSON(url,"result", "matches");
        JSONList = json.playerInfo;
    }
}
