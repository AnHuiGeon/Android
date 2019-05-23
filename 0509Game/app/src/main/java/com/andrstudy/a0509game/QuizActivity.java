package com.andrstudy.a0509game;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    private ConstraintLayout easyTextLayout, imageLayout, hardTextLayout;   // easyTextLayout -> Easy, imageLayout -> Easy, Hard 모든 상황에 다 나옴, hardTextLayout -> Hard
    private RadioGroup radioGroup;  // hardTextLayout일 때만 INVISIVLE
    private RadioButton radio1, radio2, radio3, radio4;    // 유저가 누른 답(radioChoice)과 DB의 답을 비교하라
    private TextView easyExample1, easyExample2, easyExample3, easyExample4, scoreTextView, hardExample, textViewTitle, textViewTitleContent;
    private ImageView imageExample1, imageExample2, imageExample3, imageExample4;   // EASY, HARD 모든 상황에 다 나옴
    private EditText hardEditText;  // HARD
    private Button submit;  // 답 제출
    public static int gameScore;    // 유저가 획득한 점수
    public static boolean gameLevel;   // false : easy, true : hard
    public String questionMode = null;  // 질문이 Text인지 Image인지 DB에 저장된 값으로 설정하라
    public static int gameCountStart;
    public static int gameCountEnd;
    public static int code;
    static final int DIALOG_REQ = 330;

    private ArrayList<QuestionBean> data;
    private QuestionBean question;
    private QuestionDBHelper dbHelper;

    public int radioChoice;    // 사용자가 선택한 라디오 버튼이 몇번인지 저장

    public void layoutSet(){
//        Toast.makeText(this,""+gameCountStart,Toast.LENGTH_SHORT).show();
        if(gameCountStart < gameCountEnd){
            question = data.get(gameCountStart);
            if(gameLevel == false){ // false : easy
                hardTextLayout.setVisibility(View.INVISIBLE);
                radioGroup.setVisibility(View.VISIBLE);
                questionMode = question.getType();
                textViewTitle.setText(question.getQuestion());
                if(questionMode.equals(QuestionBean.TYPE_TEXT)){
                    easyTextLayout.setVisibility(View.VISIBLE);
                    imageLayout.setVisibility(View.INVISIBLE);
                    easyExample1.setText(question.getEx1());
                    easyExample2.setText(question.getEx2());
                    easyExample3.setText(question.getEx3());
                    easyExample4.setText(question.getEx4());
                }else if(questionMode.equals(QuestionBean.TYPE_IMAGE)){
                    imageLayout.setVisibility(View.VISIBLE);
                    easyTextLayout.setVisibility(View.INVISIBLE);
                    imageExample1.setImageURI(Uri.parse(question.getEx1()));
                    imageExample2.setImageURI(Uri.parse(question.getEx2()));
                    imageExample3.setImageURI(Uri.parse(question.getEx3()));
                    imageExample4.setImageURI(Uri.parse(question.getEx4()));
                }
            }else if(gameLevel == true){    // true : hard
                easyTextLayout.setVisibility(View.INVISIBLE);
                radioGroup.setVisibility(View.INVISIBLE);
                textViewTitle.setText(question.getQuestion());
                questionMode = question.getType();
                if(questionMode.equals(QuestionBean.TYPE_TEXT)){
                    textViewTitleContent.setVisibility(View.INVISIBLE);
                    textViewTitle.setVisibility(View.INVISIBLE);
                    radioGroup.setVisibility(View.INVISIBLE);
                    imageLayout.setVisibility(View.INVISIBLE);
                    hardTextLayout.setVisibility(View.VISIBLE);
                    hardExample.setText(question.getQuestion());
                }else if(questionMode.equals(QuestionBean.TYPE_IMAGE)){
                    hardTextLayout.setVisibility(View.INVISIBLE);
                    radioGroup.setVisibility(View.VISIBLE);
                    imageLayout.setVisibility(View.VISIBLE);
                    textViewTitleContent.setVisibility(View.VISIBLE);
                    textViewTitle.setVisibility(View.VISIBLE);
                    textViewTitle.setText(question.getQuestion());
                    imageExample1.setImageURI(Uri.parse(question.getEx1()));
                    imageExample2.setImageURI(Uri.parse(question.getEx2()));
                    imageExample3.setImageURI(Uri.parse(question.getEx3()));
                    imageExample4.setImageURI(Uri.parse(question.getEx4()));
                }
            }
        }else if(gameCountStart == gameCountEnd) {
            gameCountStart = 0;
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        gameCountStart = 0;
        gameScore = 0;
        Intent intent = getIntent();
        gameLevel = intent.getExtras().getBoolean("GameMode");
        textViewTitleContent = findViewById(R.id.textView10);
        textViewTitle = findViewById(R.id.textViewQuestion);
        scoreTextView = findViewById(R.id.scorePointView);
        imageLayout = findViewById(R.id.imageLayout);
        radioGroup = findViewById(R.id.RadioGroup);
        submit = findViewById(R.id.submit);
        easyTextLayout = findViewById(R.id.easyTextLayout);
        hardTextLayout = findViewById(R.id.hardTextLayout);
        radio1 = findViewById(R.id.Radio1);
        radio2 = findViewById(R.id.Radio2);
        radio3 = findViewById(R.id.Radio3);
        radio4 = findViewById(R.id.Radio4);
        easyExample1 = findViewById(R.id.textView1);
        easyExample2 = findViewById(R.id.textView2);
        easyExample3 = findViewById(R.id.textView3);
        easyExample4 = findViewById(R.id.textView4);
        imageExample1 = findViewById(R.id.setImage1);
        imageExample2 = findViewById(R.id.setImage2);
        imageExample3 = findViewById(R.id.setImage3);
        imageExample4 = findViewById(R.id.setImage4);
        hardExample = findViewById(R.id.hardTextView);
        hardEditText = findViewById(R.id.hardEditText);
        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio1.setChecked(true);
                radioChoice = 1;
            }
        });
        radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio2.setChecked(true);
                radioChoice = 2;
            }
        });
        radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio3.setChecked(true);
                radioChoice = 3;
            }
        });
        radio4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio4.setChecked(true);
                radioChoice = 4;
            }
        });

        dbHelper = new QuestionDBHelper(this, "quizdb", null, 1);       // ==========요기서 머리가 박살나버림=========
        question = new QuestionBean();
        data = new ArrayList<QuestionBean>();
        data = dbHelper.get();
        gameCountEnd = data.size(); // gameCountStart : 0 ~ gameCountEnd : ArrayList.size();

        layoutSet();    // 초반 레이아웃 어떤걸 끄고 켤지 결정함.

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean yesOrNo = false;
                if (gameLevel == false) { // false : Easy모드
                    if (radioChoice == question.getAnswer()) {
                        yesOrNo = true;
                    }
                } else if (gameLevel == true) {    // true : Hard모드
                    if (questionMode.equals(QuestionBean.TYPE_TEXT)) {
                        if (question.getAnswer() == 1) {
                            if (question.getEx1().equals(hardEditText.getText().toString())) {
                                yesOrNo = true;
                            }
                        } else if (question.getAnswer() == 2) {
                            if (question.getEx2().equals(hardEditText.getText().toString())) {
                                yesOrNo = true;
                            }
                        } else if (question.getAnswer() == 3) {
                            if (question.getEx3().equals(hardEditText.getText().toString())) {
                                yesOrNo = true;
                            }
                        } else if (question.getAnswer() == 4) {
                            if (question.getEx4().equals(hardEditText.getText().toString())) {
                                yesOrNo = true;
                            }
                        }
                    } else if (questionMode.equals(QuestionBean.TYPE_IMAGE)) {
                        if (radioChoice == question.getAnswer()) {
                            yesOrNo = true;
                        }
                    }
                }
                Intent dialogIntent = new Intent(QuizActivity.this, DialogActivity.class);
                if(yesOrNo == true){
                    gameScore += question.getScore();
                    scoreTextView.setText(String.valueOf(gameScore));
                    radioGroup.clearCheck();
                    gameCountStart ++;
                    hardEditText.setText(null);
                    radioChoice = 0;
                    if(gameCountStart == gameCountEnd){
                        dialogIntent.putExtra("message", "EndGame");
                        dialogIntent.putExtra("totalScore", gameScore);
                    }else if(gameCountStart < gameCountEnd) {
                        dialogIntent.putExtra("message", "Ok");
                        dialogIntent.putExtra("totalScore", question.getScore());
                    }
                }else if(yesOrNo == false){
                    if(gameLevel == true && question.getType().equals(QuestionBean.TYPE_TEXT)){
                        if(hardEditText.getText().length() == 0){
                            dialogIntent.putExtra("message", "NoText");
                        }else{
                            dialogIntent.putExtra("message", "No");
                        }
                    }else {
                        if (radioChoice == 0) {
                            dialogIntent.putExtra("message", "Choice");
                        } else if (radioChoice > 0) {
                            dialogIntent.putExtra("message", "No");
                        }
                    }
                }
                startActivityForResult(dialogIntent, DIALOG_REQ);
//                layoutSet();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == DIALOG_REQ){
            if(resultCode == RESULT_OK){
                layoutSet();
            }
        }
    }
}
