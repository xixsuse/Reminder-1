package com.xiachunle.reminder.adapter;

import android.content.Context;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;


import com.xiachunle.reminder.R;
import com.xiachunle.reminder.bean.MemoReminders;
import com.xiachunle.reminder.recyclerView.holder.MyViewHolder;
import com.xiachunle.reminder.recyclerView.holder.SwipeMenuViewHolder;
import com.xiachunle.reminder.util.FileUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xiachunle on 2016/12/1.
 */

public class MyRecyViewAdapter extends RecyclerView.Adapter {

    private List<MemoReminders> lists = new ArrayList<>();

    private boolean isGrid;
    private Context mContext;
    private LayoutInflater inflater;

    private OnItemOnClickListener mOnItemOnClickListener;

    private OnMenuOnClickListener onMenuOnClickListener;

    public MyRecyViewAdapter(Context context, List<MemoReminders> list, boolean isGrid) {
        this.lists.clear();
        if (list != null) {
            this.lists.addAll(list);
        }
        notifyDataSetChanged();
        this.isGrid = isGrid;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);

    }

    public void addOnItemOnClickListener(OnItemOnClickListener onItemOnClickListener) {
        this.mOnItemOnClickListener = onItemOnClickListener;
    }

    public void addOnMenuOnClickListener(OnMenuOnClickListener onMenuOnClickListener){
        this.onMenuOnClickListener=onMenuOnClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (isGrid) {
            view = inflater.inflate(R.layout.list_grid, parent, false);
            return new MyViewHolder(view, isGrid);
        } else {
            view = inflater.inflate(R.layout.item_menu_delete, parent, false);
            return new SwipeMenuViewHolder(view);
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final MemoReminders reminders = lists.get(position);
        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.itemText.setSingleLine(true);
        myViewHolder.itemText.setEllipsize(TextUtils.TruncateAt.END);

        String content = reminders.getmContent();
        if (reminders.getImageDatas() == null) {
            if (content.length() > 4) {
                content = content.substring(0, 4);
            } else {
                content = content;
            }
        }

        myViewHolder.itemText.setText(FileUtil.parseTime(reminders.getCreateTime()) == null ? "" :
                FileUtil.parseTime(reminders.getCreateTime()));
        myViewHolder.itemText.append(content);
        myViewHolder.itemText.setImageShow(reminders.getImageDatas() == null);
        myViewHolder.itemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemOnClickListener.onItemClick(holder, position);
            }
        });
        if (!isGrid) {
            SwipeMenuViewHolder swipeMenuViewHolder = (SwipeMenuViewHolder) holder;
            swipeMenuViewHolder.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                      onMenuOnClickListener.onMenuClick(holder,position);
                    lists.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }
            });
        }
    }


    @Override
    public int getItemCount() {

        return lists == null ? 0 : lists.size();
    }

    public interface OnItemOnClickListener {
        void onItemClick(RecyclerView.ViewHolder holder, int position);
    }

    public interface OnMenuOnClickListener{
        void onMenuClick(RecyclerView.ViewHolder holder, int position);
    }
}
