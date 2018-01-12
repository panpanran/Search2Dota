package DotaFile;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import Convert.CImage;

/**
 * Created by panpanr on 8/1/2015.
 */
public class ImageFile {
    public static void SaveFileByBitmap(Bitmap myBitmap, Context context , File file){
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
