package JSON2Data;

import JSon.FriendJSON;
import Urls.Urls;
import Urls.UrlsFactory;

/**
 * Created by panpanr on 6/7/2015.
 */
public class JSON2Friend extends JSON2Data {
    public JSON2Friend() {
        super();
    }
    @Override
    public void GetData(String accountID)
    {
        Urls dota2url = UrlsFactory.GetDota2Url("FriendUrl");
        String url = dota2url.GetUrl(accountID);
        FriendJSON json = new FriendJSON(url,"friendslist","friends");
        JSONList = json.playerInfo;
    }
}
