package com.xiachunle.reminder.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.xiachunle.reminder.MemoApplication;
import com.xiachunle.reminder.R;
import com.xiachunle.reminder.fragment.ContentFragment;


/**
 * Created by xiachunle on 2016/11/30.
 */

public class ContentActivity extends FragmentActivity {

    private TextView title;

    private ContentFragment fm;

    private static final int PERMISSION_RESULT_CODE=0x011;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        MemoApplication.getInstance().addActivity(this);
        initView();
    }





    private void initView() {
        title=(TextView)findViewById(R.id.content_title);

        FragmentManager fragmentManager=getSupportFragmentManager();

        if(fm==null){
            fm=ContentFragment.getInstance();
        }
       fragmentManager.beginTransaction().add(R.id.conttentLayout, fm)
                .addToBackStack(getPackageName())
                .commit();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){

                MemoApplication.getInstance().exit();


        }
        return super.onKeyDown(keyCode, event);
    }
}
