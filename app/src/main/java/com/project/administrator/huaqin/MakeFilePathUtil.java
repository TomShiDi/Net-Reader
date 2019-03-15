package com.project.administrator.huaqin;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MakeFilePathUtil {


    public static void createTestFile(Context context) {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + context.getString(R.string.filePath) + "/" + "testFile.txt");
        Log.d("Directory: ", Environment.getExternalStorageDirectory().getAbsolutePath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter fileWriter = new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + context.getString(R.string.filePath) + "/" + "testFile.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < 4; i++) {
                bufferedWriter.write("   TextView中可以设置一个ellipsize属性,作用是当文字长度超过textview宽度时的显示方式:\n" +
                        "\n" +
                        "     例如，字符串\"abcedfg\" 的各种现实效果：\n" +
                        "\n" +
                        "\n" +
                        "android:ellipsize=”start”—–省略号显示在开头 \"...edfg\"\n" +
                        "android:ellipsize=”end”——省略号显示在结尾  \"abcdec...\"\n" +
                        "android:ellipsize=”middle”—-省略号显示在中间 \"ab...fg\"\n" +
                        "android:ellipsize=”marquee”–以横向滚动方式显示(需获得当前焦点时)\n" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "对于使用marquee即滚动显示方式的，需要当前textview获得焦点才会滚动。所以有时可能因为实际需要，textview未获得焦点或者需要多个textview都同时滚动显示时，可以采用以下办法：\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "     因为判断textview是否处于focused状态是通过它本身isFocused()方法，这样只要此方法返回为true时，即认为处于focused的状态，就可以滚动啦。\n" +
                        "\n" +
                        "所以可以通过继承TextView类，并override isFocused()方法来控制是否滚动咯。\n" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "     另外如果是组合View，外层layout需要加入以下属性来保证focus状态的传递：addStatesFromChildren=\"true\"\n" +
                        "--------------------- \n" +
                        "作者：Seachal \n" +
                        "来源：CSDN \n" +
                        "原文：https://blog.csdn.net/zhangxichao100/article/details/52139123 \n" +
                        "版权声明：本文为博主原创文章，转载请附上博文链接！");
            }


            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void makeDirectory(Context context) {
        File file = null;

        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + context.getString(R.string.filePath));

        if (!file.exists()) {
            file.mkdirs();
        }


    }
}
