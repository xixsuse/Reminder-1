package com.xiachunle.reminder.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.xiachunle.reminder.R;
import com.xiachunle.reminder.fragment.ContentFragment;
import com.xiachunle.reminder.util.SharedHelper;


/**
 * Created by xiachunle on 2016/12/18.
 */

public class SettingActivity extends AppCompatActivity  implements View.OnClickListener{

    private TextView lineSet,gridSet;
    public static  boolean isGrid;

    SharedHelper sh;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        isGrid= ContentFragment.isGrid;
        inittView();
    }

    private void inittView() {
        sh=new SharedHelper(getApplicationContext());
        lineSet=(TextView)findViewById(R.id.lineSetting);
        gridSet=(TextView)findViewById(R.id.gridSetting);
        if(isGrid){
            if (Build.VERSION.SDK_INT >= 23) {
//                gridSet.setBackgroundColor(getResources().getColor(R.color.colorAccent,null));
            }else{
                gridSet.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
        }else{
            if (Build.VERSION.SDK_INT >= 23) {
//                lineSet.setBackgroundColor(getResources().getColor(R.color.colorAccent,null));
            }else{
                lineSet.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            }
        }
        lineSet.setOnClickListener(this);
        gridSet.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.lineSetting:
                isGrid=false;
                sh.saveLayout(isGrid);
                if (Build.VERSION.SDK_INT >= 23) {
//                    lineSet.setBackgroundColor(getResources().getColor(R.color.colorAccent,null));
//                    gridSet.setBackgroundColor(getResources().getColor(R.color.clear,null));
                }else{
                    lineSet.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    gridSet.setBackgroundColor(getResources().getColor(R.color.clear));
                }

                break;
            case R.id.gridSetting:
                isGrid=true;
                sh.saveLayout(isGrid);
                if (Build.VERSION.SDK_INT >= 23) {
//                    gridSet.setBackgroundColor(getResources().getColor(R.color.colorAccent,null));
//                    lineSet.setBackgroundColor(getResources().getColor(R.color.clear,null));
                }else{
                    gridSet.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    lineSet.setBackgroundColor(getResources().getColor(R.color.clear));
                }

                break;
        }
       startActivity(new Intent(this,ContentActivity.class));
        finish();

    }
}
