package com.xiachunle.reminder.recyclerView;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xiachunle on 2016/12/1.
 */

public class MyItemDivider extends RecyclerView.ItemDecoration {
    private boolean isGrid;
    private Paint paint;

    public MyItemDivider(boolean isGrid) {
        this.isGrid = isGrid;
        paint = new Paint();
        paint.setColor(Color.GRAY);

    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (isGrid) {
                c.drawLine(child.getRight(), child.getTop(), child.getRight(), child.getBottom(), paint);
                c.drawLine(child.getLeft(),child.getBottom(),child.getRight(),child.getBottom(),paint);
            } else {
                c.drawLine(child.getLeft(),child.getBottom(),child.getRight(),child.getBottom(),paint);

            }

        }
    }

}
