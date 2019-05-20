package com.andrstudy.simplememo;

import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MemoAdapter extends RecyclerView.Adapter<MemoViewHolder> {

    private ArrayList<MemoBean> data;
    private ItemClickListener listener;

    public MemoAdapter(ArrayList<MemoBean> data, ItemClickListener listener){
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MemoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_memo, viewGroup, false);
        return new MemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoViewHolder holder, int i) {
        MemoBean memo = data.get(i);

        holder.textViewTitle.setText(memo.getTitle());
        Date date = new Date(memo.getTime());
        SimpleDateFormat format =
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        holder.textViewTime.setText(format.format(date));
        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(v, i);
        });
    }

    @Override
    public int getItemCount() {
        if(data == null) return 0;

        return data.size();
    }
}
