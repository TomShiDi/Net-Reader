package com.project.administrator.huaqin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.RelativeLayout;

public class CreatePage {

    public static Bitmap createBitMap(String content, int width, int height, int textSize, int textColor) {

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        TextPaint textPaint = new TextPaint();
//        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/");
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);

        StaticLayout staticLayout = new StaticLayout(content, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);

        staticLayout.draw(canvas);

        return bitmap;
    }

}
