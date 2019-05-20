package com.andrstudy.a0509game;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder> {
    private ArrayList<QuestionBean> data;
    private ItemClickListener listener;

    public QuestionAdapter(ArrayList<QuestionBean> data, ItemClickListener listener){
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_question, viewGroup, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder questionViewHolder, int i) {
        QuestionBean question = data.get(i);

        questionViewHolder.textViewQid.setText(String.valueOf(question.getQid()));
        questionViewHolder.textViewQuestion.setText(question.getQuestion());
        Date date = new Date(question.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        questionViewHolder.textViewTime.setText(format.format(date));
        questionViewHolder.textViewType.setText(question.getType());
        questionViewHolder.itemView.setOnClickListener(v -> {
            listener.onItemClick(v, i);
        });
    }

    @Override
    public int getItemCount() {
        if(data == null) return 0;

        return data.size();
    }
}
