package Urls;

/**
 * Created by panpanr on 6/4/2015.
 */
public class MatchHistoryUrl extends Urls {
    @Override
    public String GetUrl(String accountid) {
        String url = "https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?key=F5AD98D0EC9BE4B0988F245F21851D68&account_id=" + accountid + "&matches_requested=25";
        return url;
    }
}
