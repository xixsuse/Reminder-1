package com.xiachunle.reminder.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.xiachunle.reminder.R;

/**
 * Created by xiachunle on 2017/1/2.
 */

public class LinearTextView extends LinearLayout {
    public LinearTextView(Context context) {
        this(context,null);
    }

    public LinearTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LinearTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ui_linear_text,this);
    }
}
