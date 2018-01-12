package Convert;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

import Database.Table.MatchTable;
import Database.Table.PlayerTable;

/**
 * Created by panpanr on 7/10/2015.
 */
public class CImage {
    public static Bitmap DrawLine(Bitmap bmp,float x, float y, float xend, float yend, int color, int border) {

        Canvas c = new Canvas(bmp);
        Paint p = new Paint();
        p.setColor(color);
        p.setStrokeWidth(border);
        c.drawLine(x, y, xend, yend, p);
        return  bmp;
    }

    public static Bitmap DrawHexagonal(Bitmap bmp,float imgHeight, float imgWidth, int color) {
        float imgA = imgWidth > imgHeight ? imgHeight : imgWidth;
        int border = 5;
        CImage.DrawLine(bmp,imgWidth/2 - imgA/4, imgHeight/2 - (17 * imgA /40), imgWidth/2 + imgA/4, imgHeight/2 - (17 * imgA /40), color,border);
        CImage.DrawLine(bmp,imgWidth / 2 - imgA / 4, imgHeight / 2 - (17 * imgA / 40), imgWidth / 2 - imgA / 2, imgHeight / 2, color,border);
        CImage.DrawLine(bmp,imgWidth / 2 - imgA / 2, imgHeight / 2, imgWidth / 2 - imgA / 4, imgHeight / 2 + (17 * imgA / 40), color,border);
        CImage.DrawLine(bmp,imgWidth / 2 - imgA / 4, imgHeight / 2 + (17 * imgA / 40), imgWidth / 2 + imgA / 4, imgHeight / 2 + (17 * imgA / 40), color,border);
        CImage.DrawLine(bmp,imgWidth / 2 + imgA / 4, imgHeight / 2 + (17 * imgA / 40), imgWidth / 2 + imgA / 2, imgHeight / 2, color,border);
        CImage.DrawLine(bmp,imgWidth / 2 + imgA / 2, imgHeight / 2, imgWidth / 2 + imgA / 4, imgHeight / 2 - (17 * imgA / 40), color,border);
        return bmp;
    }

    public static Bitmap DrawScoreHexagonal(Bitmap bmp,float imgHeight, float imgWidth, Map<String,Float> mapScore, int color) {
        float imgA = imgWidth > imgHeight ? imgHeight : imgWidth;

        float goldScore = (mapScore.get("type")+1 - mapScore.get(PlayerTable.Key_gold))/mapScore.get("type");
        float heroScore = (mapScore.get("type")+1 - mapScore.get(PlayerTable.Key_herodamage))/mapScore.get("type");
        float killScore = (mapScore.get("type")+1 - mapScore.get(PlayerTable.Key_kills))/mapScore.get("type");
        float pushScore = (mapScore.get("type")+1 - mapScore.get(PlayerTable.Key_towerdamage))/mapScore.get("type");
        float levelScore = (mapScore.get("type")+1 - mapScore.get(PlayerTable.Key_level))/mapScore.get("type");
        float escapeScore = mapScore.get(PlayerTable.Key_deaths)/mapScore.get("type");
        int border = 2;
        CImage.DrawLine(bmp,imgWidth/2 - goldScore *imgA/4, imgHeight/2 - goldScore * (17 * imgA /40), imgWidth/2 + heroScore*imgA/4, imgHeight/2 - heroScore*(17 * imgA /40), color,border);
        CImage.DrawLine(bmp,imgWidth/2 - goldScore * imgA/4, imgHeight/2 - goldScore*(17 * imgA /40), imgWidth/2 - escapeScore*imgA/2, imgHeight/2, color,border);
        CImage.DrawLine(bmp,imgWidth/2 - escapeScore*imgA/2, imgHeight/2, imgWidth/2 - levelScore*imgA/4, imgHeight/2 + levelScore*(17 * imgA /40), color,border);
        CImage.DrawLine(bmp,imgWidth/2 - levelScore*imgA/4, imgHeight/2 + levelScore*(17 * imgA /40), imgWidth/2 + pushScore*imgA/4, imgHeight/2 + pushScore*(17 * imgA /40), color,border);
        CImage.DrawLine(bmp,imgWidth/2 + pushScore*imgA/4, imgHeight/2 + pushScore*(17 * imgA /40), imgWidth/2 + killScore*imgA/2, imgHeight/2, color,border);
        CImage.DrawLine(bmp,imgWidth/2 + killScore*imgA/2, imgHeight/2, imgWidth/2 + heroScore*imgA/4, imgHeight/2 - heroScore*(17 * imgA /40), color,border);
        return bmp;
    }

    public static Bitmap getCircularBitmapWithWhiteBorder(Bitmap bitmap,int borderWidth) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }

        final int width = bitmap.getWidth() + borderWidth;
        final int height = bitmap.getHeight() + borderWidth;

        Bitmap canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Canvas canvas = new Canvas(canvasBitmap);
        float radius = width > height ? ((float) height) / 2f : ((float) width) / 2f;
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(borderWidth);
        canvas.drawCircle(width / 2, height / 2, radius - borderWidth / 2, paint);
        return canvasBitmap;
    }

    public static Bitmap cropCricle(Bitmap bm){

        int width = bm.getWidth();
        int height = bm.getHeight();

        Bitmap cropped_bitmap;

    /* Crop the bitmap so it'll display well as a circle. */
        if (width > height) {
            cropped_bitmap = Bitmap.createBitmap(bm,
                    (width / 2) - (height / 2), 0, height, height);
        } else {
            cropped_bitmap = Bitmap.createBitmap(bm, 0, (height / 2)
                    - (width / 2), width, width);
        }

        BitmapShader shader = new BitmapShader(cropped_bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        height = cropped_bitmap.getHeight();
        width = cropped_bitmap.getWidth();

        Bitmap mCanvasBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(mCanvasBitmap);
        canvas.drawCircle(width/2, height/2, width/2, paint);

        return mCanvasBitmap;
    }

    public static void FloodFill(Bitmap bmp, Point pt, int targetColor, int replacementColor){
        Queue<Point> q = new LinkedList<Point>();
        q.add(pt);
        while (q.size() > 0) {
            Point n = q.poll();
            if (bmp.getPixel(n.x, n.y) != targetColor)
                continue;

            Point w = n, e = new Point(n.x + 1, n.y);
            while ((w.x > 0) && (bmp.getPixel(w.x, w.y) == targetColor)) {
                bmp.setPixel(w.x, w.y, replacementColor);
                if ((w.y > 0) && (bmp.getPixel(w.x, w.y - 1) == targetColor))
                    q.add(new Point(w.x, w.y - 1));
                if ((w.y < bmp.getHeight() - 1)
                        && (bmp.getPixel(w.x, w.y + 1) == targetColor))
                    q.add(new Point(w.x, w.y + 1));
                w.x--;
            }
            while ((e.x < bmp.getWidth() - 1)
                    && (bmp.getPixel(e.x, e.y) == targetColor)) {
                bmp.setPixel(e.x, e.y, replacementColor);

                if ((e.y > 0) && (bmp.getPixel(e.x, e.y - 1) == targetColor))
                    q.add(new Point(e.x, e.y - 1));
                if ((e.y < bmp.getHeight() - 1)
                        && (bmp.getPixel(e.x, e.y + 1) == targetColor))
                    q.add(new Point(e.x, e.y + 1));
                e.x++;
            }
        }}
}
