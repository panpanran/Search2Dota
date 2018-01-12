package Urls;

/**
 * Created by panpanr on 6/4/2015.
 */
public class ItemIconUrl extends Urls {
    @Override
    public String GetUrl(String itemname) {
        String url = "http://cdn.dota2.com/apps/dota2/images/items/" + itemname + "_lg.png";
        return url;
    }
}
