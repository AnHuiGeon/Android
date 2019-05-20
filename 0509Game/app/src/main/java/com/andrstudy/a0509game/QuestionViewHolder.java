package com.andrstudy.a0509game;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class QuestionViewHolder extends RecyclerView.ViewHolder {
    TextView textViewQid;
    TextView textViewQuestion;
    TextView textViewTime;
    TextView textViewType;
    public QuestionViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewQid = itemView.findViewById(R.id.textViewQid);
        textViewQuestion = itemView.findViewById(R.id.textViewQuestion);
        textViewTime = itemView.findViewById(R.id.textViewTime);
        textViewType = itemView.findViewById(R.id.textViewType);
    }
}
