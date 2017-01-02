package com.xiachunle.reminder.recyclerView.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiachunle.reminder.R;
import com.xiachunle.reminder.ui.ItemTitleTextView;


/**
 * Created by xiachunle on 2016/12/22.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    private boolean isGird;

    public ItemTitleTextView itemText;


    public MyViewHolder(View itemView, boolean isGird) {
        super(itemView);
        this.isGird = isGird;
        itemText = (ItemTitleTextView) itemView.findViewById( isGird?R.id.grid_item_text:R.id.linear_item_text);
    }

}
