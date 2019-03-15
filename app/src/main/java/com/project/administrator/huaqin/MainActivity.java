package com.project.administrator.huaqin;


import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

import android.widget.AbsListView;
import android.widget.AdapterView;


import android.widget.GridLayout;

import android.widget.ListView;
import android.widget.Toast;


import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.ramotion.foldingcell.FoldingCell;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final int REQUESTCODE = 101;

    private FloatingActionsMenu floatingActionsMenu;

    private GridLayout gridLayout;

    private List<String> dataList;

    private List<Item> addtionaList;

    private int index = 10;

    private Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        PermisionUtil.verifyStoragePermissions(this);



        floatingActionsMenu = findViewById(R.id.floatingMenu);
        final ListView listView = findViewById(R.id.mainListView);

//        gridLayout = findViewById(R.id.gridlayout_1);
        FloatingActionButton button_1 = new FloatingActionButton(getBaseContext());
        FloatingActionButton button_2 = new FloatingActionButton(getBaseContext());
        final FloatingActionButton button_3 = new FloatingActionButton(getBaseContext());

        button_1.setTitle("");
        button_1.setIcon(R.mipmap.ic_fab_star);
        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocalReadActivity.class);
                startActivity(intent);
            }
        });

        button_2.setTitle("下载");
        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, PageFlipActivity.class);
//                startActivity(intent);
//                downFileThird();
//                new FileDownLoadUtil().downFileThird(MainActivity.this);

            }
        });

        button_3.setTitle("本地阅读");
        button_3.setIcon(R.mipmap.read_local);
        button_3.setColorNormalResId(R.color.pink_pressed);
        button_3.setColorPressedResId(R.color.pink);

        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, FileListActivity.class);
                startActivity(intent);

            }
        });

//        floatingActionsMenu.addButton(button_1);
//        floatingActionsMenu.addButton(button_2);
        floatingActionsMenu.addButton(button_3);

        final Intent intent = getIntent();

        String bookList = intent.getStringExtra("bookList");
        final Gson gson = new Gson();
        final List<Item> bookItemList = gson.fromJson(bookList, new TypeToken<List<Item>>() {}.getType());
//        Log.e("MainActivity", bookItemList.toString());
//        final ArrayList<Item> items = Item.getTestingList();

        final FoldingCellListAdapter foldingCellListAdapter = new FoldingCellListAdapter(this, bookItemList);
        listView.setAdapter(foldingCellListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((FoldingCell) view).toggle(false);
                foldingCellListAdapter.registerToggle(position);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                boolean flag = false;
                switch (scrollState){
                    case SCROLL_STATE_IDLE:
                        flag = isListViewReachBottomEdge(view);
                        break;
                }

                if (flag){
                    index = index + 30;
                    new Thread() {
                        public void run() {
                            String url = "https://selltom.mynatapp.cc/huaqin/getbooklist?index=" + index;
                            String data = HttpUtil.getBookList(url);

                            Message message = new Message();
                            message.obj = data;
                            handler.sendMessage(message);
                        }
                    }.start();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        Log.e("SplashActivity", msg.obj.toString());
                        addtionaList = gson.fromJson(msg.obj.toString(), new TypeToken<List<Item>>() {
                        }.getType());
                        bookItemList.addAll(addtionaList);
                        foldingCellListAdapter.notifyDataSetChanged();
                        break;
                    default:
                        Log.e("MainActivity", "default message");
                        break;
                }
            }
        };

    }

    public boolean isListViewReachBottomEdge(final AbsListView listView) {
        boolean result = false;
        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
            final View bottomChildView = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
            result = (listView.getHeight() >= bottomChildView.getBottom());
        };
        return result;
    }




}
