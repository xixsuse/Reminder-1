package com.xiachunle.reminder.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiachunle.reminder.R;
import com.xiachunle.reminder.activity.ContentActivity;


/**
 * Created by xiachunle on 2016/11/30.
 * 引导页4
 */

public class FourFragment extends Fragment implements View.OnClickListener{
    private TextView controlTxt;
    public static FourFragment getInstance(){
        return new FourFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_four,container,false);
        initView(v);
        return v;
    }

    private void initView(View v) {
        controlTxt= (TextView) v.findViewById(R.id.launch_Control);
//        controlTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.launch_Control){
            Intent intent=new Intent(getActivity(), ContentActivity.class);
            intent.putExtra("install&&update","");
            startActivity(intent);
            getActivity().finish();
        }
    }
}
