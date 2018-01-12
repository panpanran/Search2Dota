package JSON2Data;

import JSon.FriendJSON;
import JSon.ItemJSON;
import Urls.Urls;
import Urls.UrlsFactory;

/**
 * Created by panpanr on 6/5/2015.
 */
public class JSON2Item extends JSON2Data {
    public JSON2Item() {
        super();
    }
    @Override
    public void GetData()
    {
        Urls dota2url = UrlsFactory.GetDota2Url("ItemUrl");
        String url = dota2url.GetUrl();
        ItemJSON json = new ItemJSON(url,"result", "items");
        JSONList = json.playerInfo;
    }
}
