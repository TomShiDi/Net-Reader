package com.project.administrator.huaqin;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

public class PageFlipActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {


    private PageFlipView pageFlipView;

    private GestureDetector gestureDetector;

    private PopupWindow popupWindow;

    private Animation animation;

    private View popupView;

    private final static int FLIP_DISTANTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(Constant.TEXT_SIZE);
        Constant.PAGE_SIZE = PagesUtil.getPageSize(textPaint, this);
        Constant.FILE_NAME = intent.getStringExtra("fileName");
        Constant.PRE_PAGE_NUM = PagesUtil.getProgress(this);

        PagesUtil.getFileType(Constant.FILE_NAME, this);

        pageFlipView = new PageFlipView(this);

        setContentView(pageFlipView);

//        setContentView(R.layout.activity_page_flip);

//        ((RelativeLayout) findViewById(R.id.page_flip_layout)).addView(pageFlipView);

        gestureDetector = new GestureDetector(this, this);





        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            pageFlipView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_IMMERSIVE |
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        LoadBitmapTask.get(this).start();
        pageFlipView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        pageFlipView.onPause();
        LoadBitmapTask.get(this).stop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            pageFlipView.onFingerUp(event.getX(), event.getY());
            return true;
        }
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        pageFlipView.onFingerDown(e.getX(), e.getY());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (e1.getY() - e2.getY() > FLIP_DISTANTS) {
            popupWin();
        }
        pageFlipView.onFingerMove(e2.getX(), e2.getY());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        return false;
    }





    public void popupWin() {
        if (null != popupWindow) {
            popupWindow.dismiss();
        }

        popupView = View.inflate(this, R.layout.popupwindow, null);
        ((SeekBar) popupView.findViewById(R.id.seekBar_textSize)).setProgress(Constant.TEXT_SIZE);
        ((SeekBar)popupView.findViewById(R.id.seekBar_textSize)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Constant.TEXT_SIZE = progress;
                TextPaint textPaint = new TextPaint();
                textPaint.setTextSize(Constant.TEXT_SIZE);
                Constant.PAGE_SIZE = PagesUtil.getPageSize(textPaint, PageFlipActivity.this);

                ((SinglePageRender)pageFlipView.pageRender).initPageUtil();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                lighton();
            }
        });

        popupWindow.setOutsideTouchable(true);


        popupWindow.setAnimationStyle(R.style.WindowPop);
        popupWindow.showAtLocation(pageFlipView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        lightoff();

//            if (popupWindow.isShowing()) {
//                popupWindow.dismiss();
//                lighton();
//            }
    }


    /**
     * 设置手机屏幕亮度变暗
     */
    private void lightoff() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
    }

    /**
     * 设置手机屏幕亮度显示正常
     */
    private void lighton() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }
}
