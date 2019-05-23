package com.andrstudy.a0509game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DialogActivity extends AppCompatActivity {
    private TextView dialogTextView;
    private Button ButtonOK;
    public String message;
    public int totalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        setResult(Activity.RESULT_CANCELED);
        dialogTextView = findViewById(R.id.dialogTextView);
        ButtonOK = findViewById(R.id.ButtonOK);
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
        }else if(message.equals("NoText")){
            dialogTextView.setText("정답란이 비어있어요!");
        }
        ButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result", "OK");
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
//        return super.onTouchEvent(event);
    }
}
