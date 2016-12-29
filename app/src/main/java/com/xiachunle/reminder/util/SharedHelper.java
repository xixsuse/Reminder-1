package com.xiachunle.reminder.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xiachunle on 2016/12/30.
 */

public class SharedHelper {
    private Context mContext;

    public SharedHelper(Context mContext) {
        this.mContext = mContext;
    }

    public SharedHelper() {
    }

    public  void saveLayout(boolean isGrid ){
        SharedPreferences sp=mContext.getSharedPreferences("layout",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("grid",isGrid);
        editor.commit();
    }

    public  boolean getLayout(){
        SharedPreferences sp=mContext.getSharedPreferences("layout",Context.MODE_PRIVATE);
        return sp.getBoolean("grid",false);
    }
}
