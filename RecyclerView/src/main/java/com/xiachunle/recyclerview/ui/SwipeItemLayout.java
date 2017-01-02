package com.xiachunle.recyclerview.ui;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by xiachunle on 2017/1/2.
 */

public class SwipeItemLayout extends FrameLayout {

    private View menu;
    private View content;

    private final ViewDragHelper dragHelper;
    private boolean isOpen;
    private int currentState;
    public SwipeItemLayout(Context context) {
        this(context,null);
    }

    public SwipeItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        dragHelper=ViewDragHelper.create(this,rightCallBack);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menu=getChildAt(0);
        content=getChildAt(1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return  true;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return dragHelper.shouldInterceptTouchEvent(event);
    }
    private ViewDragHelper.Callback rightCallBack=new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return content==child;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left>0?0:left<-menu.getWidth()?-menu.getWidth():left;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            //item的宽度一半
            if(isOpen){
                if(xvel>menu.getWidth() ||-content.getLeft()<menu.getWidth()/2){
                    close();
                }else{
                    open();
                }
            }else {
                if(-xvel>menu.getWidth()||-content.getLeft()>menu.getWidth()/2){
                    open();
                }else {
                    close();
                }
            }
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return super.getViewHorizontalDragRange(child);
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return super.getViewVerticalDragRange(child);
        }

        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
            currentState=state;
        }
    };
    public void close(){
        dragHelper.smoothSlideViewTo(content,0,0);
        isOpen=false;
        invalidate();
    }
    public void open(){
        dragHelper.smoothSlideViewTo(content,-menu.getWidth(),0);
        isOpen=true;
        invalidate();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public int getState(){
        return currentState;
    }

    public Rect outRect=new Rect();
    public Rect getMenuRect(){
        menu.getHitRect(outRect);
        return outRect;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(dragHelper.continueSettling(true)){
            invalidate();
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        content.setOnClickListener(l);
    }
}
