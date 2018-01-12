package JSON2Data;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Convert.CImage;
import Data.MatchData;
import Database.BusinessLogicLayer.BLLFriend;
import Database.BusinessLogicLayer.BLLMatch;
import Database.BusinessLogicLayer.BLLPlayer;
import Database.BusinessLogicLayer.BLLUser;
import Database.BusinessLogicLayer.BLLSearch;
import Database.Table.FriendTable;
import Database.Table.MatchTable;
import Database.Table.PlayerTable;
import Database.Table.UserTable;
import Database.Table.SearchTable;
import EnumData.EnumGameMode;
import EnumData.EnumLobbyType;
import Urls.Urls;
import Urls.UrlsFactory;
import ranpan.dota2search.LoadingActivity;
import ranpan.dota2search.LoginActivity;
import ranpan.dota2search.R;

/**
 * Created by panpanr on 6/6/2015.
 */
public class JSON2Download {
    JSON2Hero json2Hero = new JSON2Hero();
    JSON2Item json2Item = new JSON2Item();
    JSON2Friend json2Friend = new JSON2Friend();
    JSON2MatchHistory json2MatchHistory = new JSON2MatchHistory();
    List<Map<String, Object>> heroList;
    List<Map<String, Object>> itemList;
    List<Map<String, Object>> friendList;
    List<Map<String, Object>> matchList;
    Map<String, String> heroMap = new HashMap();

    public JSON2Download(Context context, String accountID) {
        json2Hero.GetData();
        heroList = json2Hero.JSONList;
        json2Item.GetData();
        itemList = json2Item.JSONList;
        json2Friend.GetData(accountID);
        friendList = json2Friend.JSONList;
        json2MatchHistory.GetData(accountID);
        matchList = json2MatchHistory.JSONList;
        LoadingActivity.fileSize = friendList.size() + heroList.size() + itemList.size() + matchList.size();
        LoadingActivity.fileSizeCopy = friendList.size() + heroList.size() + itemList.size() + matchList.size();
    }

    public void DownloadHero(Context context) {
        String heroName = "";
        String heroid = "";
        for (int i = 0; i < heroList.size(); i++) {
            LoadingActivity.fileSize--;
            heroName = heroList.get(i).get("name").toString().replace("npc_dota_hero_", "");
            heroid = heroList.get(i).get("id").toString();
            heroMap.put(heroid, heroName);
            Urls dota2url = UrlsFactory.GetDota2Url("HeroIconUrl");
            try {
                File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "hero_" + heroName + ".jpg");
                if (!file.exists()) {
                    URL url = new URL(dota2url.GetUrl(heroName));
                    InputStream in = new BufferedInputStream(url.openStream());
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    int n = 0;
                    while (-1 != (n = in.read(buf))) {
                        out.write(buf, 0, n);
                    }
                    out.close();
                    in.close();
                    byte[] response = out.toByteArray();
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(response);
                    fos.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public void DownloadItem(Context context) {
        String itemName = "";
        for (int i = 0; i < itemList.size(); i++) {
            LoadingActivity.fileSize--;
            itemName = itemList.get(i).get("name").toString().replace("item_", "");
            Urls dota2url = UrlsFactory.GetDota2Url("ItemIconUrl");
            try {
                File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "item_" + itemName + ".png");
                if (!file.exists()) {
                    URL url = new URL(dota2url.GetUrl(itemName));
                    InputStream in = new BufferedInputStream(url.openStream());
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] buf = new byte[1024];
                    int n = 0;
                    while (-1 != (n = in.read(buf))) {
                        out.write(buf, 0, n);
                    }
                    out.close();
                    in.close();
                    byte[] response = out.toByteArray();
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(response);
                    fos.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public void DownloadFriend(Context context, String accountID) {
        String friendName = "";
        BLLFriend bllFriend = new BLLFriend(context);
        List<FriendTable> friendTableList = bllFriend.SelectListBySteamID(Long.parseLong(accountID.toString()) + 76561197960265728L);
        try {
            for (int n = 0; n < friendList.size(); n++) {
                LoadingActivity.fileSize--;
                FriendTable friendTable = new FriendTable(
                        Long.parseLong(accountID.toString()) + 76561197960265728L,
                        Long.parseLong(friendList.get(n).get("steamid").toString()),
                        friendList.get(n).get("relationship").toString(),
                        Long.parseLong(friendList.get(n).get("friend_since").toString()));
                //download friend data
                if (friendTableList.size() > n) {
                    if (friendTableList.get(n).contentValues.get(FriendTable.KEY_steamid) == accountID.toString() &&
                            friendTableList.get(n).contentValues.get(FriendTable.KEY_friendsteamid) == friendList.get(n).get("steamid").toString()) {
                        bllFriend.update(friendTable);
                    }
                } else {
                    //download friend data
                    bllFriend.insert(friendTable);
                }

                JSON2Player json2Player = new JSON2Player();
                String playerID = String.valueOf(Long.parseLong(friendList.get(n).get("steamid").toString()) - 76561197960265728L);
                json2Player.GetData(playerID);
                //download player data
                BLLUser bllUser = new BLLUser(context);
                UserTable userTable = bllUser.SelectBySteamID(Long.parseLong(json2Player.JSONList.get(0).get("steamid").toString()));
                String realName = "";
                String country = "";
                String timecreated = "";
                String visibility = "";
                try {
                    timecreated = json2Player.JSONList.get(0).get("timecreated").toString();
                } catch (Exception e) {
                }
                try {
                    realName = json2Player.JSONList.get(0).get("realname").toString();
                } catch (Exception e) {
                }
                try {
                    country = json2Player.JSONList.get(0).get("loccountrycode").toString();
                } catch (Exception e) {
                }
                try {
                    visibility = json2Player.JSONList.get(0).get("communityvisibilitystate").toString();
                } catch (Exception e) {
                }
                if (userTable.contentValues == null) {
                    userTable =
                            new UserTable(
                                    Long.parseLong(json2Player.JSONList.get(0).get("steamid").toString()),
                                    visibility,
                                    json2Player.JSONList.get(0).get("personaname").toString(),
                                    json2Player.JSONList.get(0).get("lastlogoff").toString(),
                                    userTable.GetStatus(json2Player.JSONList.get(0).get("personastate").toString()),
                                    json2Player.JSONList.get(0).get("profileurl").toString(),
                                    realName,
                                    timecreated,
                                    country);
                    int insertId = bllUser.insert(userTable);
                } else {
                    userTable =
                            new UserTable(
                                    Long.parseLong(json2Player.JSONList.get(0).get("steamid").toString()),
                                    visibility,
                                    json2Player.JSONList.get(0).get("personaname").toString(),
                                    json2Player.JSONList.get(0).get("lastlogoff").toString(),
                                    userTable.GetStatus(json2Player.JSONList.get(0).get("personastate").toString()),
                                    json2Player.JSONList.get(0).get("profileurl").toString(),
                                    realName,
                                    timecreated,
                                    country);
                    bllUser.update(userTable);
                }
                //download Icon
                File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Player_" + playerID + ".jpg");
                URL url = new URL(json2Player.JSONList.get(0).get("avatarfull").toString());
                InputStream in = new BufferedInputStream(url.openStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int m = 0;
                while (-1 != (m = in.read(buf))) {
                    out.write(buf, 0, m);
                }
                out.close();
                in.close();
                byte[] response = out.toByteArray();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(response);
                fos.close();
            }

        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void DownloadPlayer(Context context, String accountID) {
        try {
            JSON2Player json2Player = new JSON2Player();
            json2Player.GetData(accountID);

            //insert search record
            Long steamID = Long.parseLong(json2Player.JSONList.get(0).get("steamid").toString());
            if(steamID == Long.parseLong(String.valueOf(Integer.parseInt(LoginActivity.accountID) + 76561197960265728L))){
            BLLSearch bllSearch = new BLLSearch(context);
            SearchTable searchTable = bllSearch.SelectBySteamID(steamID);
            if (searchTable.contentValues == null) {
                searchTable =
                        new SearchTable(
                                steamID,
                                1);
                int insertId = bllSearch.insert(searchTable);
            } else {
                searchTable =
                        new SearchTable(
                                steamID,
                                Integer.parseInt(searchTable.contentValues.get(SearchTable.KEY_searchtimes).toString()) + 1);
                bllSearch.update(searchTable);
            }}

            //download player data
            BLLUser bllUser = new BLLUser(context);
            UserTable userTable = bllUser.SelectBySteamID(Long.parseLong(json2Player.JSONList.get(0).get("steamid").toString()));
            String realName = "";
            String country = "";
            String timecreated = "";
            String visibility = "";
            try {
                timecreated = json2Player.JSONList.get(0).get("timecreated").toString();
            } catch (Exception e) {
            }
            try {
                realName = json2Player.JSONList.get(0).get("realname").toString();
            } catch (Exception e) {
            }
            try {
                country = json2Player.JSONList.get(0).get("loccountrycode").toString();
            } catch (Exception e) {
            }
            try {
                visibility = json2Player.JSONList.get(0).get("communityvisibilitystate").toString();
            } catch (Exception e) {
            }
            if (userTable.contentValues == null) {
                userTable =
                        new UserTable(
                                Long.parseLong(json2Player.JSONList.get(0).get("steamid").toString()),
                                visibility,
                                json2Player.JSONList.get(0).get("personaname").toString(),
                                json2Player.JSONList.get(0).get("lastlogoff").toString(),
                                userTable.GetStatus(json2Player.JSONList.get(0).get("personastate").toString()),
                                json2Player.JSONList.get(0).get("profileurl").toString(),
                                realName,
                                timecreated,
                                country);
                int insertId = bllUser.insert(userTable);
            } else {
                userTable =
                        new UserTable(
                                Long.parseLong(json2Player.JSONList.get(0).get("steamid").toString()),
                                visibility,
                                json2Player.JSONList.get(0).get("personaname").toString(),
                                json2Player.JSONList.get(0).get("lastlogoff").toString(),
                                userTable.GetStatus(json2Player.JSONList.get(0).get("personastate").toString()),
                                json2Player.JSONList.get(0).get("profileurl").toString(),
                                realName,
                                timecreated,
                                country);
                bllUser.update(userTable);
            }

            //download Icon
            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Player_" + accountID + ".jpg");
                URL url = new URL(json2Player.JSONList.get(0).get("avatarfull").toString());
                InputStream in = new BufferedInputStream(url.openStream());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int n = 0;
                while (-1 != (n = in.read(buf))) {
                    out.write(buf, 0, n);
                }
                out.close();
                in.close();
                byte[] response = out.toByteArray();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(response);
                fos.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void DownloadMatch(Context context, String accountID) {
        try {
            //download data
            BLLMatch bllMatch = new BLLMatch(context);
            BLLPlayer bllPlayer = new BLLPlayer(context);

            for (int n = matchList.size()-1; n >= 0; n--) {
                try {
                    LoadingActivity.fileSize--;
                    Long matchid = Long.parseLong(matchList.get(n).get("match_id").toString());
                    List<PlayerTable> playerList = bllPlayer.SelectListBymatchID(matchid);
                    JSON2MatchDetails json2MatchDetails = new JSON2MatchDetails();
                    if (playerList.size() <= 0) {
                        json2MatchDetails.GetData(matchid);
                        String accountid = "";
                        Date dateStart = new Date();
                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        dateStart.setTime(Long.parseLong(matchList.get(n).get("start_time").toString()) * 1000);
                        String startime = df.format(dateStart);
                        EnumLobbyType enumLobbyType = new EnumLobbyType(matchList.get(n).get("lobby_type").toString());
                        String lobby = enumLobbyType.LobbyType;
                        String mode = "";
                        String result = "";
                        Long duration = 0L;
                        String hero = "";
                        String radiant = "";
                        String level = "";
                        String hero_damage = "";
                        String tower_damage = "";
                        String gold = "";
                        String kills = "";
                        String deaths = "";
                        String assists = "";

                        for (int m = 0; m < json2MatchDetails.JSONList.size(); m++) {
                            if (m == 0) {
                                EnumGameMode enumGameMode = new EnumGameMode(json2MatchDetails.JSONList.get(m).get("game_mode").toString());
                                mode = enumGameMode.GameMode;
                                duration = Long.parseLong(json2MatchDetails.JSONList.get(m).get("duration").toString());
                                result = json2MatchDetails.JSONList.get(m).get("radiant_win").toString();
                            } else {
                                accountid = json2MatchDetails.JSONList.get(m).get("account_id").toString();

                                if (Integer.parseInt(json2MatchDetails.JSONList.get(m).get("player_slot").toString()) > 127) {
                                    radiant = "false";
                                } else {
                                    radiant = "true";
                                }

                                hero = heroMap.get(json2MatchDetails.JSONList.get(m).get("hero_id").toString()).toString().replace("npc_dota_hero_", "");
                                level = json2MatchDetails.JSONList.get(m).get("level").toString();
                                hero_damage = json2MatchDetails.JSONList.get(m).get("hero_damage").toString();
                                tower_damage = json2MatchDetails.JSONList.get(m).get("tower_damage").toString();
                                gold = json2MatchDetails.JSONList.get(m).get("gold_per_min").toString();
                                kills = json2MatchDetails.JSONList.get(m).get("kills").toString();
                                deaths = json2MatchDetails.JSONList.get(m).get("deaths").toString();
                                assists = json2MatchDetails.JSONList.get(m).get("assists").toString();

                                //download Icon
                                if(!accountid.equals("4294967295")) {
                                    DownloadPlayer(context,accountid);
                                }
                                MatchTable mt = bllMatch.SelectBymatchID(matchid);
                                if(mt.contentValues==null) {
                                    MatchTable matchTable = new MatchTable(
                                            matchid,
                                            startime,
                                            lobby,
                                            mode,
                                            duration,
                                            result);
                                    int matchID = bllMatch.insert(matchTable);
                                }

                                PlayerTable playerTable = new PlayerTable(
                                        matchid,
                                        accountid,
                                        hero,
                                        radiant,
                                        level,
                                        hero_damage,
                                        tower_damage,
                                        gold,
                                        kills,
                                        deaths,
                                        assists);
                                int playerID = bllPlayer.insert(playerTable);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
