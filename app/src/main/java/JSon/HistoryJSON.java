package JSon;

import java.util.List;
import java.util.Map;

/**
 * Created by panpanr on 6/4/2015.
 */
public class HistoryJSON extends JSON {
    public List<Map<String, Object>> playerInfo;

    public HistoryJSON(String url,String FirstLayout,String SecondLayout) {
        super(url,FirstLayout,SecondLayout );
        playerInfo = this.JSONList;
    }

    public HistoryJSON(String url,String FirstLayout,String SecondLayout, String ThirdLayout){
        super(url,FirstLayout,SecondLayout,ThirdLayout);
        playerInfo = this.JSONList;
    }
}
