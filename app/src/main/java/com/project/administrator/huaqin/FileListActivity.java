package com.project.administrator.huaqin;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class FileListActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        final List<String> fileList = FileUtil.fileSearch(Environment.getExternalStorageDirectory().getAbsolutePath() + getResources().getString(R.string.filePath));
        FileListAdapter fileListAdapter = new FileListAdapter(getBaseContext(), fileList);
        listView = findViewById(R.id.listView_file);
        listView.setAdapter(fileListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FileListActivity.this, PageFlipActivity.class);
                intent.putExtra("fileName", fileList.get(position));
                startActivity(intent);

            }
        });
    }
}
