package ranpan.dota2search.CustomView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import Convert.CImage;
import ranpan.dota2search.R;

/**
 * Created by panpanr on 8/1/2015.
 */
public class CustomProgressDialog extends Dialog implements View.OnClickListener {
    Context mContext;
    TextView mTitle = null;
    TextView mMessage = null;
    ImageView mImage = null;
    View v = null;
    ProgressBar progressBar;

    public CustomProgressDialog(Context context) {
        super(context);
        mContext = context;
        /** 'Window.FEATURE_NO_TITLE' - Used to hide the mTitle */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /** Design the dialog in main.xml file */
        setContentView(R.layout.progress_loading);
        v = getWindow().getDecorView();
        v.setBackgroundResource(android.R.color.transparent);
        mTitle = (TextView) findViewById(R.id.dialogTitle);
        mMessage = (TextView) findViewById(R.id.dialogMessage);
        progressBar = (ProgressBar) findViewById(R.id.progressLoading);
        mImage = (ImageView) findViewById(R.id.imgLoadingLogo);
        Bitmap myBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.loadinglogo);
        myBitmap = CImage.getCircularBitmapWithWhiteBorder(myBitmap, 5);
        mImage.setImageBitmap(myBitmap);
        mImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTitle.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        mTitle.setText(mContext.getResources().getString(titleId));
    }

    /**
     * Set the message text for this dialog's window.
     *
     * @param message - The new message to display in the title.
     */
    public void setMessage(CharSequence message) {
        mMessage.setText(message);
        mMessage.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    /**
     * Set the message ID
     *
     * @param messageId
     */
    public void setMessage(int messageId) {
        mMessage.setText(mContext.getResources().getString(messageId));
        mMessage.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }
}