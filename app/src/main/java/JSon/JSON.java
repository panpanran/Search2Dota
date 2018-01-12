package JSon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by panpanr on 6/4/2015.
 */
public abstract class JSON {
    protected List<Map<String, Object>> JSONList = new ArrayList();
    protected String JSONData = "";

    public String readFile(String filename) {
        String content = null;
        File file = new File(filename); //for ex foo.txt
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public String getHtml(String urlString) {
        try {
            StringBuffer html = new StringBuffer();
            java.net.URL url = new java.net.URL(urlString);  //
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();//
            java.io.InputStreamReader isr = new java.io.InputStreamReader(conn.getInputStream());//
            java.io.BufferedReader br = new java.io.BufferedReader(isr);//

            String temp;
            while ((temp = br.readLine()) != null) {  //
                if (!temp.trim().equals("")) {
                    html.append(temp).append("\n");  //
                }
            }
            br.close();   //
            isr.close();  //
            return html.toString();   //
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSON(String urlString, String FirstLayout, String SecondLayout) {

        try {
            String html = getHtml(urlString);
            try {
                JSONData = new JSONObject(html).getJSONObject(FirstLayout).getString(SecondLayout);
            } catch (JSONException e) {
                e.getMessage();
            }
            try {
            JSONArray array = new JSONObject(html).getJSONObject(FirstLayout).getJSONArray(SecondLayout);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.optJSONObject(i);
                Map<String, Object> map = new HashMap();
                Iterator keys = object.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    map.put(key, object.get(key));
                }
                JSONList.add(map);
            }
            } catch (JSONException e) {
                e.getMessage();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public JSON(String urlString, String Layout) {
        try {
            String html = getHtml(urlString);
            JSONArray array = new JSONObject(html).getJSONArray(Layout);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.optJSONObject(i);
                Map<String, Object> map = new HashMap();
                Iterator keys = object.keys();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    map.put(key, object.get(key));
                }
                JSONList.add(map);
            }
            Map<String, Object> map = new HashMap();
        } catch (JSONException e) {
            e.getMessage();
        }
    }

    public JSON(String urlString, String FirstLayout, String SecondLayout, String ThirdLayout) {
        try {
            String html = getHtml(urlString);
            JSONArray array = new JSONObject(html).getJSONObject(FirstLayout).getJSONArray(SecondLayout);
            for (int i = 0; i < array.length(); i++) {
                JSONArray jsonArray = array.getJSONObject(i).getJSONArray(ThirdLayout);
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject object = jsonArray.optJSONObject(j);
                    Map<String, Object> map = new HashMap();
                    Iterator keys = object.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        map.put(key, object.get(key));
                    }
                    JSONList.add(map);
                }
            }
            Map<String, Object> map = new HashMap();
        } catch (JSONException e) {
        }
    }
//    public JSON(String txt){
//        try{
//            String txtLocation = readFile(txt);
//            JSONData = new JSONObject(txtLocation).getString("name");
//        }
//        catch(JSONException e)
//        {
//        }
//    }
}
