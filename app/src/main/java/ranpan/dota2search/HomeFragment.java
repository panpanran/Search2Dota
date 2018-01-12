package ranpan.dota2search;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import Convert.CImage;
import Data.MatchData;
import Data.UserData;

/**
 * Created by panpanr on 7/11/2015.
 */
public class HomeFragment extends Fragment {

    List<String> playerList;
    Map<String,Float> mapScore;
    public Context context;
    public static Boolean loginFlag =false;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        new HomeTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
//        Runnable r = new MyThread(rootView);
//        new Thread(r).start();
        return rootView;
    }

//    public class MyThread implements Runnable {
//        private  View V;
//        public MyThread(View thisView) {
//            // store parameter for later user
//            V = thisView;
//        }
//
//        public void run() {
//            getActivity().runOnUiThread(new Runnable() //run on ui thread
//            {
//                @Override
//                public void run() {
//                    imgMe = (ImageView) V.findViewById(R.id.imgHome);
//                    Bitmap bmp1 = Bitmap.createBitmap(imgMe.getWidth(), imgMe.getHeight(), Bitmap.Config.ARGB_8888);
//                    Point p1 = new Point(imgMe.getWidth()/2,imgMe.getHeight()/2);
//                    CImage.DrawHexagonal(bmp1, imgMe, Color.YELLOW);
//                    CImage.FloodFill(bmp1, p1, bmp1.getPixel(p1.x, p1.y), Color.YELLOW);
//                    Canvas canvas = new Canvas(bmp1);
//                    Bitmap bmp2 = Bitmap.createBitmap(imgMe.getWidth(), imgMe.getHeight(), Bitmap.Config.ARGB_8888);
//                    CImage.DrawScoreHexagonal(bmp2, imgMe, mapScore, Color.BLUE);
//                    canvas.drawBitmap(bmp2, 0, 0, null);
//                    Point p2 = new Point(imgMe.getWidth()/2+1,imgMe.getHeight()/2);
//                    CImage.FloodFill(bmp1, p2, bmp1.getPixel(p1.x, p1.y), Color.BLUE);
//                    imgMe.setImageBitmap(bmp1);
//                }
//            });
//        }
//    }

    public class HomeTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Data
            if (getView() != null) {
                // update views
            try {
                    ImageView imgMe = (ImageView) getView().findViewById(R.id.imgHome);

                File imgFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Score_" + LoginActivity.accountID + ".png");
                if(!loginFlag) {
                    try {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        myBitmap = CImage.cropCricle(myBitmap);
                        imgMe.setImageBitmap(myBitmap);
                        imgMe.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }else{
                    Bitmap bmp1 = Bitmap.createBitmap(imgMe.getWidth(), imgMe.getHeight(), Bitmap.Config.ARGB_8888);
                    Point p1 = new Point(imgMe.getWidth() / 2, imgMe.getHeight() / 2);
                    CImage.DrawHexagonal(bmp1,imgMe.getHeight(), imgMe.getWidth(), Color.YELLOW);
                    CImage.FloodFill(bmp1, p1, bmp1.getPixel(p1.x, p1.y), Color.YELLOW);
                    Canvas canvas = new Canvas(bmp1);
                    Bitmap bmp2 = Bitmap.createBitmap(imgMe.getWidth(), imgMe.getHeight(), Bitmap.Config.ARGB_8888);
                    CImage.DrawScoreHexagonal(bmp2, imgMe.getHeight(), imgMe.getWidth(), mapScore, Color.BLUE);
                    canvas.drawBitmap(bmp2, 0, 0, null);
                    Point p2 = new Point(imgMe.getWidth() / 2, imgMe.getHeight() / 2);
                    CImage.FloodFill(bmp1, p2, bmp1.getPixel(p2.x, p2.y), Color.BLUE);
                    imgMe.setImageBitmap(bmp1);
                    //download Score Image
                    FileOutputStream fOut = new FileOutputStream(imgFile);
                    bmp1.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    loginFlag = false;
                }

                String[] strList = playerList.toArray(new String[playerList.size()]);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, strList);
                ListView listView = (ListView)getView().findViewById(R.id.listHome);
                listView.setAdapter(adapter);
            } catch (Exception e) {
                e.getMessage();
//                startActivity(new Intent(context, LoginActivity.class));
            }
            }else{

            }
        }

        @Override
        protected String doInBackground(String... url) {
            try {
                playerList = UserData.getInfo(context);
                mapScore = MatchData.getMyScore(context,LoginActivity.accountID);
            } catch (Exception e) {
//                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
            return null;
        }
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

