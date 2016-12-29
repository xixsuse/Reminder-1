package com.xiachunle.reminder.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xiachunle on 2016/12/22.
 */

public class MyItemOntouchListenr implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener mOnItemClickListener;
    private GestureDetector mGestureDetector;


    public MyItemOntouchListenr(Context context,final  RecyclerView recyclerView,OnItemClickListener listener) {
    this.mOnItemClickListener=listener;
        mGestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childView=recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(childView!=null && mOnItemClickListener!=null){
                    mOnItemClickListener.OnItemLongClick(recyclerView.getChildLayoutPosition(childView));
                }
            }
        });

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView=rv.findChildViewUnder(e.getX(),e.getY());
        if(childView!=null && mOnItemClickListener!=null && mGestureDetector.onTouchEvent(e)){
            mOnItemClickListener.OnItemClick(rv.getChildLayoutPosition(childView));
            return true;
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }


    public interface OnItemClickListener{
        void OnItemClick(int position);
        void OnItemLongClick(int position);
    }
}
