package com.project.administrator.huaqin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class FileListAdapter extends BaseAdapter {

    private List<String> fileList;

    private Context context;

    public FileListAdapter(Context context, List<String> fileList) {

        this.fileList = fileList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return fileList.size();
    }

    @Override
    public Object getItem(int position) {
        return fileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        View view = convertView;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.file_item, null);

            viewHolder.name = view.findViewById(R.id.textView_name);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.name.setText(fileList.get(position));

        return view;
    }

    public static class ViewHolder{
        TextView name;
    }
}
