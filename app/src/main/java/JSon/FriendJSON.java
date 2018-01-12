package JSon;

import java.util.List;
import java.util.Map;

/**
 * Created by panpanr on 6/4/2015.
 */
public class FriendJSON extends JSON{
    public List<Map<String, Object>> playerInfo;
    public FriendJSON(String url,String FirstLayout,String SecondLayout){
        super(url,FirstLayout,SecondLayout);
        playerInfo = this.JSONList;
    }
}
