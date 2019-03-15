package com.project.administrator.huaqin;

import android.content.Context;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

public class FileDownLoadUtil {


    public void downFileThird(final Context context, final NotifiCationUtil notifiCationUtil, String url, final CircularProgressButton circularProgressButton){
        FileDownloader.setup(context);

        BaseDownloadTask baseDownloadTask = FileDownloader.getImpl().create(url)
                .setPath(Environment.getExternalStorageDirectory().getAbsolutePath() + context.getResources().getString(R.string.filePath) + "/" + url.substring(url.lastIndexOf("/")))
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                        notifiCationUtil.updataProgress(100 * soFarBytes / totalBytes);
                        circularProgressButton.setProgress(100 * soFarBytes / totalBytes);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        circularProgressButton.setProgress(100);
                        notifiCationUtil.complete();
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                });
        baseDownloadTask.start();
    }
}
