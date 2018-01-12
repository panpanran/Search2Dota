package ranpan.dota2search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Convert.CImage;
import Data.FriendData;
import Data.MatchData;
import Database.Table.MatchTable;
import JSON2Data.JSON2Player;
import ranpan.dota2search.Adapter.FriendAdapter;
import ranpan.dota2search.Adapter.MatchAdapter;

/**
 * Created by panpanr on 7/10/2015.
 */
public class MainActivity extends ActionBarActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private boolean visibilityEditText = false;
    private boolean boolFriendFragment = false;
    private boolean boolMatchFragment = false;
    private boolean boolRelatedFragment = false;
    MenuItem menuItem;

    HashMap<String, List<String>> friendList = new HashMap<String, List<String>>();
    Map<Long, List<String>> matchList = new TreeMap<>();
    List<String> playerList;
    List<Long> matchPlayerList;
    ExpandableListView Exp_list;
    FriendAdapter friendAdapter;
    MatchAdapter matchAdapter;
    public Context context;
    public static EditText searchText;
    TextView emptyView;
    ImageView imgRelatedOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchText = (EditText) findViewById(R.id.txtSearch);
        context = this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        File imgFile = new File(this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Player_" + LoginActivity.accountID + ".jpg");
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            myBitmap = CImage.getCircularBitmapWithWhiteBorder(myBitmap, 5);
            ImageView imgMe = (ImageView) findViewById(R.id.imgMe);
            imgMe.setImageBitmap(myBitmap);
        }

        //Data
        if (RelatedFragment.relatedAccountID != "") {
            displayView(3);
        } else {
            displayView(0);
        }
    }

    public class mainFriendTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                playerList = new ArrayList<String>(friendList.keySet());
                friendAdapter = new FriendAdapter(context, friendList, playerList);
                Exp_list = (ExpandableListView) findViewById(R.id.friendExpandList);
                emptyView = (TextView) findViewById(R.id.txtFriendEmpty);
                Exp_list.setEmptyView(emptyView);
                Exp_list.setAdapter(friendAdapter);
                Exp_list.setGroupIndicator(null);
            } catch (Exception e) {
                e.getMessage();
            }
        }

        @Override
        protected String doInBackground(String... url) {
            try {
                friendList = FriendData.getSelected(context, searchText.getText().toString());
            } catch (Exception e) {
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
            return null;
        }
    }

    public class mainMatchTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                matchPlayerList = new ArrayList<Long>(matchList.keySet());
                matchAdapter = new MatchAdapter(context, matchList, matchPlayerList);
                Exp_list = (ExpandableListView) findViewById(R.id.matchExpandlist);
//                emptyView = (TextView) findViewById(R.id.txtMatchEmpty);
//                Exp_list.setEmptyView(emptyView);
                Exp_list.setAdapter(matchAdapter);
                Exp_list.setGroupIndicator(null);
            } catch (Exception e) {
                e.getMessage();
            }
        }

        @Override
        protected String doInBackground(String... url) {
            try {
                matchList = MatchData.getSelected(context, searchText.getText().toString());
            } catch (Exception e) {
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
            return null;
        }
    }

    public class mainRelatedTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                imgRelatedOther = (ImageView) findViewById(R.id.imgRelatedOther);
                Bitmap myBitmap = null;
                imgRelatedOther.setImageBitmap(myBitmap);
                File imgFileOther = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Player_" + RelatedFragment.relatedAccountID + ".jpg");
                if (imgFileOther.exists()) {
                    myBitmap = BitmapFactory.decodeFile(imgFileOther.getAbsolutePath());
                    myBitmap = CImage.getCircularBitmapWithWhiteBorder(myBitmap, 5);
                    imgRelatedOther.setImageBitmap(myBitmap);
                }
            } catch (Exception e) {
                e.getMessage();
            }
            try {
                matchPlayerList = new ArrayList<Long>(matchList.keySet());
                matchAdapter = new MatchAdapter(context, matchList, matchPlayerList);
                Exp_list = (ExpandableListView) findViewById(R.id.relatedExpandlist);
//                emptyView = (TextView) findViewById(R.id.txtRelatedEmpty);
//                Exp_list.setEmptyView(emptyView);
                Exp_list.setAdapter(matchAdapter);
                Exp_list.setGroupIndicator(null);
            } catch (Exception e) {
                e.getMessage();
            }
        }

        @Override
        protected String doInBackground(String... urll) {
            matchList = new TreeMap<Long, List<String>>();
            try {
                JSON2Player json2Player = new JSON2Player();
                json2Player.GetData(RelatedFragment.relatedAccountID);
                File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Player_" + RelatedFragment.relatedAccountID + ".jpg");
                if (!file.exists()) {
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
                }
                matchList = MatchData.getRelatedMatchSelected(context, RelatedFragment.relatedAccountID, searchText.getText().toString());
            } catch (Exception e) {
                e.getMessage();
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Get the SearchView and set the searchable configuration
        menuItem = menu.findItem(R.id.action_search);
        if (boolFriendFragment | boolMatchFragment | boolRelatedFragment) {
            menuItem.setVisible(true);
        } else {
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {

            visibilityEditText = !visibilityEditText;
            searchText = (EditText) findViewById(R.id.txtSearch);
            if (visibilityEditText) {
                if (boolFriendFragment) {
                    searchText.setHint("Name / Status");
                }
                if (boolMatchFragment) {
                    searchText.setHint("Hero / Type / Start Time");
                }
                if (boolRelatedFragment) {
                    searchText.setHint("Hero / Type / Start Time");
                }
                searchText.setVisibility(View.VISIBLE);
            } else {
                if (boolFriendFragment) {
                    new mainFriendTask().execute();
                }
                if (boolMatchFragment) {
                    new mainMatchTask().execute();
                }
                if (boolRelatedFragment) {
//                    RelatedFragment.relatedAccountID = searchText.getText().toString();
                    new mainRelatedTask().execute();
                }
                searchText.setVisibility(View.GONE);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent;
        if (boolFriendFragment) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
        } else if (boolMatchFragment) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
        } else if (boolRelatedFragment) {
            RelatedFragment.relatedAccountID = "";
            intent = new Intent(getApplicationContext(), MainActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        }
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    Fragment fragment = null;

    private void displayView(int position) {
        String title = getString(R.string.app_name);
        boolFriendFragment = false;
        boolMatchFragment = false;
        boolRelatedFragment = false;
        switch (position) {
            case 0:
                title = "Home";
                RelatedFragment.relatedAccountID = "";
                fragment = new HomeFragment();
                break;
            case 1:
                boolFriendFragment = true;
                title = "Friend";
                RelatedFragment.relatedAccountID = "";
                fragment = new FriendFragment();
                break;
            case 2:
                boolMatchFragment = true;
                title = "Match";
                RelatedFragment.relatedAccountID = "";
                fragment = new MatchFragment();
                break;
            case 3:
                boolRelatedFragment = true;
                title = "Related Match";
                fragment = new RelatedFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}
