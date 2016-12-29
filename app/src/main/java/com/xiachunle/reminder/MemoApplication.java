package com.xiachunle.reminder;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiachunle on 2016/11/30.
 */

public class MemoApplication extends Application {
    private List<Activity> activityList = new ArrayList<Activity>();
    private static MemoApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static MemoApplication getInstance() {
        if(instance==null){
            instance=new MemoApplication();
        }
        return instance;
    }

    public void addActivity(Activity activity){
        activityList.add(activity);
    }
    public void  exit(){
        for (Activity activity:activityList) {
            activity.finish();
        }
        System.exit(0);
    }
}
