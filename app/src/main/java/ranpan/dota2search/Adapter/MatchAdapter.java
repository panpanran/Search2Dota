package ranpan.dota2search.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import Convert.CImage;
import Data.MatchData;
import Database.BusinessLogicLayer.BLLMatch;
import Database.BusinessLogicLayer.BLLPlayer;
import Database.BusinessLogicLayer.BLLUser;
import Database.Table.MatchTable;
import Database.Table.PlayerTable;
import Database.Table.UserTable;
import ranpan.dota2search.LoadingActivity;
import ranpan.dota2search.LoginActivity;
import ranpan.dota2search.MainActivity;
import ranpan.dota2search.MatchFragment;
import ranpan.dota2search.R;
import ranpan.dota2search.RelatedFragment;

/**
 * Created by panpanr on 7/14/2015.
 */
public class MatchAdapter extends BaseExpandableListAdapter {
    private Context ctx;
    private Map<Long, List<String>> MatchList;
    private List<Long> playerList;

    public MatchAdapter(Context ctx, Map<Long, List<String>> MatchList, List<Long> playerList) {
        this.ctx = ctx;
        this.MatchList = MatchList;
        this.playerList = playerList;
    }

    @Override
    public Object getChild(int parent, int child) {
        return MatchList.get(playerList.get(parent)).get(child);
    }

    @Override
    public long getChildId(int parent, int child) {
        // TODO Auto-generated method stub
        return child;
    }

    @Override
    public View getChildView(int parent, int child, boolean lastChild, View convertview,
                             ViewGroup parentview) {
        String child_title = (String) getChild(parent, child);
        if (convertview == null) {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview = inflator.inflate(R.layout.activity_matchrow, parentview, false);
        }

        final int playerid = Integer.parseInt(child_title);
        Context context = convertview.getContext();
        BLLPlayer bllPlayer = new BLLPlayer(context);
        final PlayerTable playerTable = bllPlayer.SelectListByplayerid(playerid);

        if (!child_title.equals("9999999")) {
            BLLMatch bllMatch = new BLLMatch(context);
            MatchTable matchTable = bllMatch.SelectBymatchID(playerTable.contentValues.getAsLong(PlayerTable.KEY_matchid));

            File imgFile = new File(ctx.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "hero_" + playerTable.contentValues.get(PlayerTable.KEY_hero) + ".jpg");
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                myBitmap = CImage.cropCricle(myBitmap);
                ImageView imgHero = (ImageView) convertview.findViewById(R.id.imgMatchHero);
                imgHero.setImageBitmap(myBitmap);
                imgHero.setVisibility(View.VISIBLE);
            }
            String accountid = playerTable.contentValues.get(PlayerTable.KEY_accountid).toString();
            if (!accountid.equals("4294967295")) {
                File imgPlayer = new File(ctx.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Player_" + playerTable.contentValues.get(PlayerTable.KEY_accountid).toString() + ".jpg");
                if (imgPlayer.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgPlayer.getAbsolutePath());
                    ImageView imgGroup = (ImageView) convertview.findViewById(R.id.imgMatchPlayer);
                    imgGroup.setImageBitmap(myBitmap);
                    imgGroup.setVisibility(View.VISIBLE);
                }
            } else {
                Drawable d = context.getResources().getDrawable(R.drawable.questionmark);
                ImageView image = (ImageView) convertview.findViewById(R.id.imgMatchPlayer);
                image.setImageDrawable(d);
                image.setVisibility(View.VISIBLE);
            }

            TextView txtLevel = (TextView) convertview.findViewById(R.id.txtMatchLevel);
            txtLevel.setText(playerTable.contentValues.get(PlayerTable.Key_level).toString());
            txtLevel.setTextColor(Color.WHITE);
            txtLevel.setVisibility(View.VISIBLE);

            TextView txtKDA = (TextView) convertview.findViewById(R.id.txtMatchKDA);
            txtKDA.setText(playerTable.contentValues.get(PlayerTable.Key_kills).toString() + "|" + playerTable.contentValues.get(PlayerTable.Key_deaths).toString() + "|" + playerTable.contentValues.get(PlayerTable.Key_assists).toString());
            txtKDA.setTextColor(Color.WHITE);

            final View temp = convertview;
            ImageView imgMatchStat = (ImageView) temp.findViewById(R.id.imgMatchStat);
            try {
                final File imgFileOutput = new File(temp.getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Score_" + playerTable.contentValues.get(PlayerTable.KEY_playerid) + ".png");
                if (!imgFileOutput.exists()) {
                    try {
                        Map<String, Float> mapScore = MatchData.getMatchScore(temp.getContext(), playerid);
                        int height = 200;
                        int width = 200;
                        Bitmap bmp1 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                        Point p1 = new Point(width / 2, height / 2);
                        CImage.DrawHexagonal(bmp1, height, width, Color.YELLOW);
                        CImage.FloodFill(bmp1, p1, bmp1.getPixel(p1.x, p1.y), Color.YELLOW);
                        Canvas canvas = new Canvas(bmp1);
                        Bitmap bmp2 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                        CImage.DrawScoreHexagonal(bmp2, height, width, mapScore, Color.BLUE);
                        canvas.drawBitmap(bmp2, 0, 0, null);
                        Point p2 = new Point(width / 2, height / 2);
                        CImage.FloodFill(bmp1, p2, bmp1.getPixel(p2.x, p2.y), Color.BLUE);
                        imgMatchStat.setImageBitmap(bmp1);
                        imgMatchStat.setVisibility(View.VISIBLE);
                        FileOutputStream fOut = new FileOutputStream(imgFileOutput);
                        bmp1.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                        fOut.flush();
                        fOut.close();
                    } catch (Exception e) {
                        e.getMessage();
                    }
                } else {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFileOutput.getAbsolutePath());
                    imgMatchStat.setImageBitmap(myBitmap);
                    imgMatchStat.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.getMessage();
            }

            imgMatchStat.setOnClickListener(new View.OnClickListener() {
                //@Override
                public void onClick(View v) {
                    try {
                        MatchFragment.imgMatch.setImageResource(0);
                        MatchData.ShowScore(temp, playerid, MatchFragment.imgMatch);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });

            TextView txtMatchStat = (TextView) convertview.findViewById(R.id.txtMatchStat);
            txtMatchStat.setVisibility(View.GONE);

            TextView txtMatchLogin = (TextView) convertview.findViewById(R.id.txtMatchLogin);
            txtMatchLogin.setVisibility(View.GONE);

            TextView txtMatchRelated = (TextView) convertview.findViewById(R.id.txtMatchRelated);
            txtMatchRelated.setVisibility(View.GONE);

            TextView txtMatchHero = (TextView) convertview.findViewById(R.id.txtMatchHero);
            txtMatchHero.setVisibility(View.GONE);
            TextView txtMatchPlayer = (TextView) convertview.findViewById(R.id.txtMatchPlayer);
            txtMatchPlayer.setVisibility(View.GONE);

            ImageView btnMatchLogin = (ImageView) convertview.findViewById(R.id.btnMatchLogin);
            Bitmap eyeBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.eyebutton);
            Bitmap eyeResize = Bitmap.createScaledBitmap(eyeBitmap, 200, 200, true);
            btnMatchLogin.setImageBitmap(eyeResize);

            btnMatchLogin.setOnClickListener(new View.OnClickListener() {
                //@Override
                public void onClick(View v) {
                    try {
                        LoginActivity.accountID = playerTable.contentValues.getAsString(PlayerTable.KEY_accountid);
                        ctx.startActivity(new Intent(ctx, LoadingActivity.class));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
            ImageView btnMatchRelated = (ImageView) convertview.findViewById(R.id.btnMatchRelated);
            btnMatchRelated.setImageBitmap(eyeResize);
            btnMatchRelated.setOnClickListener(new View.OnClickListener() {
                //@Override
                public void onClick(View v) {
                    try {
                        RelatedFragment.relatedAccountID = playerTable.contentValues.getAsString(PlayerTable.KEY_accountid);
                        ctx.startActivity(new Intent(ctx, MainActivity.class));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });

            if (!accountid.equals("4294967295") && !accountid.equals(LoginActivity.accountID)) {
                BLLUser bllUser = new BLLUser(ctx);
                UserTable userTable = bllUser.SelectBySteamID(Long.parseLong(String.valueOf(Integer.parseInt(accountid)+ 76561197960265728L)));
                String strVisiblity = String.valueOf(userTable.contentValues.get(UserTable.KEY_visibility));
                if (Integer.parseInt(strVisiblity) == 3 ) {
                    btnMatchLogin.setVisibility(View.VISIBLE);
                }else{
                    btnMatchLogin.setVisibility(View.GONE);
                }
                Map<Long, List<String>> sameMatchList = MatchData.getRelatedMatch(context, accountid);
                if(sameMatchList.size() > 1)
                {
                    btnMatchRelated.setVisibility(View.VISIBLE);
                }
                else{
                    btnMatchRelated.setVisibility(View.GONE);
                }
            }else{
                btnMatchLogin.setVisibility(View.GONE);
                btnMatchRelated.setVisibility(View.GONE);
            }

            LinearLayout relmatchChild = (LinearLayout) convertview.findViewById(R.id.relmatchChild);
            if (playerTable.contentValues.get(PlayerTable.Key_radiant).equals(matchTable.contentValues.get(MatchTable.KEY_result))) {
                relmatchChild.setBackgroundColor(Color.rgb(0, 60, 0));
            } else {
                relmatchChild.setBackgroundColor(Color.rgb(99, 0, 0));
            }
        } else {
            LinearLayout relmatchChild = (LinearLayout) convertview.findViewById(R.id.relmatchChild);
            relmatchChild.setBackgroundColor(Color.rgb(0, 0, 0));

            ImageView imgHero = (ImageView) convertview.findViewById(R.id.imgMatchHero);
            imgHero.setVisibility(View.GONE);
            ImageView imgPlayer = (ImageView) convertview.findViewById(R.id.imgMatchPlayer);
            imgPlayer.setVisibility(View.GONE);

            TextView txtLevel = (TextView) convertview.findViewById(R.id.txtMatchLevel);
            txtLevel.setText("Level");
            txtLevel.setTextColor(Color.YELLOW);

            TextView txtKDA = (TextView) convertview.findViewById(R.id.txtMatchKDA);
            txtKDA.setText("K|D|A");
            txtKDA.setTextColor(Color.YELLOW);

            TextView txtMatchStat = (TextView) convertview.findViewById(R.id.txtMatchStat);
            txtMatchStat.setText("Stat");
            txtMatchStat.setVisibility(View.VISIBLE);
            txtMatchStat.setTextColor(Color.YELLOW);

            TextView txtMatchLogin = (TextView) convertview.findViewById(R.id.txtMatchLogin);
            txtMatchLogin.setText("Login");
            txtMatchLogin.setVisibility(View.VISIBLE);
            txtMatchLogin.setTextColor(Color.YELLOW);

            TextView txtMatchRelated = (TextView) convertview.findViewById(R.id.txtMatchRelated);
            txtMatchRelated.setText("Related");
            txtMatchRelated.setVisibility(View.VISIBLE);
            txtMatchRelated.setTextColor(Color.YELLOW);

            TextView txtMatchHero = (TextView) convertview.findViewById(R.id.txtMatchHero);
            txtMatchHero.setText("Hero");
            txtMatchHero.setTextColor(Color.YELLOW);
            txtMatchHero.setVisibility(View.VISIBLE);
            TextView txtMatchPlayer = (TextView) convertview.findViewById(R.id.txtMatchPlayer);
            txtMatchPlayer.setText("Player");
            txtMatchPlayer.setTextColor(Color.YELLOW);
            txtMatchPlayer.setVisibility(View.VISIBLE);

            ImageView imgMatchStat = (ImageView) convertview.findViewById(R.id.imgMatchStat);
            imgMatchStat.setVisibility(View.GONE);

            ImageView btnMatchLogin = (ImageView) convertview.findViewById(R.id.btnMatchLogin);
            btnMatchLogin.setVisibility(View.GONE);
            ImageView btnMatchRelated = (ImageView) convertview.findViewById(R.id.btnMatchRelated);
            btnMatchRelated.setVisibility(View.GONE);
        }
        return convertview;
    }

    @Override
    public int getChildrenCount(int arg0) {
        return MatchList.get(playerList.get(arg0)).size();
    }

    @Override
    public Object getGroup(int arg0) {
        // TODO Auto-generated method stub
        return playerList.get(arg0);
    }

    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return playerList.size();
    }

    @Override
    public long getGroupId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertview, ViewGroup parentview) {
        // TODO Auto-generated method stub
        Long group_title = Long.parseLong(getGroup(parent).toString());
        if (convertview == null) {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview = inflator.inflate(R.layout.activity_matchgroup, parentview, false);
        }

        ctx = convertview.getContext();
        Context context = convertview.getContext();
        BLLPlayer bllPlayer = new BLLPlayer(context);
        PlayerTable playerTable = bllPlayer.SelectListBymatchIDByaccountID(group_title, LoginActivity.accountID);
        BLLMatch bllMatch = new BLLMatch(context);
        MatchTable matchTable = bllMatch.SelectBymatchID(group_title);
        File imgFile = new File(ctx.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "hero_" + playerTable.contentValues.get(PlayerTable.KEY_hero) + ".jpg");
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView imgGroup = (ImageView) convertview.findViewById(R.id.imgHero);
            imgGroup.setImageBitmap(myBitmap);
        }

        TextView txtMode = (TextView) convertview.findViewById(R.id.txtTime);
        txtMode.setText(matchTable.contentValues.get(MatchTable.KEY_starttime).toString());
        txtMode.setTextColor(Color.WHITE);
        TextView txtLobby = (TextView) convertview.findViewById(R.id.txtLobby);
        txtLobby.setText(matchTable.contentValues.get(MatchTable.KEY_lobby).toString());
        txtLobby.setTextColor(Color.YELLOW);
        TextView textViewResult = (TextView) convertview.findViewById(R.id.txtResult);
        if (playerTable.contentValues.get(PlayerTable.Key_radiant).equals(matchTable.contentValues.get(MatchTable.KEY_result))) {
            textViewResult.setTextColor(Color.rgb(0, 140, 0));
            textViewResult.setText("Won");
        } else {
            textViewResult.setTextColor(Color.rgb(180, 0, 0));
            textViewResult.setText("Lost");
        }

        return convertview;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return true;
    }
}
