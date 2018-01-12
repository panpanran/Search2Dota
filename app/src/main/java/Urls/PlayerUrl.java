package Urls;

/**
 * Created by panpanr on 6/4/2015.
 */
public class PlayerUrl extends Urls {
    @Override
    public String GetUrl(String accountid) {
        String url = "http://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key=F5AD98D0EC9BE4B0988F245F21851D68&steamids=" + String.valueOf(Integer.parseInt(accountid) + 76561197960265728L);
        return url;
    }
}
