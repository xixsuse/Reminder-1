package com.xiachunle.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.xiachunle.recyclerview.adapter.MainAdapter;
import com.xiachunle.recyclerview.itemtouch.DefaultItemTouchHelper;
import com.xiachunle.recyclerview.itemtouch.OnItemTouchCallBackListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<String> lists=null;
    private MainAdapter mainAdapter;

    private DefaultItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lists=new ArrayList<>();
        for(int i=1;i<=20;i++){
            lists.add("第"+i+"个");
        }
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        mainAdapter=new MainAdapter(lists);
        mainAdapter.setmItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(getApplicationContext(),"点击的是"+position,Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(mainAdapter);

        itemTouchHelper=new DefaultItemTouchHelper(new OnItemTouchCallBackListener() {
            @Override
            public void onSwiped(int adapterPosition) {
                if(lists!=null){
                    lists.remove(adapterPosition);
                    mainAdapter.notifyItemRemoved(adapterPosition);
                }
            }

            @Override
            public boolean onMove(int srcPosition, int targetPosition) {
                if(lists!=null){
                    Collections.swap(lists,srcPosition,targetPosition);
                    mainAdapter.notifyItemMoved(srcPosition,targetPosition);
                    return true;
                }
                return false;
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
        mainAdapter.setmItemTouchHelper(itemTouchHelper);
        itemTouchHelper.setDragEnable(true);
        itemTouchHelper.setSwipeEnabled(true);

    }
}
