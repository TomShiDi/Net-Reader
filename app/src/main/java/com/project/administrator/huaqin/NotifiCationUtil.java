package com.project.administrator.huaqin;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotifiCationUtil {

    private Context mContext;

    private RemoteViews remoteViews;

    private NotificationManager notificationManager;

    private NotificationCompat.Builder mBuilder;

    public NotifiCationUtil(Context mContext) {
        this.mContext = mContext;
        remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.notification_view);
        notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(mContext);
    }

    public void showNotification(String title) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext);
//        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("10", "ds", NotificationManager
                    .IMPORTANCE_DEFAULT);
            mBuilder.setChannelId("10");
            notificationManager.createNotificationChannel(channel);


//        remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.notification_view);


            mBuilder.setSmallIcon(R.mipmap.stars);
            mBuilder.setContent(remoteViews);
//        if (progress == 1) {
//            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
//        }
//        remoteViews.setImageViewResource(R.id.image, R.mipmap.timg);
//        remoteViews.setTextViewText(R.id.title, "我是标题");
//        remoteViews.setTextViewText(R.id.content, "我是内容");
//        remoteViews.setProgressBar(R.id.pBar, 10, progress, false);
//        remoteViews.setTextViewText(R.id.proNum, progress + "/10");
            remoteViews.setTextViewText(R.id.text_view_title, title);
            remoteViews.setProgressBar(R.id.progress_download, 100, 0, false);
            notificationManager.notify(10, mBuilder.build());
        }
    }

    public void updataProgress(int progress) {
        remoteViews.setProgressBar(R.id.progress_download, 100, progress, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("10", "ds", NotificationManager
                    .IMPORTANCE_DEFAULT);
            mBuilder.setChannelId("10");
            notificationManager.createNotificationChannel(channel);
            mBuilder.setSmallIcon(R.mipmap.stars);
            mBuilder.setContent(remoteViews);
            notificationManager.notify(10, mBuilder.build());
        }
    }

    public void complete() {
        remoteViews.setTextViewText(R.id.text_view_title, "下载完成");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("10", "ds", NotificationManager
                    .IMPORTANCE_DEFAULT);
            mBuilder.setChannelId("10");
            notificationManager.createNotificationChannel(channel);
            mBuilder.setSmallIcon(R.mipmap.stars);
            mBuilder.setContent(remoteViews);
            notificationManager.notify(10, mBuilder.build());
        }
    }
}
