package com.project.administrator.huaqin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.project.administrator.huaqin.anotherLayout.AnotherMainActivity;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MakeFilePathUtil.makeDirectory(this);


        final String[] finalBookList = new String[1];
        @SuppressLint("HandlerLeak") final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        Log.e("SplashActivity", msg.obj.toString());
                        finalBookList[0] = msg.obj.toString();
                        break;
                    default:
                        Log.e("MainActivity", "default message");
                        break;
                }
            }
        };

        new Thread() {
            public void run() {
                String url = "https://selltom.mynatapp.cc/huaqin/getbooklist?index=10";
                String data = HttpUtil.getBookList(url);

                Message message = new Message();
                message.obj = data;
                handler.sendMessage(message);
            }
        }.start();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra("bookList", finalBookList[0]);
                startActivity(intent);
                finish();
            }
        }, 4000);

    }
}
