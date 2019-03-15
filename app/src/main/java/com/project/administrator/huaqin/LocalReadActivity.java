package com.project.administrator.huaqin;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LocalReadActivity extends AppCompatActivity {

    int textSize = 70;

    int textColor = Color.BLACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_read);
        MakeFilePathUtil.makeDirectory(this);

        MakeFilePathUtil.createTestFile(this);

        final PagesUtil pagesUtil = new PagesUtil(400);
//        final TextView content = findViewById(R.id.textView_txt);
        final ImageView imageView = findViewById(R.id.imageView_content);
        Button nextPageBtn = findViewById(R.id.next_button);


        nextPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pageData = pagesUtil.getNextPageData("testFile.txt", getBaseContext());
                Bitmap bitmap = PagesUtil.createPageBitMap(pageData, imageView.getWidth(), imageView.getHeight(), Constant.TEXT_SIZE, Constant.TEXT_COLOR);

                imageView.setImageBitmap(bitmap);

            }
        });

        Button prePageBtn = findViewById(R.id.pre_button);
        prePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pageData = pagesUtil.getPrePageData("testFile.txt", getBaseContext());
//                Log.d("pagedata: ", pageData);
//                content.setText(pageData);
                Bitmap bitmap = PagesUtil.createPageBitMap(pageData, imageView.getWidth(), imageView.getHeight(), textSize, textColor);
                imageView.setImageBitmap(bitmap);
            }
        });


    }




}
