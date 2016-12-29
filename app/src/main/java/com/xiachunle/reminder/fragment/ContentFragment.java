package com.xiachunle.reminder.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiachunle.reminder.R;
import com.xiachunle.reminder.activity.DetailActivity;
import com.xiachunle.reminder.activity.SettingActivity;
import com.xiachunle.reminder.adapter.DBAdapter;
import com.xiachunle.reminder.adapter.MyRecyViewAdapter;
import com.xiachunle.reminder.bean.MemoReminders;
import com.xiachunle.reminder.recyclerView.MyItemDivider;
import com.xiachunle.reminder.recyclerView.MyItemOntouchListenr;
import com.xiachunle.reminder.util.SharedHelper;

import java.util.ArrayList;



/**
 * Created by xiachunle on 2016/12/1.
 * <p>
 * 备忘录主界面
 */

public class ContentFragment extends Fragment {

    private RecyclerView contentRecyView;
    private MyRecyViewAdapter recyViewAdapter;
    private MemoReminders memoReminder;

    private ArrayList<MemoReminders> lists;
    private LinearLayout selectLayout, bottomLayout;
    private TextView setTextView;


    private TextView addImg;
    public static boolean isGrid;
    public static boolean isLayoutSelect = false;
    private View fragmentView;
    private DBAdapter dbAdapter;

    private SharedHelper sh;

    public static ContentFragment getInstance() {
        ContentFragment contentFragment = new ContentFragment();


        return contentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content, container, false);
        fragmentView = v;
        dbAdapter = new DBAdapter(getActivity().getApplicationContext());
        dbAdapter.open();
        lists = dbAdapter.fetchAllReminders();
        initView(v);
        return v;
    }

    private void initView(View v) {
        sh=new SharedHelper(getActivity().getApplicationContext());
        isGrid=sh.getLayout();
        initRecyView(v);

        setTextView = (TextView) v.findViewById(R.id.setting);
        bottomLayout = (LinearLayout) v.findViewById(R.id.bottomLayout);
        addImg = (TextView) v.findViewById(R.id.add_img);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_input_add);
        ImageSpan imageSpan = new ImageSpan(getActivity(), bm);
        SpannableString ss = new SpannableString(addImg.getText().toString());
        ss.setSpan(imageSpan, 0, 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        addImg.setText(ss);
        setTextView.setOnClickListener(clickListener);


        addImg.setOnClickListener(clickListener);

    }

    private void initRecyView(View v) {
        contentRecyView = (RecyclerView) v.findViewById(R.id.content_revyview);
        contentRecyView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置方向
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        contentRecyView.setLayoutManager(layoutManager);


        if (!isGrid) {
            layoutManager=new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            contentRecyView.setLayoutManager(layoutManager);

        } else {
            contentRecyView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        }
        contentRecyView.addItemDecoration(new MyItemDivider(isGrid));
        contentRecyView.setAdapter(recyViewAdapter = new MyRecyViewAdapter(getActivity(), lists, isGrid));
        contentRecyView.setItemAnimator(new DefaultItemAnimator());
        contentRecyView.setHasFixedSize(true);
        contentRecyView.addOnItemTouchListener(
                new MyItemOntouchListenr(getActivity(),
                        contentRecyView, new MyItemOntouchListenr.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                dbAdapter.open();

                memoReminder = dbAdapter.fetchReminderById(position + 1);
                createOrEditreminder(String.valueOf(position + 1));
                getActivity().finish();
            }

            @Override
            public void OnItemLongClick(int position) {
                Toast.makeText(getActivity(),"Long Press "+(position+1),Toast.LENGTH_SHORT).show();
            }
        }));

    }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setting:
                    Intent intent = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    break;
                case R.id.add_img:
                    isLayoutSelect = false;
                    createOrEditreminder(null);
                    getActivity().finish();
                    break;
            }
        }
    };

    private void createOrEditreminder(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("position", id);
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
