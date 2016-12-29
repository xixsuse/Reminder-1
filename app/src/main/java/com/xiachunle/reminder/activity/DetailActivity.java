package com.xiachunle.reminder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;

import com.xiachunle.reminder.R;
import com.xiachunle.reminder.fragment.DetailFragment;


/**
 * Created by xiachunle on 2016/12/4.
 */

public class DetailActivity extends FragmentActivity {


    DetailFragment fragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        FragmentManager fragmentManager=getSupportFragmentManager();

        Bundle bundle=getIntent().getExtras();

        if(bundle!=null){

           fragment =DetailFragment.getInstance(bundle.getString("position"));
        }
        fragmentManager.beginTransaction().add(R.id.detailFragment, fragment)
                .addToBackStack(getPackageName())
                .commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(DetailActivity.this,ContentActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
