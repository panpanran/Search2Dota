package ranpan.dota2search;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Data.FriendData;
import ranpan.dota2search.Adapter.FriendAdapter;

/**
 * Created by panpanr on 7/10/2015.
 */
public class FriendFragment extends Fragment {

    HashMap<String, List<String>> friendList = new HashMap<String, List<String>>();
    List<String> playerList;
    ExpandableListView Exp_list;
    FriendAdapter adapter;
    public Context context;

    public FriendFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public class FriendTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                playerList = new ArrayList<String>(friendList.keySet());
                adapter = new FriendAdapter(context, friendList, playerList);
                Exp_list = (ExpandableListView) getView().findViewById(R.id.friendExpandList);
                Exp_list.setAdapter(adapter);
                Exp_list.setGroupIndicator(null);

            } catch (Exception e) {
                e.getMessage();
            }
        }

        @Override
        protected String doInBackground(String... url) {
            try {
                friendList = FriendData.getAll(context);
            } catch (Exception e) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friend, container, false);
        context = getActivity();
        new FriendTask().execute();
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