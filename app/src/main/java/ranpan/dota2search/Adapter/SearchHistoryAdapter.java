package ranpan.dota2search.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.util.List;
import java.util.Map;

import Convert.CImage;
import ranpan.dota2search.LoadingActivity;
import ranpan.dota2search.LoginActivity;
import ranpan.dota2search.R;

/**
 * Created by panpanr on 7/30/2015.
 */
public class SearchHistoryAdapter extends BaseAdapter {
    private Context ctx;
    private List<String> playerList;

    public SearchHistoryAdapter(Context ctx, List<String> playerList) {
        this.ctx = ctx;
        this.playerList = playerList;
    }

        @Override
        public int getCount() {
            return playerList.size();
//            return dataObjects.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertview, ViewGroup parent) {
            View retval = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_loginrow, null);
            ImageButton imgButton = (ImageButton) retval.findViewById(R.id.imgLogin);
            File imgFile = new File(ctx.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "Player_" + playerList.get(position) + ".jpg");
            try{
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgButton.setVisibility(View.VISIBLE);
                myBitmap = CImage.getCircularBitmapWithWhiteBorder(myBitmap, 5);
                imgButton.setImageBitmap(myBitmap);
            }
            catch (Exception e){
                imgButton.setVisibility(View.GONE);
            }

            return retval;
        }
}
