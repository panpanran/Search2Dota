package JSON2Data;
import JSon.PlayerJSON;
import Urls.Urls;
import Urls.UrlsFactory;

/**
 * Created by panpanr on 6/4/2015.
 */
public class JSON2Player extends JSON2Data {
    public JSON2Player() {
        super();
    }
    @Override
    public void GetData(String accountID)
    {
        Urls dota2url = UrlsFactory.GetDota2Url("PlayerUrl");
        String url = dota2url.GetUrl(accountID);
        PlayerJSON json = new PlayerJSON(url,"response","players");
        JSONList = json.playerInfo;
    }
}
