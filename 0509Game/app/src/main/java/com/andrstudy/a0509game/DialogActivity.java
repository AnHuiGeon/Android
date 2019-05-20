package com.andrstudy.a0509game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class DialogActivity extends AppCompatActivity {
    TextView dialogTextView;
    public String message;
    public int totalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
        dialogTextView = findViewById(R.id.dialogTextView);
        message = getIntent().getStringExtra("message");
        totalScore = getIntent().getIntExtra("totalScore", -1);
        if(message.equals("EndGame")){
            dialogTextView.setText("최종 " + totalScore + "점");
        }else if(message.equals("Ok")){
            dialogTextView.setText("정답!\n" + "+" + totalScore + "점");
        }else if(message.equals("No")){
            dialogTextView.setText("오답ㅠㅠ");
        }else if(message.equals("Choice")){
            dialogTextView.setText("답을 골라주세요!");
        }
    }
}
