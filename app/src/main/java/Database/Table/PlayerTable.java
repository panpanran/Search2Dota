package Database.Table;

import android.content.ContentValues;

/**
 * Created by panpanr on 8/2/2015.
 */
public class PlayerTable {
    public static final String TABLE = "tbPlayer";

    public static final String KEY_playerid = "PlayerID";
    public static final String KEY_matchid = "MatchID";
    public static final String KEY_accountid = "AccountID";
    public static final String KEY_hero = "Hero";
    public static final String Key_radiant = "Radiant";
    public static final String Key_level = "Level";
    public static final String Key_herodamage = "Herodamage";
    public static final String Key_towerdamage = "Towerdamage";
    public static final String Key_gold = "Gold";
    public static final String Key_kills = "Kills";
    public static final String Key_deaths = "Deaths";
    public static final String Key_assists = "Assists";

    public ContentValues contentValues;
    public PlayerTable(){

    }

    public PlayerTable(
            Long matchid,
            String accountid,
            String hero,
            String radiant,
            String level,
            String hero_damage,
            String tower_damage,
            String gold_per_min,
            String kills,
            String deaths,
            String assists
    ) {
        contentValues = new ContentValues();
        contentValues.put(KEY_matchid, matchid);
        contentValues.put(KEY_accountid, accountid);
        contentValues.put(KEY_hero, hero);
        contentValues.put(Key_radiant, radiant);
        contentValues.put(Key_level, level);
        contentValues.put(Key_herodamage, hero_damage);
        contentValues.put(Key_towerdamage, tower_damage);
        contentValues.put(Key_gold, gold_per_min);
        contentValues.put(Key_kills, kills);
        contentValues.put(Key_deaths, deaths);
        contentValues.put(Key_assists, assists);
    }

    public PlayerTable(
            int playerid,
            Long matchid,
            String accountid,
            String hero,
            String radiant,
            String level,
            String hero_damage,
            String tower_damage,
            String gold_per_min,
            String kills,
            String deaths,
            String assists
    ) {
        contentValues = new ContentValues();
        contentValues.put(KEY_playerid, playerid);
        contentValues.put(KEY_matchid, matchid);
        contentValues.put(KEY_accountid, accountid);
        contentValues.put(KEY_hero, hero);
        contentValues.put(Key_radiant, radiant);
        contentValues.put(Key_level, level);
        contentValues.put(Key_herodamage, hero_damage);
        contentValues.put(Key_towerdamage, tower_damage);
        contentValues.put(Key_gold, gold_per_min);
        contentValues.put(Key_kills, kills);
        contentValues.put(Key_deaths, deaths);
        contentValues.put(Key_assists, assists);;
    }
}
