package com.project.administrator.huaqin;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

    public static String getBookList(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().get().url(url).method("GET",null).build();
        String reponseData = null;
        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            if (response.code() == 200) {
                reponseData = response.body().string();
            }else {
//                throw new Exception("网络错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reponseData;
    }
}
