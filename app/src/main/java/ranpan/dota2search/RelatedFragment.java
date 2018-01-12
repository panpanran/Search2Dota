package ranpan.dota2search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Convert.CImage;
import Data.MatchData;
import ranpan.dota2search.Adapter.MatchAdapter;

/**
 * Created by panpanr on 7/10/2015.
 */
public class RelatedFragment extends Fragment {

    Map<Long, List<String>> matchList = new TreeMap<>();
    List<Long> playerList;
    ExpandableListView Exp_list;
    MatchAdapter adapter;
    public static String relatedAccountID = "";
    public Context context;
    ;
    public static ImageView imgMatch;
    View rootView;

    public Context getContext() {
        return context;
    }

    public RelatedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        new RelatedTask().execute();
    }

    public class RelatedTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                File imgFileMe = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Player_" + LoginActivity.accountID + ".jpg");
                if (imgFileMe.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFileMe.getAbsolutePath());
                    myBitmap = CImage.getCircularBitmapWithWhiteBorder(myBitmap, 5);
                    ImageView imgRelatedMe = (ImageView) getView().findViewById(R.id.imgRelatedMe);
                    imgRelatedMe.setImageBitmap(myBitmap);
                }
            } catch (Exception e) {
                e.getMessage();
            }

            try {
                {
                    File imgFileOther = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Player_" + RelatedFragment.relatedAccountID + ".jpg");
                    if (imgFileOther.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFileOther.getAbsolutePath());
                        myBitmap = CImage.getCircularBitmapWithWhiteBorder(myBitmap, 5);
                        ImageView imgRelatedOther = (ImageView) getView().findViewById(R.id.imgRelatedOther);
                        imgRelatedOther.setImageBitmap(myBitmap);
                    }
                }
            } catch (Exception e) {
                e.getMessage();
            }

            try {
                playerList = new ArrayList<Long>(matchList.keySet());
                adapter = new MatchAdapter(context, matchList, playerList);
                Exp_list = (ExpandableListView) getView().findViewById(R.id.relatedExpandlist);
                Exp_list.setAdapter(adapter);
//                Exp_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//                    @Override
//                    public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
//                        String child_title = (String) adapter.getChild(groupPosition, childPosition);
//                        imgMatch.setImageResource(0);
//                        MatchData.ShowScore(getView(), Integer.parseInt(child_title), imgMatch);
//                        return false;
//                    }
//                });
                Exp_list.setGroupIndicator(null);
            } catch (Exception e) {
                e.getMessage();
            }
        }

        @Override
        protected String doInBackground(String... url) {
            try {
                matchList = MatchData.getRelatedMatch(context, RelatedFragment.relatedAccountID);
            } catch (Exception e) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_related, container, false);
        imgMatch = (ImageView) rootView.findViewById(R.id.imgRelatedScore);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}