package Data;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Convert.CImage;
import Database.BusinessLogicLayer.BLLMatch;
import Database.BusinessLogicLayer.BLLPlayer;
import Database.BusinessLogicLayer.BLLUser;
import Database.Table.MatchTable;
import Database.Table.PlayerTable;
import Database.Table.UserTable;
import ranpan.dota2search.LoginActivity;

/**
 * Created by panpanr on 7/13/2015.
 */
public class MatchData {
    public static Map<Long, List<String>> getAll(Context context) {
        Map<Long, List<String>> matchList = new TreeMap<Long, List<String>>(Collections.reverseOrder());
        BLLPlayer bllPlayer = new BLLPlayer(context);
        List<PlayerTable> playerTableList = bllPlayer.SelectListByaccountID(LoginActivity.accountID);
        for (int i = 0; i < playerTableList.size(); i++) {
            PlayerTable playerTable = playerTableList.get(i);
            List<PlayerTable> playerList = bllPlayer.SelectListBymatchID(Long.parseLong(playerTable.contentValues.getAsString(PlayerTable.KEY_matchid)));
            List<String> matchDetails = new ArrayList<String>();
            matchDetails.add("9999999");
            for (int j = 0; j < playerList.size(); j++) {
                PlayerTable plTable = playerList.get(j);
                matchDetails.add(plTable.contentValues.getAsString(PlayerTable.KEY_playerid));
            }
            matchList.put(Long.parseLong(playerTable.contentValues.getAsString(MatchTable.KEY_matchid)), matchDetails);
        }
        return matchList;
    }

    public static Map<Long, List<String>> getSelected(Context context, String textSearch) {
        Map<Long, List<String>> matchList = new TreeMap<>(Collections.reverseOrder());
        BLLPlayer bllPlayer = new BLLPlayer(context);
        List<PlayerTable> playerTableList = bllPlayer.SelectListByaccountIDBylobbyBystartTimeByhero(LoginActivity.accountID, textSearch);
        for (int i = 0; i < playerTableList.size(); i++) {
            PlayerTable playerTable = playerTableList.get(i);
            List<PlayerTable> playerList = bllPlayer.SelectListBymatchID(Long.parseLong(playerTable.contentValues.getAsString(MatchTable.KEY_matchid)));
            List<String> matchDetails = new ArrayList<String>();
            matchDetails.add("9999999");
            for (int j = 0; j < playerList.size(); j++) {
                PlayerTable plTable = playerList.get(j);
                matchDetails.add(plTable.contentValues.getAsString(PlayerTable.KEY_playerid));
            }

            matchList.put(playerTable.contentValues.getAsLong(PlayerTable.KEY_matchid), matchDetails);
        }
        return matchList;
    }

    public static Map<Long, List<String>> getRelatedMatch(Context context, String accountid) {
        Map<Long, List<String>> matchList = new TreeMap<>(Collections.reverseOrder());
        BLLPlayer bllPlayer = new BLLPlayer(context);
        List<PlayerTable> playerTableList = bllPlayer.SelectListByaccountID(LoginActivity.accountID);
        for (int i = 0; i < playerTableList.size(); i++) {
            PlayerTable playerTable = playerTableList.get(i);
            PlayerTable table = bllPlayer.SelectListBymatchIDByaccountID(Long.parseLong(playerTable.contentValues.getAsString(PlayerTable.KEY_matchid)), accountid);
            if (table.contentValues != null) {
                List<PlayerTable> playerList = bllPlayer.SelectListBymatchID(Long.parseLong(playerTable.contentValues.getAsString(PlayerTable.KEY_matchid)));
                List<String> matchDetails = new ArrayList<String>();
                matchDetails.add("9999999");
                for (int j = 0; j < playerList.size(); j++) {
                    PlayerTable plTable = playerList.get(j);
                    matchDetails.add(plTable.contentValues.getAsString(PlayerTable.KEY_playerid));
                }

                matchList.put(Long.parseLong(playerTable.contentValues.getAsString(PlayerTable.KEY_matchid)), matchDetails);
            }
        }
        return matchList;
    }

    public static Map<Long, List<String>> getRelatedMatchSelected(Context context, String accountid, String textSearch) {
        Map<Long, List<String>> matchList = new TreeMap<>(Collections.reverseOrder());
        BLLPlayer bllPlayer = new BLLPlayer(context);
        List<PlayerTable> playerTableList = bllPlayer.SelectListByaccountIDBylobbyBystartTimeByhero(LoginActivity.accountID, textSearch);
        for (int i = 0; i < playerTableList.size(); i++) {
            PlayerTable playerTable = playerTableList.get(i);
            PlayerTable table = bllPlayer.SelectListBymatchIDByaccountID(playerTable.contentValues.getAsLong(MatchTable.KEY_matchid), accountid);
            if (table.contentValues != null) {
                List<PlayerTable> playerList = bllPlayer.SelectListBymatchID(Long.parseLong(playerTable.contentValues.getAsString(MatchTable.KEY_matchid)));
                List<String> matchDetails = new ArrayList<String>();
                matchDetails.add("9999999");
                for (int j = 0; j < playerList.size(); j++) {
                    PlayerTable plTable = playerList.get(j);
                    matchDetails.add(plTable.contentValues.getAsString(PlayerTable.KEY_playerid));
                }
                matchList.put(playerTable.contentValues.getAsLong(PlayerTable.KEY_matchid), matchDetails);
            }
        }
        return matchList;
    }

    public static Map<String, Float> getMyScore(Context context, String accountID) {
        BLLPlayer bllPlayer = new BLLPlayer(context);
        PlayerTable playerTable = bllPlayer.SelectListByaccountIDByMostRecent(accountID);
        Map<String, Float> mapScore = new TreeMap<String, Float>();
        String[] strScores = {PlayerTable.Key_gold, PlayerTable.Key_deaths, PlayerTable.Key_herodamage, PlayerTable.Key_towerdamage, PlayerTable.Key_level, PlayerTable.Key_kills};

        List<PlayerTable> playerList = bllPlayer.SelectListBymatchID(Long.parseLong(playerTable.contentValues.getAsString(PlayerTable.KEY_matchid)));
        for (int m = 0; m < strScores.length; m++) {
            Map<String, Integer> map = new IdentityHashMap<String, Integer>();
            for (int j = 0; j < playerList.size(); j++) {
                PlayerTable plTable = playerList.get(j);
                String tempAccountID = plTable.contentValues.getAsString(PlayerTable.KEY_accountid);
                if (plTable.contentValues.getAsString(PlayerTable.KEY_accountid).equals("4294967295")) {
                    tempAccountID = String.valueOf(j);
                }
                if (strScores[m] == PlayerTable.Key_kills) {
                    map.put(tempAccountID, Integer.parseInt(plTable.contentValues.getAsString(strScores[m])) + Integer.parseInt(plTable.contentValues.getAsString(PlayerTable.Key_assists)));
                } else {
                    map.put(tempAccountID, Integer.parseInt(plTable.contentValues.getAsString(strScores[m])));
                }
            }

            map = sortByValue(map);
            List<String> keys = new ArrayList(map.keySet());
            float count = 0;
            for (int i = 0; i < keys.size(); i++) {
                count++;
                if (keys.get(i).equals(accountID)) {
                    mapScore.put(strScores[m], keys.size() + 1 - count);
                }
            }
            mapScore.put("type", Float.valueOf(map.size()));
        }

        return mapScore;
    }

    public static Map<String, Float> getMatchScore(Context context, int playerID) {
        BLLPlayer bllPlayer = new BLLPlayer(context);
        PlayerTable playerTable = bllPlayer.SelectListByplayerid(playerID);
        Map<String, Float> mapScore = new TreeMap<String, Float>();
        String[] strScores = {PlayerTable.Key_gold, PlayerTable.Key_deaths, PlayerTable.Key_herodamage, PlayerTable.Key_towerdamage, PlayerTable.Key_level, PlayerTable.Key_kills};

        List<PlayerTable> playerList = bllPlayer.SelectListBymatchID(Long.parseLong(playerTable.contentValues.getAsString(PlayerTable.KEY_matchid)));
        for (int m = 0; m < strScores.length; m++) {
            Map<String, Integer> map = new IdentityHashMap<String, Integer>();
            for (int j = 0; j < playerList.size(); j++) {
                PlayerTable plTable = playerList.get(j);
                String tempHero = plTable.contentValues.getAsString(PlayerTable.KEY_hero);
                if (strScores[m] == PlayerTable.Key_kills) {
                    map.put(tempHero, Integer.parseInt(plTable.contentValues.getAsString(strScores[m])) + Integer.parseInt(plTable.contentValues.getAsString(PlayerTable.Key_assists)));
                } else {
                    map.put(tempHero, Integer.parseInt(plTable.contentValues.getAsString(strScores[m])));
                }
            }

            map = sortByValue(map);
            List<String> keys = new ArrayList(map.keySet());
            float count = 0;
            for (int i = 0; i < keys.size(); i++) {
                count++;
                if (keys.get(i).equals(playerTable.contentValues.getAsString(PlayerTable.KEY_hero))) {
                    mapScore.put(strScores[m], keys.size() + 1 - count);
                }
            }
            mapScore.put("type", Float.valueOf(map.size()));
        }

        return mapScore;
    }

    public static Map sortByValue(Map<String, Integer> map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) o1).getValue())
                        .compareTo(((Map.Entry) o2).getValue());
            }
        });
        Map result = new LinkedHashMap();

        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static void ShowScore(View vt, int playerID, ImageView imgMe) {
        Dialog builder = new Dialog(vt.getContext());
        builder.requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        Map<String, Float> mapScore = getMatchScore(vt.getContext(), playerID);
        BLLPlayer bllPlayer = new BLLPlayer(vt.getContext());
        PlayerTable playerTable = bllPlayer.SelectListByplayerid(playerID);
        BLLUser bllUser = new BLLUser(vt.getContext());
        String name;
        try {
            Long steamID = playerTable.contentValues.getAsLong(PlayerTable.KEY_accountid) + 76561197960265728L;
            UserTable userTable = bllUser.SelectBySteamID(steamID);
            name = userTable.contentValues.getAsString(UserTable.KEY_name);
        } catch (Exception e) {
            name = "Unknown";
        }
        ((ViewGroup) imgMe.getParent()).removeView(imgMe);
        Bitmap bmp1 = Bitmap.createBitmap(imgMe.getWidth(), imgMe.getHeight(), Bitmap.Config.ARGB_8888);
        Point matchP1 = new Point(imgMe.getWidth() / 2, imgMe.getHeight() / 2);
        CImage.DrawHexagonal(bmp1, imgMe.getHeight(), imgMe.getWidth(), Color.YELLOW);
        CImage.FloodFill(bmp1, matchP1, bmp1.getPixel(matchP1.x, matchP1.y), Color.YELLOW);
        Canvas canvas = new Canvas(bmp1);
        Bitmap bmp2 = Bitmap.createBitmap(imgMe.getWidth(), imgMe.getHeight(), Bitmap.Config.ARGB_8888);
        CImage.DrawScoreHexagonal(bmp2, imgMe.getHeight(), imgMe.getWidth(), mapScore, Color.BLUE);
        canvas.drawBitmap(bmp2, 0, 0, null);
        Point matchP2 = new Point(imgMe.getWidth() / 2, imgMe.getHeight() / 2);
        CImage.FloodFill(bmp1, matchP2, bmp1.getPixel(matchP2.x, matchP2.y), Color.BLUE);
        imgMe.setImageBitmap(bmp1);
        builder.addContentView(imgMe, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.setTitle(name + "(" + playerTable.contentValues.getAsString(PlayerTable.KEY_accountid) + ")");
        builder.show();
    }
}
