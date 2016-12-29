package com.xiachunle.reminder.adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;


import com.xiachunle.reminder.R;
import com.xiachunle.reminder.bean.MemoReminders;
import com.xiachunle.reminder.recyclerView.MyViewHolder;
import com.xiachunle.reminder.util.FileUtil;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by xiachunle on 2016/12/1.
 */

public class MyRecyViewAdapter extends RecyclerView.Adapter{

    private List<MemoReminders> lists = new ArrayList<>();

    private boolean isGrid;
    private Context mContext;
    private LayoutInflater inflater;

    public MyRecyViewAdapter(Context context, List<MemoReminders> list, boolean isGrid) {
        this.lists.clear();
        if (list != null) {
            this.lists.addAll(list);
        }
        notifyDataSetChanged();
        this.isGrid = isGrid;
        this.mContext = context;
        this.inflater=LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        View itemView =inflater.inflate(isGrid? R.layout.list_grid:R.layout.list_linear,parent,false);
        return new MyViewHolder(itemView,isGrid);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MemoReminders reminders=lists.get(position);
        final MyViewHolder myViewHolder=(MyViewHolder)holder;
        myViewHolder.itemText.setSingleLine(true);
        myViewHolder.itemText.setEllipsize(TextUtils.TruncateAt.END);

        String content = reminders.getmContent();
        if (reminders.getImageDatas() == null) {
            if (content.length() > 4) {
                content = content.substring(0, 4);
            } else {
                content = content;
            }
            myViewHolder.imageView.setVisibility(View.GONE);
        } else {
            myViewHolder.imageView.setVisibility(View.VISIBLE);
        }

        myViewHolder.timeText.setText(FileUtil.parseTime(reminders.getCreateTime())==null?"":
        FileUtil.parseTime(reminders.getCreateTime()));
        myViewHolder.itemText.setText(content);

    }


    @Override
    public int getItemCount() {

        return lists ==null?0: lists.size();
    }



}
