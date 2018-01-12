package Urls;

/**
 * Created by panpanr on 6/4/2015.
 */
public class MatchDetailsUrl extends Urls {
    @Override
    public String GetUrl(String matchid) {
        String url = "https://api.steampowered.com/IDOTA2Match_570/GetMatchDetails/V001/?key=F5AD98D0EC9BE4B0988F245F21851D68&match_id=" + matchid;
        return url;
    }
}
