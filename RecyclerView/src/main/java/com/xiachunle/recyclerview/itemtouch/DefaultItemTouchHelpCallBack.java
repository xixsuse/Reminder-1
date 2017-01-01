package com.xiachunle.recyclerview.itemtouch;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by xiachunle on 2017/1/1.
 */

public class DefaultItemTouchHelpCallBack extends ItemTouchHelper.Callback {

    private OnItemTouchCallBackListener itemTouchCallBackListener;

    private boolean isCanSwiped=false;//是否可以滑动
    private boolean isCanDrap=false;//是否可以拖动

    public DefaultItemTouchHelpCallBack(OnItemTouchCallBackListener itemTouchCallBackListener) {
        this.itemTouchCallBackListener = itemTouchCallBackListener;
    }

    //设置Item的操作回调，更新数据和UI
    public void setItemTouchCallBackListener(OnItemTouchCallBackListener itemTouchCallBackListener) {
        this.itemTouchCallBackListener = itemTouchCallBackListener;
    }

    //设置是否滑动
    public void setCanSwiped(boolean canSwiped) {
        isCanSwiped = canSwiped;
    }
    //设置是否被拖动
    public void setCanDrap(boolean canDrap) {
        isCanDrap = canDrap;
    }

    //Item长按时是否可以被拖拽
    @Override
    public boolean isLongPressDragEnabled() {
        return isCanDrap;
    }
    //Item是否可以滑动
    @Override
    public boolean isItemViewSwipeEnabled() {
        return isCanSwiped;
    }


    //感应UI界面上的操作
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutManager layoutManager=recyclerView.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){//
            int dragFlag=ItemTouchHelper.LEFT|ItemTouchHelper.UP|ItemTouchHelper.RIGHT|ItemTouchHelper.DOWN;
            int swipeFlag=0;
            return makeMovementFlags(dragFlag,swipeFlag);
        }else if(layoutManager instanceof LinearLayoutManager){
            LinearLayoutManager lineLayoutManager=(LinearLayoutManager)layoutManager;
            int orientation=lineLayoutManager.getOrientation();
            int dragFlag=0;
            int swipeFlag=0;
            if(orientation==LinearLayoutManager.HORIZONTAL){
                swipeFlag=ItemTouchHelper.UP|ItemTouchHelper.DOWN;
                dragFlag=ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
            }else if(orientation==LinearLayoutManager.VERTICAL){
                swipeFlag=ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
                dragFlag=ItemTouchHelper.UP|ItemTouchHelper.DOWN;
            }
            return makeMovementFlags(dragFlag,swipeFlag);
        }
        return 0;
    }

    //拖动操作是的回调
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
       if(itemTouchCallBackListener!=null){
           return  itemTouchCallBackListener.onMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
       }
        return false;
    }

    //滑动回调
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
         if(itemTouchCallBackListener!=null){
             itemTouchCallBackListener.onSwiped(viewHolder.getAdapterPosition());
         }
    }
}
