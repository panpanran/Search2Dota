package Urls;

/**
 * Created by panpanr on 6/4/2015.
 */
public class HeroIconUrl extends Urls {
    @Override
    public String GetUrl(String heroname) {
        String url = "http://cdn.dota2.com/apps/dota2/images/heroes/" + heroname + "_vert.jpg";
        return url;
    }
}
