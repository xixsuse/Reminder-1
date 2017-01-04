package com.xiachunle.reminder.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xiachunle.reminder.R;
import com.xiachunle.reminder.activity.DetailActivity;
import com.xiachunle.reminder.activity.SettingActivity;
import com.xiachunle.reminder.adapter.DBAdapter;
import com.xiachunle.reminder.adapter.MyRecyViewAdapter;
import com.xiachunle.reminder.bean.MemoReminders;
import com.xiachunle.reminder.recyclerView.MyItemDivider;
import com.xiachunle.reminder.ui.AddTextView;
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


    private AddTextView addImg;
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
        sh = new SharedHelper(getActivity().getApplicationContext());
        isGrid = sh.getLayout();
        initRecyView(v);

        setTextView = (TextView) v.findViewById(R.id.setting);
        bottomLayout = (LinearLayout) v.findViewById(R.id.bottomLayout);
        addImg = (AddTextView) v.findViewById(R.id.add_img);
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
        contentRecyView.addItemDecoration(new MyItemDivider(isGrid));

        if (!isGrid) {
            layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            contentRecyView.setLayoutManager(layoutManager);

        } else {
            contentRecyView.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        }

        recyViewAdapter = new MyRecyViewAdapter(getActivity(), lists, isGrid);
        recyViewAdapter.addOnItemOnClickListener(new MyRecyViewAdapter.OnItemOnClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                Log.e("test", "show:" + holder.getAdapterPosition() + "-----" + position);
                dbAdapter.open();
                memoReminder = dbAdapter.fetchReminderById(position+1);
                createOrEditreminder(String.valueOf(position)+1);
                getActivity().finish();
            }
        });
        recyViewAdapter.addOnMenuOnClickListener(new MyRecyViewAdapter.OnMenuOnClickListener() {
            @Override
            public void onMenuClick(RecyclerView.ViewHolder holder, int position) {
                dbAdapter.deleteReminderByRealId(holder.getAdapterPosition()+1);
                dbAdapter.updataRealId();
            }
        });
        contentRecyView.setAdapter(recyViewAdapter);
        contentRecyView.setItemAnimator(new DefaultItemAnimator());
        contentRecyView.setHasFixedSize(true);


    }


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setting:
                    Log.e("Test","aaaa");
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    ListView modeListView = new ListView(getActivity());
                    String[] modes = {"选择布局", "清空"};
                    ArrayAdapter<String> modeApapter = new ArrayAdapter<String>(getActivity(),
                           R.layout.setting_dialog
                            , R.id.set_dialog, modes);
                    modeListView.setAdapter(modeApapter);
                    builder.setView(modeListView);
                    final Dialog dialog = builder.create();
                    dialog.show();
                    modeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position == 0) {
                                Intent intent = new Intent(getActivity(), SettingActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }else {
                                dbAdapter.deleteAllReminder();
                                dbAdapter.updataRealId();
                                if(lists.size()>0){
                                    lists.removeAll(lists);
                                    recyViewAdapter.notifyDataSetChanged();
                                }
                                lists=dbAdapter.fetchAllReminders();
                                recyViewAdapter=new MyRecyViewAdapter(getActivity(),lists,isGrid);
                                contentRecyView.setAdapter(recyViewAdapter);

                            }
                            dialog.dismiss();
                        }
                    });
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

    class MyHandler extends  Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    if(lists.size()>0){
                        lists.removeAll(lists);
                        recyViewAdapter.notifyDataSetChanged();
                    }
                    lists=dbAdapter.fetchAllReminders();

                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
