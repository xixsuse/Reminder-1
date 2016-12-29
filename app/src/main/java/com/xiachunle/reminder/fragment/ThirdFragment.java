package com.xiachunle.reminder.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiachunle.reminder.R;


/**
 * Created by xiachunle on 2016/11/30.
 * 引导页3
 */

public class ThirdFragment extends Fragment {
    public static ThirdFragment getInstance(){
        return new ThirdFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_third,container,false);
    }
}
