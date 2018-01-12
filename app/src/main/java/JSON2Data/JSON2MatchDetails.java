package JSON2Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import JSon.HistoryJSON;
import JSon.MatchJSON;
import Urls.Urls;
import Urls.UrlsFactory;

/**
 * Created by panpanr on 6/5/2015.
 */
public class JSON2MatchDetails extends JSON2Data {
    public JSON2MatchDetails() {
        super();
    }
    @Override
    public void GetData(Long matchID)
    {
        Urls dota2url = UrlsFactory.GetDota2Url("MatchDetailsUrl");
        String url = dota2url.GetUrl(String.valueOf(matchID));
        String[] strMatch = {"radiant_win", "duration", "game_mode", "human_players"};
        JSONList = new ArrayList<>();
        Map<String,Object> map = new HashMap();;
        for(int i = 0; i<strMatch.length;i++){
            MatchJSON json1 = new MatchJSON(url,"result",strMatch[i]);
            map.put(strMatch[i], json1.playerData);
        }
        JSONList.add(map);
        MatchJSON json2 = new MatchJSON(url,"result","players");
        JSONList.addAll(json2.playerInfo);
    }
}
