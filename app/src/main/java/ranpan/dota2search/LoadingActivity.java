package ranpan.dota2search;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.purplebrain.adbuddiz.sdk.AdBuddiz;

import JSON2Data.JSON2Download;


public class LoadingActivity extends Activity {

    private volatile boolean running = true;
    public static ProgressDialog progressBar;
    private Handler progressBarHandler = new Handler();
    private int progressBarStatus = 0;
    public static double fileSize = 0;
    public static double fileSizeCopy = 0;
    Thread threadLoading;
    public static String loadingTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        startProgressDialog(this.findViewById(android.R.id.content));
        DownloadLoading.execute();
        AdBuddiz.setPublisherKey("40926306-d2e7-4d2e-b55f-9c09488380a4");
        AdBuddiz.cacheAds(this); // this = current Activity
        AdBuddiz.showAd(this);
    }

    loadingProcess DownloadLoading = new loadingProcess();

    public class loadingProcess extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
        }

        @Override
        protected String doInBackground(String... url) {
            while (!DownloadLoading.isCancelled() && running) {
                try {
                    Thread.sleep(1000);
                    DownloadImage();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    public void DownloadImage() {
        try {
            JSON2Download json2Download = new JSON2Download(this, LoginActivity.accountID);
            json2Download.DownloadHero(this);
            json2Download.DownloadItem(this);
            json2Download.DownloadFriend(this, LoginActivity.accountID);
            json2Download.DownloadPlayer(this, LoginActivity.accountID);
            json2Download.DownloadMatch(this, LoginActivity.accountID);
            HomeFragment.loginFlag = true;
            running = false;
        } catch (Exception e) {
            DownloadLoading.cancel(true);
            running = false;
            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
            e.getMessage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loading, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DownloadLoading.cancel(true);
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void startProgressDialog(View V) {
        // prepare for a progress bar dialog
        try {
            progressBarStatus = 0;
            fileSize = 0;
            fileSizeCopy = 0;
            // Get the Drawable custom_progressbar
//            Drawable customDrawable ;
//            if(android.os.Build.VERSION.SDK_INT >= 21){
//                customDrawable = this.getDrawable(R.xml.loadingprogressbar);
//            } else {
//                customDrawable = getResources().getDrawable(R.xml.loadingprogressbar);
//            }

            progressBar = new ProgressDialog(V.getContext());
            progressBar.setCancelable(false);
            progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            // set the drawable as progress drawavle
            progressBar.setMessage("Downloading file...");
//            progressBar.setProgressDrawable(customDrawable);
            progressBar.show();

            //reset progress bar status
            progressBarStatus = 0;
            threadLoading = new Thread(new Runnable() {
                public void run() {
                    while (progressBarStatus < 100) {
                        // process some tasks
                        progressBarStatus = fileDownloadStatus();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // Update the progress bar
                        progressBarHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressBarStatus);
                            }
                        });
                    }

                    // when, file is downloaded 100%,
                    if (progressBarStatus >= 100) {
                        // close the progress bar dialog
                        progressBar.dismiss();
                    }
                }
            });
            threadLoading.start();
        }catch (Exception e){
            DownloadLoading.cancel(true);
            running = false;
            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
            e.getMessage();
        }
    }

    public int fileDownloadStatus() {
        while (1 - (fileSize / fileSizeCopy) != 1) {
            return (int) ((1 - (fileSize / fileSizeCopy)) * 100);
        }
        return 100;
    }
}
