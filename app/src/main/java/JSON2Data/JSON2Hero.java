package JSON2Data;

import JSon.FriendJSON;
import JSon.HeroJSON;
import Urls.Urls;
import Urls.UrlsFactory;

/**
 * Created by panpanr on 6/5/2015.
 */
public class JSON2Hero extends JSON2Data {
    public JSON2Hero() {
        super();
    }
    @Override
    public void GetData()
    {
        Urls dota2url = UrlsFactory.GetDota2Url("HeroUrl");
        String url = dota2url.GetUrl();
        HeroJSON json = new HeroJSON(url,"result","heroes");
        JSONList = json.playerInfo;
    }
}