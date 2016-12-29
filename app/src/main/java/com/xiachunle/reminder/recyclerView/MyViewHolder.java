package com.xiachunle.reminder.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiachunle.reminder.R;


/**
 * Created by xiachunle on 2016/12/22.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    private boolean isGird;
    public TextView itemText;
    public TextView timeText;
    public ImageView imageView;
    public TextView deleteView;

    public MyViewHolder(View itemView, boolean isGird) {
        super(itemView);
        this.isGird = isGird;

        timeText = (TextView) itemView.findViewById( isGird? R.id.grid_item_text:R.id.linear_item_time);
        itemText = (TextView) itemView.findViewById( isGird?R.id.grid_item_text:R.id.linear_item_text);
        imageView = (ImageView) itemView.findViewById(isGird?R.id.grid_item_picture:R.id.linear_item_picture);
//        deleteView=(TextView)itemView.findViewById(R.id.item_delete);
    }


}
