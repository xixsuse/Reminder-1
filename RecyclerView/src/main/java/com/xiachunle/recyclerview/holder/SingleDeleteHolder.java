package com.xiachunle.recyclerview.holder;

import android.view.View;
import android.widget.ImageView;

import com.xiachunle.recyclerview.R;

/**
 * Created by xiachunle on 2017/1/2.
 */

public class SingleDeleteHolder extends BaseViewHolder {
   public  ImageView img_delete;
    public SingleDeleteHolder(View itemView) {
        super(itemView);
        img_delete=(ImageView)itemView.findViewById(R.id.image_delete);
    }
}
