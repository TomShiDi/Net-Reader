package com.project.administrator.huaqin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.eschao.android.widget.pageflip.Page;
import com.eschao.android.widget.pageflip.PageFlip;
import com.eschao.android.widget.pageflip.PageFlipState;

public class SinglePageRender extends PageRender {


    public SinglePageRender(Context context, PageFlip pageFlip, Handler handler,int pageNo) {
        super(context, pageFlip, handler,pageNo);
        initPageUtil();
    }

    @Override
    void onDrawFrame() {
        // 1. delete unused textures
        mPageFlip.deleteUnusedTextures();
        Page page = mPageFlip.getFirstPage();

        // 2. handle drawing command triggered from finger moving and animating
        if (mDrawCommand == DRAW_MOVING_FRAME ||
                mDrawCommand == DRAW_ANIMATING_FRAME) {
            // is forward flip
            if (mPageFlip.getFlipState() == PageFlipState.FORWARD_FLIP) {
                // check if second texture of first page is valid, if not,
                // create new one
                if (!page.isSecondTextureSet()) {
//                    drawPage(mPageNo + 1);

                    String content = pagesUtil.getNextPageData(Constant.FILE_NAME, mContext);

                    Log.e("forward pageNo: ", mPageNo + "  content:" + pagesUtil.currentPage);
                    drawPage(content);

                    page.setSecondTexture(mBitmap);
                }
            }
            // in backward flip, check first texture of first page is valid
            else if (!page.isFirstTextureSet()) {
//                drawPage(--mPageNo);
//                mPageNo--;

                String content = pagesUtil.getPrePageData(Constant.FILE_NAME, mContext);

                Log.e("backward pageNo: ", mPageNo + "  content:" + pagesUtil.currentPage);
                drawPage(content);
                page.setFirstTexture(mBitmap);
            }

            // draw frame for page flip
            mPageFlip.drawFlipFrame();
        }
        // draw stationary page without flipping
        else if (mDrawCommand == DRAW_FULL_PAGE) {
            if (!page.isFirstTextureSet()) {
                drawPage(mPageNo);
                page.setFirstTexture(mBitmap);
            }

            mPageFlip.drawPageFrame();
        }

        // 3. send message to main thread to notify drawing is ended so that
        // we can continue to calculate next animation frame if need.
        // Remember: the drawing operation is always in GL thread instead of
        // main thread
        Message msg = Message.obtain();
        msg.what = MSG_ENDED_DRAWING_FRAME;
        msg.arg1 = mDrawCommand;
        mHandler.sendMessage(msg);
    }

    @Override
    void onSurfaceChanged(int width, int height) {
        // recycle bitmap resources if need
        if (mBackgroundBitmap != null) {
            mBackgroundBitmap.recycle();
        }

        if (mBitmap != null) {
            mBitmap.recycle();
        }

        // create bitmap and canvas for page
        //mBackgroundBitmap = background;
        Page page = mPageFlip.getFirstPage();
        mBitmap = Bitmap.createBitmap((int)page.width(), (int)page.height(),
                Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);
        LoadBitmapTask.get(mContext).set(width, height, 1);
    }

    @Override
    boolean onEndedDrawing(int what) {
        if (what == DRAW_ANIMATING_FRAME) {
            boolean isAnimating = mPageFlip.animating();
            // continue animating
            if (isAnimating) {
                mDrawCommand = DRAW_ANIMATING_FRAME;
                return true;
            }
            // animation is finished
            else {
                final PageFlipState state = mPageFlip.getFlipState();
                // update page number for backward flip
                if (state == PageFlipState.END_WITH_BACKWARD) {
                    // don't do anything on page number since mPageNo is always
                    // represents the FIRST_TEXTURE no;
                    mPageNo--;
                    PagesUtil.saveProgress(mContext, pagesUtil.currentPage);
                }
                // update page number and switch textures for forward flip
                else if (state == PageFlipState.END_WITH_FORWARD) {
                    mPageFlip.getFirstPage().setFirstTextureWithSecond();
                    mPageNo++;
                    pagesUtil.currentPage++;
                    PagesUtil.saveProgress(mContext, pagesUtil.currentPage);
                }
//                else {
//                    pagesUtil.currentPage = pagesUtil.prePage;
//                }

                mDrawCommand = DRAW_FULL_PAGE;
                return true;
            }
        }
        return false;
    }



    /**
     * Draw page content
     *
     * @param number page number
     */
    private void drawPage(int number) {
        final int width = mCanvas.getWidth();
        final int height = mCanvas.getHeight();
        Paint p = new Paint();
        p.setFilterBitmap(true);

        // 1. draw background bitmap
        Bitmap background = LoadBitmapTask.get(mContext).getBitmap();
        Rect rect = new Rect(0, 0, width, height);
        mCanvas.drawBitmap(background, null, rect, p);
        background.recycle();
        background = null;

        // 2. draw page content
        int fontSize = calcFontSize(70);
        p.setColor(Color.BLACK);
        p.setStrokeWidth(1);
        p.setAntiAlias(true);
        p.setShadowLayer(5.0f, 8.0f, 8.0f, Color.BLACK);
        p.setTextSize(fontSize);
        String text = String.valueOf(mPageNo);
        float textWidth = p.measureText(text);
        float y = height - p.getTextSize() - 20;
        mCanvas.drawText(text, (width - textWidth) / 2, y, p);

        if (number <= 1) {
            String firstPage = "The First Page";
            p.setTextSize(calcFontSize(16));
            float w = p.measureText(firstPage);
            float h = p.getTextSize();
            mCanvas.drawText(firstPage, (width - w) / 2, y + 5 + h, p);
        }
        else if (number >= MAX_PAGES) {
            String lastPage = "The Last Page";
            p.setTextSize(calcFontSize(16));
            float w = p.measureText(lastPage);
            float h = p.getTextSize();
            mCanvas.drawText(lastPage, (width - w) / 2, y + 5 + h, p);
        }
    }

    /**
     * draw page content
     * @param content
     */

    private void drawPage(String content) {
        final int width = mCanvas.getWidth();
        final int height = mCanvas.getHeight();
        Paint p = new Paint();
        p.setFilterBitmap(true);
        Log.e("SingleRender", content);
        // 1. draw background bitmap
        Bitmap background = LoadBitmapTask.get(mContext).createPageBgBipmap();
        Rect rect = new Rect(0, 0, width, height);
        mCanvas.drawBitmap(background, null, rect, p);
        background.recycle();
        background = null;

        // 2. draw page content

        Bitmap textBitmap = PagesUtil.createPageBitMap(content, width, height, Constant.TEXT_SIZE, Constant.TEXT_COLOR);
        mCanvas.drawBitmap(textBitmap, null, new Rect(0, 0, textBitmap.getWidth() - 30, textBitmap.getHeight()-40), p);
    }

    /**
     * set Page Config
     */
    public void initPageUtil(){
        pagesUtil = new PagesUtil(Constant.PAGE_SIZE);
    }

    /**
     * If page can flip forward
     *
     * @return true if it can flip forward
     */
    @Override
    public boolean canFlipForward() {
        return (mPageNo < MAX_PAGES);
    }

    /**
     * If page can flip backward
     *
     * @return true if it can flip backward
     */
    @Override
    public boolean canFlipBackward() {
        if (mPageNo > 1) {
            mPageFlip.getFirstPage().setSecondTextureWithFirst();
            return true;
        }
        else {
            return false;
        }
    }
}
