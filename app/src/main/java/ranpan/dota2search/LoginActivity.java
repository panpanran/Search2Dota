package ranpan.dota2search;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.purplebrain.adbuddiz.sdk.AdBuddiz;

import java.util.ArrayList;
import java.util.List;

import Database.BusinessLogicLayer.BLLSearch;
import Database.Table.SearchTable;
import ranpan.dota2search.Adapter.SearchHistoryAdapter;
import ranpan.dota2search.CustomView.CustomProgressDialog;
import ranpan.dota2search.CustomView.HorizontalListView;


public class LoginActivity extends Activity {

    public static String accountID = "";
    private List<String> AccountIDRecent = new ArrayList<>();
    SearchHistoryAdapter searchHistoryAdapter;
    public Context context;
    CustomProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        HorizontalListView historyList = (HorizontalListView) findViewById(R.id.historyList);
        searchHistoryAdapter = new SearchHistoryAdapter(context, AccountIDRecent);
        historyList.setAdapter(searchHistoryAdapter);
        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v,
                                    int position, long id) {
                LoginActivity.accountID = AccountIDRecent.get(position);
                context.startActivity(new Intent(context, LoadingActivity.class));
            }
        });

//        ProgressSetting();

        ShowRecent();
        Button button = (Button) findViewById(R.id.btnSignin);
        button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        EditText editText = (EditText) findViewById(R.id.txtAccountID);
                        accountID = editText.getText().toString();
                        startActivity(new Intent(LoginActivity.this, LoadingActivity.class));
                    }
                }
        );
    }

    public void ProgressSetting(){
        progressDialog = new CustomProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Message");
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(progressDialog.getWindow().getAttributes());
        lp.width = 500;
        lp.height = 500;
        progressDialog.show();
        progressDialog.getWindow().setAttributes(lp);
    }

    public void ShowRecent(){
        BLLSearch bllSearch = new BLLSearch(this);
        List<SearchTable> searchList;
        try {
            searchList = bllSearch.SelectRecent();
            for(int i = 0;i<searchList.size();i++) {
                AccountIDRecent.add(String.valueOf(Long.parseLong(searchList.get(i).contentValues.get(SearchTable.KEY_steamid).toString()) - 76561197960265728L));
            }
        }catch (Exception e){
            e.getMessage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }
}
