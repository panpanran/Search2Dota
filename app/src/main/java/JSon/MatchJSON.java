package JSon;

import java.util.List;
import java.util.Map;

/**
 * Created by panpanr on 6/4/2015.
 */
public class MatchJSON extends JSON{
    public List<Map<String, Object>> playerInfo;
    public String playerData;
    public MatchJSON(String url,String Layout){
        super(url,Layout);
        playerInfo = this.JSONList;
    }

    public MatchJSON(String url,String FirstLayout,String SecondLayout){
        super(url,FirstLayout,SecondLayout);
        playerInfo = this.JSONList;
        playerData = this.JSONData;
    }
}
