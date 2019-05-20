package com.andrstudy.simplememo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MemoViewHolder extends RecyclerView.ViewHolder {
    // 아래 두 경우에 한해서만 접근제어자를 설정하지 않는다.
    public TextView textViewTitle;
    public TextView textViewTime;

    public MemoViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.textViewTitle);
        textViewTime = itemView.findViewById(R.id.textViewTime);
    }
}
