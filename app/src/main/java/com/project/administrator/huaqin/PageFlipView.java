package com.project.administrator.huaqin;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;

import com.eschao.android.widget.pageflip.PageFlip;
import com.eschao.android.widget.pageflip.PageFlipException;

import java.util.concurrent.locks.ReentrantLock;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PageFlipView extends GLSurfaceView implements GLSurfaceView.Renderer {

    private final static String TAG = "PageFlipView";


    int duration;
    Handler handler;
    PageRender pageRender;
    PageFlip pageFlip;
    ReentrantLock drawLock;


    public PageFlipView(Context context) {
        super(context);
        init(context);
    }

    public PageFlipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }


    public void init(Context context) {


        newHandler();

        pageFlip = new PageFlip(context);
        pageFlip.setSemiPerimeterRatio(0.8f)
                .setShadowWidthOfFoldEdges(5, 60, 0.3f)
                .setShadowWidthOfFoldBase(5, 80, 0.4f);
        setEGLContextClientVersion(2);

        drawLock = new ReentrantLock();
        pageRender = new SinglePageRender(context, pageFlip, handler, Constant.PRE_PAGE_NUM);

        duration = 1000;

        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);


    }


    /**
     * Handle finger down event
     *
     * @param x finger x coordinate
     * @param y finger y coordinate
     */
    public void onFingerDown(float x, float y) {
        // if the animation is going, we should ignore this event to avoid
        // mess drawing on screen
        if (!pageFlip.isAnimating() &&
                pageFlip.getFirstPage() != null) {
            pageFlip.onFingerDown(x, y);
        }
    }

    /**
     * Handle finger moving event
     *
     * @param x finger x coordinate
     * @param y finger y coordinate
     */
    public void onFingerMove(float x, float y) {
        if (pageFlip.isAnimating()) {
            // nothing to do during animating
        } else if (pageFlip.canAnimate(x, y)) {
            // if the point is out of current page, try to start animating
            onFingerUp(x, y);
        }
        // move page by finger
        else if (pageFlip.onFingerMove(x, y)) {
            try {
                drawLock.lock();
                if (pageRender != null &&
                        pageRender.onFingerMove(x, y)) {
                    requestRender();
                }
            } finally {
                drawLock.unlock();
            }
        }
    }

    /**
     * Handle finger up event and start animating if need
     *
     * @param x finger x coordinate
     * @param y finger y coordinate
     */
    public void onFingerUp(float x, float y) {
        if (!pageFlip.isAnimating()) {
            pageFlip.onFingerUp(x, y, duration);
            try {
                drawLock.lock();
                if (pageRender != null &&
                        pageRender.onFingerUp(x, y)) {
                    requestRender();
                }
            } finally {
                drawLock.unlock();
            }
        }
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        try {
            pageFlip.onSurfaceCreated();
        } catch (PageFlipException e) {
            Log.e(TAG, "Failed to run PageFlipFlipRender:onSurfaceCreated");
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        try {
            pageFlip.onSurfaceChanged(width, height);

            // if there is the second page, create double page render when need
//            int pageNo = pageRender.getPageNo();
            if (pageFlip.getSecondPage() != null && width > height) {
                if (!(pageRender instanceof DoublePagesRender)) {
                    pageRender.release();
                    pageRender = new DoublePagesRender(getContext(),
                            pageFlip,
                            handler, 1);
                }
            }
            // if there is only one page, create single page render when need
            else if (!(pageRender instanceof SinglePageRender)) {
                pageRender.release();
                pageRender = new SinglePageRender(getContext(),
                        pageFlip,
                        handler, 1);
            }

            // let page render handle surface change
            pageRender.onSurfaceChanged(width, height);
        } catch (PageFlipException e) {
            Log.e(TAG, "Failed to run PageFlipFlipRender:onSurfaceChanged");
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        try {
            drawLock.lock();
            if (pageRender != null) {
                pageRender.onDrawFrame();
            }
        } finally {
            drawLock.unlock();
        }
    }


    private void newHandler() {
        handler = new android.os.Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case PageRender.MSG_ENDED_DRAWING_FRAME:
                        try {
                            drawLock.lock();
                            // notify page render to handle ended drawing
                            // message
                            if (pageRender != null &&
                                    pageRender.onEndedDrawing(msg.arg1)) {
                                requestRender();
                            }
                        } finally {
                            drawLock.unlock();
                        }
                        break;

                    default:
                        break;
                }
            }
        };
    }
}
