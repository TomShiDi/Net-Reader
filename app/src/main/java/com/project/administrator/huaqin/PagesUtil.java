package com.project.administrator.huaqin;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PagesUtil {
    int currentPage = 1;

    int prePage = 1;


    private int pageSize;

    boolean isReadEnd = false;

    public PagesUtil(int pageSize) {
        this.pageSize = pageSize;
        this.currentPage = Constant.PRE_PAGE_NUM;
    }

    public static void getFileType(String fileName, Context context) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + context.getString(R.string.filePath) + "/" + fileName);
        String fileType = EncodingDetect.detect(file);
        Constant.FILE_TYPE = fileType;
    }


    public long getPageNum(String fileName, Context context) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + context.getString(R.string.filePath) + "/" + fileName);
        long fileLength = 0;
        if (file.exists()) {
            fileLength = file.length();
        }
        return fileLength;
    }

    public String getNextPageData(String fileName, Context context) {

        FileInputStream fileInputStream = null;
        FileReader fileReader = null;
        char[] pageData = new char[pageSize + 1];
        String data = "";
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + context.getString(R.string.filePath);

        try {
            fileReader = new FileReader(filePath + "/" + fileName);


            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath + "/" + fileName)), Constant.FILE_TYPE));
            bufferedReader.skip((currentPage - 1) * pageSize);
            if (bufferedReader.read(pageData) == -1) {
                isReadEnd = true;
//                    PageRender.MAX_PAGES = currentPage + 1;
            }
            Constant.SKIP_SIZE = new String(pageData).split("\n").length - 1;
//            data = bufferedReader.readLine();
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.prePage = currentPage;

        return new String(pageData);
    }

    public String getPrePageData(String fileName, Context context) {

        char[] pageData = new char[pageSize + 1];

        if (currentPage >= 3) {

            if (isReadEnd){
                this.isReadEnd = !(this.isReadEnd);
            }
            this.prePage = currentPage;
            this.currentPage = this.currentPage - 2;

            FileInputStream fileInputStream = null;
            FileReader fileReader = null;

            String data = "";
            String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + context.getString(R.string.filePath);
            try {
                fileReader = new FileReader(filePath + "/" + fileName);

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath + "/" + fileName)), Constant.FILE_TYPE));
//                BufferedReader bufferedReader = new BufferedReader(fileReader);
                bufferedReader.skip(((currentPage - 1) * pageSize));
                bufferedReader.read(pageData);
//            bufferedReader.reset();
//            data = bufferedReader.readLine();
                fileReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.currentPage++;
        }


        return new String(pageData);
    }


    public static Bitmap createPageBitMap(String content, int width, int height, int textSize, int textColor) {

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        TextPaint textPaint = new TextPaint();
//        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/");
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(textSize);

        StaticLayout staticLayout = new StaticLayout(content, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);

        int height_1 = staticLayout.getHeight();
        int width_1 = staticLayout.getWidth();
        staticLayout.getWidth();
        staticLayout.draw(canvas);

        return bitmap;
    }

    public static int getPageSize(TextPaint textPaint,Context context) {
        Rect bound = new Rect();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        textPaint.getTextBounds("稽", 0, 1, bound);
        int width = bound.width();
        int height = bound.height();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels - 17*height;
        int pageSize = (screenHeight / height) * (screenWidth / width);

        return pageSize;
    }

    public static void saveProgress(Context context,int progress){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constant.FILE_NAME, progress);
        editor.commit();
    }

    public static int getProgress(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constant.SHARED_PREFERENCE_NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getInt(Constant.FILE_NAME, 1);
    }
}
