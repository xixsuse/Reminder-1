package com.xiachunle.recyclerview.itemtouch;

import android.support.v7.widget.helper.SimpleitemTouchHelper;

/**
 * Created by xiachunle on 2017/1/1.
 */

public class DefaultItemTouchHelper extends SimpleitemTouchHelper {

    private DefaultItemTouchHelpCallBack itemTouchHelpCallBack;
    public DefaultItemTouchHelper(OnItemTouchCallBackListener itemTouchCallBackListener) {
        super(new DefaultItemTouchHelpCallBack(itemTouchCallBackListener));
        itemTouchHelpCallBack=(DefaultItemTouchHelpCallBack)getCallback();
    }

    /**
     * 设置是否可以被拖拽
     * @param canDrag
     */
    public void setDragEnable(boolean canDrag){
        itemTouchHelpCallBack.setCanDrap(canDrag);
    }

    /**
     * 设置是否可以滑动
     * @param canSwip
     */
    public void setSwipeEnabled(boolean canSwip){
        itemTouchHelpCallBack.setCanSwiped(canSwip);
    }
}


