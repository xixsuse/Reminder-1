package com.xiachunle.recyclerview.itemtouch;

/**
 * Created by xiachunle on 2017/1/1.
 */

public interface OnItemTouchCallBackListener {
    /**
     * Item操作--滑动或者拖动
     */

    void onSwiped(int adapterPosition);//滑动

    boolean onMove(int srcPosition,int targetPosition);//拖拽移动交互位置
}
