package com.project.administrator.huaqin;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;

public class FoldingCellListAdapter extends ArrayAdapter<Item> {

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();

    private View.OnClickListener defaultRequestBtnClickListener;

    public FoldingCellListAdapter(Context context, List<Item> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // get item for selected view
        final Item item = getItem(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        final ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder
            viewHolder.title = cell.findViewById(R.id.textView_title);
            viewHolder.writer = cell.findViewById(R.id.textView_writer);
            viewHolder.downloadCount = cell.findViewById(R.id.textView_download_count);
            viewHolder.content = cell.findViewById(R.id.textView_content);
            viewHolder.imageView_cover = cell.findViewById(R.id.imageView_cover);
//            viewHolder.downloadBtn = cell.findViewById(R.id.textview_download);
            viewHolder.circularProgressButton = cell.findViewById(R.id.btn_progress);
//            viewHolder.content.setMovementMethod(ScrollingMovementMethod.getInstance());
            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                cell.unfold(true);
            } else {
                cell.fold(true);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }

        if (null == item)
            return cell;

        // bind data from selected element to view through view holder
        Picasso.get()
                .load(item.getBook_cover_url())
                .placeholder(R.drawable.temp)
                .error(R.drawable.error_icon)
                .into(viewHolder.imageView_cover);
//
        viewHolder.title.setText(item.getBook_title());
        viewHolder.writer.setText(item.getWriter());
        viewHolder.downloadCount.setText(item.getDownload_count());
        viewHolder.content.setText(item.getContent_description());

//         set custom btn handler for list item from that item
        if (item.getRequestBtnClickListener() != null) {
            viewHolder.circularProgressButton.setOnClickListener(item.getRequestBtnClickListener());
        } else {
            // (optionally) add "default" handler if no handler found in item
            viewHolder.circularProgressButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "开始下载", Toast.LENGTH_LONG).show();
                    NotifiCationUtil notifiCationUtil = new NotifiCationUtil(getContext());
                    notifiCationUtil.showNotification(item.getBook_title() + "  正在下载");

                    new FileDownLoadUtil().downFileThird(getContext(), notifiCationUtil,item.getDown_url(),viewHolder.circularProgressButton);

//                    notifiCationUtil.updataProgress(20);
                }
            });
        }

        return cell;
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }


    private static class ViewHolder {
        TextView title;
        TextView writer;
        TextView content;
        TextView downloadCount;
        ImageView imageView_cover;
        TextView downloadBtn;
        CircularProgressButton circularProgressButton;
    }
}
