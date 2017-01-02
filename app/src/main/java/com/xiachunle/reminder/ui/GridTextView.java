package com.xiachunle.reminder.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.xiachunle.reminder.R;

/**
 * Created by xiachunle on 2017/1/2.
 */

public class GridTextView extends LinearLayout {
    public GridTextView(Context context) {
        this(context,null);
    }

    public GridTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GridTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ui_grid_text,this);
    }
}
