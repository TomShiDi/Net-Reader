package com.project.administrator.huaqin.anotherLayout;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.administrator.huaqin.FoldingCellListAdapter;
import com.project.administrator.huaqin.Item;
import com.project.administrator.huaqin.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

public class AnotherMainActivity extends AppCompatActivity {

    private ViewPager main_viewpager;
    private View main_button1,main_button2,main_button3;
    private ArrayList<View> main_pageview = new ArrayList<View>();
    private int currIndex = 0;// 当前页编号
    ////////////////////////////////////////recommend页面控件/////////////////////////////////////////////////////
    private ViewPager recommend_viewpager;
    private View recommend_button1,recommend_button2,recommend_button3,recommend_button4;
    private ArrayList<View> recommend_pageview = new ArrayList<View>();
    private int currIndex1 = 0;// 当前页编号

    private RelativeLayout recommend_bluestrip;//分隔标签和界面的分界布局，把蓝条加入这个布局就能达到想要的效果
    private TextView view;//因为蓝条的宽度需要通过代码获取屏幕宽度来计算，所以蓝条在Java代码中绘制,蓝条使用一个TextView绘制，其他View也可以
    private int width;//蓝条的宽度，或者说屏幕宽度n等分的长度

    private View recommend_imageView1;
    //--------------------------recommend_custom
    private ViewPager custom_viewpager;
    private View custom_button_1,custom_button_2;
    private ArrayList<View> custom_pageview = new ArrayList<View>();
    private int currIndex2 = 0;// 当前页编号

    ////////////////////////////////////////book页面控件/////////////////////////////////////////////////////
    private View note_button1,note_button2,note_button3;
    private ListView bookListView;
    ////////////////////////////////////////my页面控件/////////////////////////////////////////////////////
    private View my_button1,my_button2,my_button3,my_button4,my_button5,my_button6;
    private View my_button7,my_button8,my_button9,my_button10,my_button11,my_button12;
    private View my_button13,my_button14,my_button15;
////////////////////////////////////////main页面控件/////////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_main);

        main_viewpager = (ViewPager) findViewById(R.id.main_viewpager);
        //查找布局文件用LayoutInflater.inflate
        LayoutInflater inflater = getLayoutInflater();
        View activity_recommend = inflater.inflate(R.layout.activity_recommend, null);
        View activity_book = inflater.inflate(R.layout.activity_book, null);
        View activity_my = inflater.inflate(R.layout.activity_my, null);
        bookListView = activity_book.findViewById(R.id.mainListView);
//        final ArrayList<Item> items = Item.getTestingList();

//        final FoldingCellListAdapter foldingCellListAdapter = new FoldingCellListAdapter(this, items);
//        bookListView.setAdapter(foldingCellListAdapter);
//        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ((FoldingCell) view).toggle(false);
//                foldingCellListAdapter.registerToggle(position);
//
//            }
//        });

        main_button1 = findViewById(R.id.main_button1);
        main_button2 = findViewById(R.id.main_button2);
        main_button3 = findViewById(R.id.main_button3);
        main_button1.setOnClickListener(new MyBtnListenermain_button1());
        main_button2.setOnClickListener(new MyBtnListenermain_button2());
        main_button3.setOnClickListener(new MyBtnListenermain_button3());
        //添加想要切换的界面
        main_pageview.add(activity_recommend);
        main_pageview.add(activity_book);
        main_pageview.add(activity_my);

        //数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter()
        {
            @Override
            //获取当前窗体界面数
            public int getCount()
            {
                return main_pageview.size();
            }
            @Override
            //判断是否由对象生成界面
            public boolean isViewFromObject(View arg0, Object arg1)
            {
                return arg0==arg1;
            }
            //使从ViewGroup中移出当前View
            public void destroyItem(View arg0, int arg1, Object arg2)
            {
                ((ViewPager) arg0).removeView(main_pageview.get(arg1));
            }

            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            public Object instantiateItem(View arg0, int arg1)
            {
                ((ViewPager)arg0).addView(main_pageview.get(arg1));
                return main_pageview.get(arg1);
            }
        };
        //绑定适配器
        main_viewpager.setAdapter(mPagerAdapter);
        //设置viewPager的初始界面为第一个界面
        main_viewpager.setCurrentItem(0);
        //添加切换界面的监听器
        main_viewpager.setOnPageChangeListener(new MyOnPageChangeListener());

        //为了获取屏幕宽度，新建一个DisplayMetrics对象
        DisplayMetrics displayMetrics = new DisplayMetrics();
        //将当前窗口的一些信息放在DisplayMetrics类中
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //得到屏幕的宽度
        int screenW = displayMetrics.widthPixels;

////////////////////////////////////////recommend页面控件/////////////////////////////////////////////////////
        recommend_viewpager = (ViewPager) activity_recommend.findViewById(R.id.recommend_viewpager);
        //查找布局文件用LayoutInflater.inflate1
        LayoutInflater inflater1 = getLayoutInflater();
        View recommend_custom = inflater1.inflate(R.layout.recommend_custom, null);
        View recommend_illusion = inflater1.inflate(R.layout.recommend_illusion, null);
        View recommend_art = inflater1.inflate(R.layout.recommend_art, null);
        View recommend_other = inflater1.inflate(R.layout.recommend_others, null);
        recommend_button1 = activity_recommend.findViewById(R.id.recommend_button1);
        recommend_button2 = activity_recommend.findViewById(R.id.recommend_button2);
        recommend_button3 = activity_recommend.findViewById(R.id.recommend_button3);
        recommend_button4 = activity_recommend.findViewById(R.id.recommend_button4);

        recommend_bluestrip = (RelativeLayout)activity_recommend.findViewById(R.id.recommend_bluestrip);//紫条


        //添加想要切换的界面
        recommend_pageview.add(recommend_custom);
        recommend_pageview.add(recommend_illusion);
        recommend_pageview.add(recommend_art);
        recommend_pageview.add(recommend_other);

        view = new TextView(this);
        //获取屏幕宽度的方法
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels/recommend_pageview.size();//由于我这里只有3个界面，所以我只要设置蓝条宽度为屏幕宽/3(metrics.widthPixels/3)
        view.setWidth(width);
        view.setHeight(8);//多次尝试才设置成正好的高度，shape文件中设置和布局文件相同的高度(10dp)却要大一些
        view.setBackground(getResources().getDrawable(R.drawable.progress));//progress文件中设置了蓝条为蓝色背景、圆角
        recommend_bluestrip.addView(view);//把蓝条加入到相对布局

        //数据适配器
        PagerAdapter mPagerAdapter1 = new PagerAdapter()
        {
            @Override
            //获取当前窗体界面数
            public int getCount()
            {
                return recommend_pageview.size();
            }
            @Override
            //判断是否由对象生成界面
            public boolean isViewFromObject(View arg0, Object arg1)
            {
                return arg0==arg1;
            }
            //使从ViewGroup中移出当前View
            public void destroyItem(View arg0, int arg1, Object arg2)
            {
                ((ViewPager) arg0).removeView(recommend_pageview.get(arg1));
            }

            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            public Object instantiateItem(View arg0, int arg1)
            {
                ((ViewPager)arg0).addView(recommend_pageview.get(arg1));
                return recommend_pageview.get(arg1);
            }
        };
        //绑定适配器
        recommend_viewpager.setAdapter(mPagerAdapter1);
        //设置viewPager的初始界面为第一个界面
        recommend_viewpager.setCurrentItem(0);
        //添加切换界面的监听器
        recommend_viewpager.setOnPageChangeListener(new MyOnPageChangeListener1());

        //为了获取屏幕宽度，新建一个DisplayMetrics对象
        DisplayMetrics displayMetrics1 = new DisplayMetrics();
        //将当前窗口的一些信息放在DisplayMetrics类中
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics1);
        //得到屏幕的宽度
        int screenW1 = displayMetrics1.widthPixels;


        recommend_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int arg0)
            {
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {
                int left = width*arg0 + (int) (width*arg1);//蓝条滑动的距离
                RelativeLayout.LayoutParams rllp = (RelativeLayout.LayoutParams) view.getLayoutParams();
                rllp.leftMargin = left;
                view.setLayoutParams(rllp);
            }
            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });


        //标签按钮的监听事件：点到哪个就切换到那一个界面
        recommend_button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                recommend_viewpager.setCurrentItem(0);
            }
        });
        recommend_button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                recommend_viewpager.setCurrentItem(1);
            }
        });
        recommend_button3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                recommend_viewpager.setCurrentItem(2);
            }
        });
        recommend_button4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                recommend_viewpager.setCurrentItem(3);
            }
        });

        recommend_imageView1 = activity_recommend.findViewById(R.id.recommend_imageView1);
        recommend_imageView1.setOnClickListener(new MyBtnListenerrecommend_imageView1());


////////////////////////////////////////book页面控件/////////////////////////////////////////////////////
//        note_button1 = activity_book.findViewById(R.id.book_button1);
//        note_button1.setOnClickListener(new MyBtnListenerbook_button1());

////////////////////////////////////////my页面控件/////////////////////////////////////////////////////
        my_button1 = activity_my.findViewById(R.id.my_button1);
        my_button1.setOnClickListener(new MyBtnListenermy_button1());
        my_button2 = activity_my.findViewById(R.id.my_button2);
        my_button2.setOnClickListener(new MyBtnListenermy_button2());
        my_button3 = activity_my.findViewById(R.id.my_button3);
        my_button3.setOnClickListener(new MyBtnListenermy_button3());
        my_button4 = activity_my.findViewById(R.id.my_button4);
        my_button4.setOnClickListener(new MyBtnListenermy_button4());
        my_button5 = activity_my.findViewById(R.id.my_button5);
        my_button5.setOnClickListener(new MyBtnListenermy_button5());
        my_button6 = activity_my.findViewById(R.id.my_button6);
        my_button6.setOnClickListener(new MyBtnListenermy_button6());
        my_button7 = activity_my.findViewById(R.id.my_button7);
        my_button7.setOnClickListener(new MyBtnListenermy_button7());
        my_button8 = activity_my.findViewById(R.id.my_button8);
        my_button8.setOnClickListener(new MyBtnListenermy_button8());
        my_button9 = activity_my.findViewById(R.id.my_button9);
        my_button9.setOnClickListener(new MyBtnListenermy_button9());
        my_button10 = activity_my.findViewById(R.id.my_button10);
        my_button10.setOnClickListener(new MyBtnListenermy_button10());
        my_button11 = activity_my.findViewById(R.id.my_button11);
        my_button11.setOnClickListener(new MyBtnListenermy_button11());
        my_button12 = activity_my.findViewById(R.id.my_button12);
        my_button12.setOnClickListener(new MyBtnListenermy_button12());
        my_button13 = activity_my.findViewById(R.id.my_button13);
        my_button13.setOnClickListener(new MyBtnListenermy_button13());
        my_button14 = activity_my.findViewById(R.id.my_button14);
        my_button14.setOnClickListener(new MyBtnListenermy_button14());
        my_button15 = activity_my.findViewById(R.id.my_button15);
        my_button15.setOnClickListener(new MyBtnListenermy_button15());
////////////////////////////////////////main页面控件/////////////////////////////////////////////////////


    }

    //以下按钮点击事件
    class MyBtnListenermain_button1 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            main_viewpager.setCurrentItem(0);
        }
    }
    class MyBtnListenermain_button2 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            main_viewpager.setCurrentItem(1);
        }
    }
    class MyBtnListenermain_button3 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            main_viewpager.setCurrentItem(3);
        }
    }

    //以下页面滑动代码

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener
    {

        @Override
        public void onPageSelected(int arg0)
        {
            //arg0为切换到的页的编码
            currIndex = arg0;
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {
        }
        @Override
        public void onPageScrollStateChanged(int arg0)
        {
        }
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.main_button1:
                //点击"视频“时切换到第一页
                main_viewpager.setCurrentItem(0);
                break;
            case R.id.main_button2:
                //点击“音乐”时切换的第二页
                main_viewpager.setCurrentItem(1);
                break;
            case R.id.main_button3:
                //点击“音乐”时切换的第二页
                main_viewpager.setCurrentItem(1);
                break;
//            case R.id.custom_button_1:
//                //点击"视频“时切换到第一页
//                custom_viewpager.setCurrentItem(0);
//                break;
//            case R.id.custom_button_2:
//                //点击“音乐”时切换的第二页
//                custom_viewpager.setCurrentItem(1);
//                break;
        }
    }

///////////////////////////////////////////以下recommend/////////////////////////////////////

    public class MyOnPageChangeListener1 implements ViewPager.OnPageChangeListener
    {

        @Override
        public void onPageSelected(int arg0)
        {
            //arg0为切换到的页的编码
            currIndex1 = arg0;
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2)
        {
        }
        @Override
        public void onPageScrollStateChanged(int arg0)
        {
        }
    }

    class MyBtnListenerrecommend_imageView1 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Toast.makeText(AnotherMainActivity.this,"这是搜索",Toast.LENGTH_LONG).show();//弹出提示框
            Intent intent= new Intent(AnotherMainActivity.this,Search.class);
            startActivity(intent);
        }
    }
/////////////////////////////////////////////////////////////以下book/////////////////////////////////////

    class MyBtnListenerbook_button1 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Toast.makeText(AnotherMainActivity.this,"啦啦啦啦啦",Toast.LENGTH_LONG).show();//弹出提示框
            Intent intent= new Intent(AnotherMainActivity.this,Book_1.class);
            startActivity(intent);
        }
    }

    /////////////////////////////////////////////////////////////以下my/////////////////////////////////////
    class MyBtnListenermy_button1 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent= new Intent(AnotherMainActivity.this,My_ydsc.class);
            startActivity(intent);
        }
    }
    class MyBtnListenermy_button2 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent= new Intent(AnotherMainActivity.this,My_sq.class);
            startActivity(intent);
        }
    }
    class MyBtnListenermy_button3 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent= new Intent(AnotherMainActivity.this,My_hx.class);
            startActivity(intent);
        }
    }
    class MyBtnListenermy_button4 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent= new Intent(AnotherMainActivity.this,My_pz.class);
            startActivity(intent);
        }
    }
    class MyBtnListenermy_button5 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent= new Intent(AnotherMainActivity.this,My_gwc.class);
            startActivity(intent);
        }
    }
    class MyBtnListenermy_button6 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent= new Intent(AnotherMainActivity.this,My_ygm.class);
            startActivity(intent);
        }
    }
    class MyBtnListenermy_button7 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent= new Intent(AnotherMainActivity.this,My_wdbj.class);
            startActivity(intent);
        }
    }
    class MyBtnListenermy_button8 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent= new Intent(AnotherMainActivity.this,My_wdye.class);
            startActivity(intent);
        }
    }
    class MyBtnListenermy_button9 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent= new Intent(AnotherMainActivity.this,My_wdlq.class);
            startActivity(intent);
        }
    }
    class MyBtnListenermy_button10 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent= new Intent(AnotherMainActivity.this,My_wdds.class);
            startActivity(intent);
        }
    }
    class MyBtnListenermy_button11 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent= new Intent(AnotherMainActivity.this,My_wdzp.class);
            startActivity(intent);
        }
    }
    class MyBtnListenermy_button12 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent= new Intent(AnotherMainActivity.this,My_wdrj.class);
            startActivity(intent);
        }
    }
    class MyBtnListenermy_button13 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent= new Intent(AnotherMainActivity.this,My_ygxsj.class);
            startActivity(intent);
        }
    }
    class MyBtnListenermy_button14 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent= new Intent(AnotherMainActivity.this,My_drbdts.class);
            startActivity(intent);
        }
    }
    class MyBtnListenermy_button15 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent intent= new Intent(AnotherMainActivity.this,My_sz.class);
            startActivity(intent);
        }
    }
}
