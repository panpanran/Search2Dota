package JSON2Data;

import JSon.HistoryJSON;
import Urls.Urls;
import Urls.UrlsFactory;

/**
 * Created by panpanr on 6/5/2015.
 */
public class JSON2HeroHistory extends JSON2Data {
    public JSON2HeroHistory() {
        super();
    }
    @Override
    public void GetData(String heroID)
    {
        Urls dota2url = UrlsFactory.GetDota2Url("HeroHistoryUrl");
        String url = dota2url.GetUrl(heroID);
        HistoryJSON json1 = new HistoryJSON(url,"result", "matches");
        JSONList = json1.playerInfo;
        HistoryJSON json2 = new HistoryJSON(url,"result", "matches", "players");
        JSONList.addAll(json2.playerInfo);
    }
}
