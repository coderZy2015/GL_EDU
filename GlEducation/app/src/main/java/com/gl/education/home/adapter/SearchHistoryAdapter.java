package com.gl.education.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gl.education.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy on 2018/8/23.
 */

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter
        .RecyclerHolder> {
    private Context mContext;
    private List<String> dataList = new ArrayList<>();
    private onRecyclerItemClickerListener mListener;

    public SearchHistoryAdapter(RecyclerView recyclerView) {
        this.mContext = recyclerView.getContext();
    }

    public void setData(List<String> dataList) {
        if (null != dataList) {
            this.dataList.clear();
            this.dataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_history, parent,
                false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerHolder holder, int position) {
        holder.textView.setText(dataList.get(position));

        holder.itemView.setOnClickListener(getOnClickListener(position));
    }

    private View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null && v != null) {
                    mListener.onRecyclerItemClick(v, dataList.get(position), position);
                }
            }
        };
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    /**
     * 增加点击监听
     */
    public void setItemListener(onRecyclerItemClickerListener mListener) {
        this.mListener = mListener;
    }


    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView textView;

        private RecyclerHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.contentTextView);
        }
    }

    /**
     * 点击监听回调接口
     */
    public interface onRecyclerItemClickerListener {
        void onRecyclerItemClick(View view, String data, int position);
    }
}

