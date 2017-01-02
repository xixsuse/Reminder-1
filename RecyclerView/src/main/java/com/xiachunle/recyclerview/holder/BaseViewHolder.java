package com.xiachunle.recyclerview.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiachunle.recyclerview.R;

/**
 * Created by xiachunle on 2017/1/2.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    public TextView item_content;
    public BaseViewHolder(final View itemView) {
        super(itemView);
        item_content=(TextView)itemView.findViewById(R.id.item_content);

    }
}
