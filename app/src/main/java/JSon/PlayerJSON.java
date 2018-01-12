package JSon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by panpanr on 6/3/2015.
 */
public class PlayerJSON extends JSON {
    public List<Map<String, Object>> playerInfo;

    public PlayerJSON(String url, String FirstLayout, String SecondLayout) {
        super(url, FirstLayout, SecondLayout);
        playerInfo = this.JSONList;
    }
}
