package ranpan.dota2search;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Data.MatchData;
import Database.Table.UserTable;
import ranpan.dota2search.Adapter.MatchAdapter;

/**
 * Created by panpanr on 7/10/2015.
 */
public class MatchFragment extends Fragment{

    Map<Long, List<String>> matchList = new TreeMap<>();
    List<Long> playerList;
    ExpandableListView Exp_list;
    MatchAdapter adapter;
    View rootView;
    public Context context;
    public static ImageView imgMatch;
    AlertDialog.Builder dialogBuilder;

    public MatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        new MatchTask().execute();
    }

    public class MatchTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                playerList = new ArrayList<Long>(matchList.keySet());
                adapter = new MatchAdapter(context, matchList, playerList);
                Exp_list = (ExpandableListView) getView().findViewById(R.id.matchExpandlist);
                Exp_list.setAdapter(adapter);
//                Exp_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//                    @Override
//                    public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {
//                        String child_title = (String) adapter.getChild(groupPosition, childPosition);
//                        imgMatch.setImageResource(0);
//                        MatchData.ShowScore(getView(),Integer.parseInt(child_title),imgMatch);
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
                matchList = MatchData.getAll(context);
            } catch (Exception e) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_match, container, false);
        imgMatch = (ImageView)rootView.findViewById(R.id.imgMatchScore);
        // Inflate the layout for this fragment
        return rootView;
    }

    public void createDialog(String name, String accountID){
        dialogBuilder = new AlertDialog.Builder(getActivity());
        final TextView textName =  new TextView(getActivity());
        textName.setTypeface(Typeface.DEFAULT_BOLD);
        textName.setTextColor(Color.YELLOW);
        textName.setGravity(Gravity.CENTER);
        textName.setText("Name: " + name);
        final TextView textID =  new TextView(getActivity());
        textID.setTypeface(Typeface.DEFAULT_BOLD);
        textID.setTextColor(Color.YELLOW);
        textID.setGravity(Gravity.CENTER);
        textID.setText("AccountID: " + accountID);
        dialogBuilder.setTitle("Player Information");
        dialogBuilder.setView(textName);
        dialogBuilder.setPositiveButton("Statistics",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
        dialogBuilder.setNegativeButton("Related Match",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = dialogBuilder.create();
        Button b1 = (Button) alertDialog.findViewById(android.R.id.button1);
        b1.setGravity(Gravity.CENTER_HORIZONTAL);
        Button b2 = (Button) alertDialog.findViewById(android.R.id.button2);
        b2.setGravity(Gravity.CENTER_HORIZONTAL);
        alertDialog.show();
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