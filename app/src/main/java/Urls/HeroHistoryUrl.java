package Urls;

/**
 * Created by panpanr on 6/4/2015.
 */
public class HeroHistoryUrl extends Urls {
    @Override
    public String GetUrl(String heroid) {
        String url = "https://api.steampowered.com/IDOTA2Match_570/GetMatchHistory/V001/?key=F5AD98D0EC9BE4B0988F245F21851D68&skill=3&min_players=10&hero_id=" + heroid + "&game_mode=1&matches_requested=20";
        return url;
    }
}
