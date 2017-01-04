package com.xiachunle.reminder.ui;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiachunle.reminder.R;

/**
 * Created by xiachunle on 2017/1/2.
 */

public class ItemTitleTextView extends LinearLayout {
    private TextView textView;
    private ImageView pictureImage,importImage;

    public ItemTitleTextView(Context context) {
        this(context, null);
    }

    public ItemTitleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemTitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ui_titletext, this);
        textView = (TextView) findViewById(R.id.text1);
        pictureImage = (ImageView) findViewById(R.id.image1);
        importImage=(ImageView)findViewById(R.id.image_import);
        importImage.setSelected(true);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setImageShow(boolean flag) {
        pictureImage.setVisibility(flag ? VISIBLE : GONE);
    }
    public void setImpoetImage(boolean flag){
        importImage.setVisibility(flag?VISIBLE:GONE);
    }
    public void setSingleLine(boolean tag){
        textView.setSingleLine(tag);
    }
    public void setEllipsize(TextUtils.TruncateAt where){
        textView.setEllipsize(where);
    }
    public void append(CharSequence text){
        textView.append(text);
    }


}
