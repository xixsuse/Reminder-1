package com.xiachunle.reminder.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.xiachunle.reminder.MemoApplication;
import com.xiachunle.reminder.R;
import com.xiachunle.reminder.adapter.MyLaunchFragmentAdapter;
import com.xiachunle.reminder.fragment.ExitDialogFragment;
import com.xiachunle.reminder.fragment.FirstFragment;
import com.xiachunle.reminder.fragment.FourFragment;
import com.xiachunle.reminder.fragment.SecondFragment;
import com.xiachunle.reminder.fragment.ThirdFragment;

import java.util.ArrayList;
import java.util.List;


import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.view.ViewPager.*;

public class LaunchActivity extends FragmentActivity {

    boolean isFirstIn = false;
    SharedPreferences preferences;

    public static final String TAG = "LAUNCHACTIVITY";
    private ViewPager lauchView;
    private LinearLayout mPoints;
    private List<Fragment> fragmentLists;
    private int lastPosition;

    private Button skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        MemoApplication.getInstance().addActivity(this);
        preferences = getSharedPreferences("first_pref", MODE_PRIVATE);
//        isFirstIn=preferences.getBoolean("isFirstIn",true);
//        if(!isFirstIn){
//            Intent intent=new Intent();
//            intent.setClass(getApplicationContext(),ContentActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        SharedPreferences.Editor editor =preferences.edit();
//        editor.putBoolean("isFirstIn",false);
//        editor.commit();
        initView();
        initEvent();
    }


    private void initEvent() {
        lauchView.setAdapter(new MyLaunchFragmentAdapter(getSupportFragmentManager(), fragmentLists));
        lauchView.setCurrentItem(0);
        lauchView.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == fragmentLists.size() - 1) {
                    skipButton.setVisibility(View.GONE);
                } else {
                    skipButton.setVisibility(View.VISIBLE);
                }
                position = position % fragmentLists.size();
                mPoints.getChildAt(position).setEnabled(true);
                mPoints.getChildAt(lastPosition).setEnabled(false);
                lastPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView() {
        lauchView = (ViewPager) findViewById(R.id.launchview);
        skipButton = (Button) findViewById(R.id.skipBtn);
        fragmentLists = new ArrayList<>();
        mPoints = (LinearLayout) findViewById(R.id.point_id);
        fragmentLists.add(FirstFragment.getInstance());
        fragmentLists.add(SecondFragment.getInstance());
        fragmentLists.add(ThirdFragment.getInstance());
        fragmentLists.add(FourFragment.getInstance());
        for (int i = 0; i < fragmentLists.size(); i++) {
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_selector);
            LinearLayout.LayoutParams lp = new
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.rightMargin = 20;
            point.setLayoutParams(lp);
            mPoints.addView(point);
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }
        }
        skipButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaunchActivity.this, ContentActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            new ExitDialogFragment().show(getSupportFragmentManager(), "Exit");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
