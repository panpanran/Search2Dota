package ranpan.dota2search.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import Database.BusinessLogicLayer.BLLUser;
import Database.Table.UserTable;
import ranpan.dota2search.LoadingActivity;
import ranpan.dota2search.LoginActivity;
import ranpan.dota2search.MainActivity;
import ranpan.dota2search.R;
import ranpan.dota2search.RelatedFragment;

/**
 * Created by panpanr on 6/20/2015.
 */
public class FriendAdapter extends BaseExpandableListAdapter {
    private Context ctx;
    private HashMap<String, List<String>> friendList;
    public HashMap<String, List<String>> outputList = new HashMap<String, List<String>>();
    private List<String> playerList;

    public FriendAdapter(Context ctx, HashMap<String, List<String>> friendList, List<String> playerList) {
        this.ctx = ctx;
        this.friendList = friendList;
        this.outputList.putAll(friendList);
        this.playerList = playerList;
    }

    @Override
    public Object getChild(int parent, int child) {
        return friendList.get(playerList.get(parent)).get(child);
    }

    @Override
    public long getChildId(int parent, int child) {
        // TODO Auto-generated method stub
        return child;
    }

    @Override
    public View getChildView(int parent, int child, boolean lastChild, View convertview,
                             ViewGroup parentview) {
        final String child_title = (String) getChild(parent, child);
        if (convertview == null) {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview = inflator.inflate(R.layout.activity_friendrow, parentview, false);
        }
        Button friendButton = (Button) convertview.findViewById(R.id.btnFriend);
        friendButton.setVisibility(View.GONE);
        Button matchButton = (Button) convertview.findViewById(R.id.btnMatch);
        matchButton.setVisibility(View.GONE);
        friendButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            LoginActivity.accountID = String.valueOf(Long.parseLong(child_title) - 76561197960265728L);
                            ctx.startActivity(new Intent(ctx, LoadingActivity.class));
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
        );

        matchButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        try {
                            RelatedFragment.relatedAccountID = String.valueOf(Long.parseLong(child_title) - 76561197960265728L);
                            ctx.startActivity(new Intent(ctx, MainActivity.class));
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
        );

        TextView child_textview = (TextView) convertview.findViewById(R.id.child_txt);
        child_textview.setTextColor(Color.WHITE);
        if (child == 0) {
            BLLUser bllUser = new BLLUser(ctx);
            UserTable userTable = bllUser.SelectBySteamID(Long.parseLong(child_title));
            String strVisiblity = String.valueOf(userTable.contentValues.get(UserTable.KEY_visibility));
            if (Integer.parseInt(strVisiblity) == 3) {
                friendButton.setVisibility(View.VISIBLE);
            }else{
                friendButton.setVisibility(View.INVISIBLE);
            }
            matchButton.setVisibility(View.VISIBLE);
            child_textview.setVisibility(View.GONE);

        } else {
            child_textview.setVisibility(View.VISIBLE);
            child_textview.setText(Html.fromHtml(child_title));
            child_textview.setMovementMethod(LinkMovementMethod.getInstance());
        }

        return convertview;
    }

    @Override
    public int getChildrenCount(int arg0) {
        return friendList.get(playerList.get(arg0)).size();
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
        String group_title = (String) getGroup(parent);
        if (convertview == null) {
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview = inflator.inflate(R.layout.activity_friendgroup, parentview, false);
        }

        ctx = convertview.getContext();
        File imgFile = new File(ctx.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Player_" + group_title + ".jpg");
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView imgGroup = (ImageView) convertview.findViewById(R.id.imgGroup);
            imgGroup.setImageBitmap(myBitmap);
        }
        Context context = convertview.getContext();
        BLLUser bllUser = new BLLUser(context);
        UserTable userTable = bllUser.SelectBySteamID(Long.parseLong(group_title) + 76561197960265728L);
        TextView txtName = (TextView) convertview.findViewById(R.id.txtplayerName);
        txtName.setText(userTable.contentValues.get(UserTable.KEY_name).toString());
        txtName.setTextColor(Color.WHITE);
        TextView txtStatus = (TextView) convertview.findViewById(R.id.txtplayerStatus);
        txtStatus.setText(userTable.contentValues.get(UserTable.KEY_status).toString());
        int colorStatus = userTable.GetColor(userTable.contentValues.get(UserTable.KEY_status).toString());
        txtStatus.setTextColor(colorStatus);
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
        return false;
    }
}