package com.xiachunle.recyclerview.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiachunle.recyclerview.R;

import java.util.List;

/**
 * Created by xiachunle on 2017/1/1.
 */

public class MainAdapter extends RecyclerView.Adapter {
    private List<String> lists;

    private OnItemClickListener mItemClickListener;

    private ItemTouchHelper mItemTouchHelper;

    public MainAdapter() {
    }

    public MainAdapter(List<String> lists) {
        this.lists = lists;
    }

    public void setmItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setmItemTouchHelper(ItemTouchHelper mItemTouchHelper) {
        this.mItemTouchHelper = mItemTouchHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu,parent,false);

        return new ContentViewHolder(view);
    }

    class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView itemText;

        public ContentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemText=(TextView)itemView.findViewById(R.id.itemText);
        }

        @Override
        public void onClick(View view) {
            if(view==itemView && mItemTouchHelper!=null){
                mItemClickListener.onItemClicked(view,getAdapterPosition());
            }
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContentViewHolder contentViewHolder=(ContentViewHolder)holder;
        contentViewHolder.itemText.setText(lists.get(position));
    }

    @Override
    public int getItemCount() {
        return lists==null?0:lists.size();
    }

    public interface OnItemClickListener{
        void onItemClicked(View view, int position);
    }
}
