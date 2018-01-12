package JSON2Data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by panpanr on 6/4/2015.
 */
public abstract class JSON2Data {
    public List<Map<String, Object>> JSONList;
    public String JSONData;
    public JSON2Data() {
    }
    public void GetData(Long ID){};
    public void GetData(String ID){};
    public void GetData(){};
    public void GetData(Context context){};
}
