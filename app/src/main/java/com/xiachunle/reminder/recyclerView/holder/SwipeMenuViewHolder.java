package com.xiachunle.reminder.recyclerView.holder;

import android.view.View;
import android.widget.ImageView;

import com.xiachunle.reminder.R;

/**
 * Created by xiachunle on 2017/1/2.
 */

public class SwipeMenuViewHolder extends MyViewHolder {

    public ImageView imageDelete;
    public SwipeMenuViewHolder(View itemView) {
        super(itemView,false);
        imageDelete=(ImageView)itemView.findViewById(R.id.image_delete);
    }
}
