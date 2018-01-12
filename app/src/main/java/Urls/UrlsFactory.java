package Urls;

/**
 * Created by panpanr on 6/4/2015.
 */
public class UrlsFactory {
    public static Urls GetDota2Url(String className) {
        Urls dota2Url = null;
        try {
            dota2Url = (Urls) Class.forName("Urls." + className).newInstance();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dota2Url;
    }
}
