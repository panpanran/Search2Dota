package Urls;

/**
 * Created by panpanr on 6/4/2015.
 */
public class FriendUrl extends Urls {
    @Override
    public String GetUrl(String accountid) {
        String url = "http://api.steampowered.com/ISteamUser/GetFriendList/v0001/?key=F5AD98D0EC9BE4B0988F245F21851D68&steamid=" + String.valueOf(Integer.parseInt(accountid) + 76561197960265728L) + "&relationship=friend";
        return url;
    }
}
