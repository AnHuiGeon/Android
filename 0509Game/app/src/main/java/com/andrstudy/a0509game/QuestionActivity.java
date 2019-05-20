package com.andrstudy.a0509game;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonSave;
    private ToggleButton toggleButton;
    private EditText editTitle, editScore, textQuestion1, textQuestion2, textQuestion3, textQuestion4;
    private ImageView setImage1, setImage2, setImage3, setImage4;
    private RadioButton radio1, radio2, radio3, radio4;
    private ConstraintLayout textLayout, imageLayout;
    private final int REQ_IMAGE1 = 1;
    private final int REQ_IMAGE2 = 2;
    private final int REQ_IMAGE3 = 3;
    private final int REQ_IMAGE4 = 4;
    public int answerRadioNumber;
    public String toggleButtonStatus;   // String보다 boolean으로 하는건 어떨까?? 왜? -> getStatus()시 true, false로 반환하기 때문이지!
    public String imageUri1, imageUri2, imageUri3, imageUri4;

    private int qid;
    private QuestionBean question;
    private QuestionDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        imageUri1 = imageUri2 = imageUri3 = imageUri4 = "";
        answerRadioNumber = 0;
        toggleButtonStatus = QuestionBean.TYPE_TEXT;
        textLayout = findViewById(R.id.textLayout);     //시마이
        imageLayout = findViewById(R.id.imageLayout);   //시마이
        toggleButton = findViewById(R.id.toggleButton); //시마이
        setImage1 = findViewById(R.id.setImage1);       //시마이
        setImage2 = findViewById(R.id.setImage2);       //시마이
        setImage3 = findViewById(R.id.setImage3);       //시마이
        setImage4 = findViewById(R.id.setImage4);       //시마이
        setImage1.setOnClickListener(this);
        setImage2.setOnClickListener(this);
        setImage3.setOnClickListener(this);
        setImage4.setOnClickListener(this);
        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);
        radio3 = findViewById(R.id.radio3);
        radio4 = findViewById(R.id.radio4);
        editTitle = findViewById(R.id.editTitle);
        editScore = findViewById(R.id.editScore);
        textQuestion1 = findViewById(R.id.textEdit1);
        textQuestion2 = findViewById(R.id.textEdit2);
        textQuestion3 = findViewById(R.id.textEdit3);
        textQuestion4 = findViewById(R.id.textEdit4);
        dbHelper = new QuestionDBHelper(this, "quizdb", null, 1);
        qid = getIntent().getIntExtra("qid", -1);   // 리사이클러뷰어의 아이템을 클릭 시 양방향 인텐트가 열리고, getIntExtra로 전송해준 정보를 받아낸다.

        radio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerRadioNumber = 1;
            }
        });
        radio2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerRadioNumber = 2;
            }
        });
        radio3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerRadioNumber = 3;
            }
        });
        radio4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerRadioNumber = 4;
            }
        });

        buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   // save버튼 누를 때 조건 -> (문제, 배점, 라디오버튼 체크, 예문 4개) OK -> 비로소 save 기능을 하도록 제한.
                if(answerRadioNumber * editTitle.getText().length() * editScore.getText().length() > 0) {
                    if(toggleButtonStatus == QuestionBean.TYPE_TEXT) {
                        if((textQuestion1.getText().length() * textQuestion2.getText().length() * textQuestion3.getText().length() * textQuestion4.getText().length()) > 0){
                            question.setQuestion(editTitle.getText().toString());
                            question.setScore(Integer.parseInt(editScore.getText().toString()));
                            question.setEx1(textQuestion1.getText().toString());
                            question.setEx2(textQuestion2.getText().toString());
                            question.setEx3(textQuestion3.getText().toString());
                            question.setEx4(textQuestion4.getText().toString());
                            question.setType(toggleButtonStatus);
                            question.setAnswer(answerRadioNumber);
                            if(qid > -1){
                                dbHelper.update(question);
                            }else{
                                dbHelper.insert(question);
                            }
                            finish();
                        }
                    }else if(toggleButtonStatus == QuestionBean.TYPE_IMAGE){
                        if(imageUri1.length() * imageUri2.length() * imageUri3.length() * imageUri4.length() > 0){
                            question.setQuestion(editTitle.getText().toString());
                            question.setScore(Integer.parseInt(editScore.getText().toString()));
                            question.setEx1(imageUri1);
                            question.setEx2(imageUri2);
                            question.setEx3(imageUri3);
                            question.setEx4(imageUri4);
                            question.setType(toggleButtonStatus);
                            question.setAnswer(answerRadioNumber);
                            if(qid > -1){
                                dbHelper.update(question);
                            }else{
                                dbHelper.insert(question);
                            }
                            finish();
                        }
                    }
                }
            }
        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean x) {
                if(x == false){         // false : TEXT모드
                    textLayout.setVisibility(View.VISIBLE);
                    imageLayout.setVisibility(View.INVISIBLE);
                    toggleButtonStatus = QuestionBean.TYPE_TEXT;
                }else if(x == true){    // true : IMAGE 모드
                    imageLayout.setVisibility(View.VISIBLE);
                    textLayout.setVisibility(View.INVISIBLE);
                    toggleButtonStatus = QuestionBean.TYPE_IMAGE;
                }
            }
        });

        // 위 -> 아래 순서로 진행하므로 밑에다가 작성해라...이걸로 2시간 날렸다...ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ
        // dbHelper.get()이다~!!! 불러낸다~!!!!!
        // 아이템 누르고 넘어올 때 누른 아이템의 정보를 문제칸에 띄워야한다.
        Toast.makeText(this, ""+qid, Toast.LENGTH_SHORT).show();
        if(qid > -1){
            question = dbHelper.get(qid);
            editTitle.setText(question.getQuestion());
            editScore.setText(String.valueOf(question.getScore()));     // db에서 데이터를 뽑아낼 때 형변환이 필요한 것은 "형변환"을 필히 해줘라...!!!
            if(question.getType().equals(QuestionBean.TYPE_TEXT)){
                toggleButtonStatus = QuestionBean.TYPE_TEXT;
                toggleButton.setChecked(false);
                textQuestion1.setText(question.getEx1());
                textQuestion2.setText(question.getEx2());
                textQuestion3.setText(question.getEx3());
                textQuestion4.setText(question.getEx4());
            }else if(question.getType().equals(QuestionBean.TYPE_IMAGE)){
                toggleButtonStatus = QuestionBean.TYPE_IMAGE;
                toggleButton.setChecked(true);
                setImage1.setImageURI(Uri.parse(question.getEx1()));
                imageUri1 = question.getEx1();
                setImage2.setImageURI(Uri.parse(question.getEx2()));
                imageUri2 = question.getEx2();
                setImage3.setImageURI(Uri.parse(question.getEx3()));
                imageUri3 = question.getEx3();
                setImage4.setImageURI(Uri.parse(question.getEx4()));
                imageUri4 = question.getEx4();
            }
            if(question.getAnswer() == 1){
                radio1.setChecked(true);
                answerRadioNumber = 1;
            }else if(question.getAnswer() == 2){
                radio2.setChecked(true);
                answerRadioNumber = 2;
            }else if(question.getAnswer() == 3){
                radio3.setChecked(true);
                answerRadioNumber = 3;
            }else if(question.getAnswer() == 4){
                radio4.setChecked(true);
                answerRadioNumber = 4;
            }
        }else{
            question = new QuestionBean();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        if(v.getId() ==  R.id.setImage1){
            startActivityForResult(intent, REQ_IMAGE1);
        }else if(v.getId() ==  R.id.setImage2){
            startActivityForResult(intent, REQ_IMAGE2);
        }else if(v.getId() ==  R.id.setImage3){
            startActivityForResult(intent, REQ_IMAGE3);
        }else if(v.getId() ==  R.id.setImage4){
            startActivityForResult(intent, REQ_IMAGE4);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null){
            Uri uri = data.getData();
            if(uri != null){
                if(requestCode == REQ_IMAGE1){
                    setImage1.setImageURI(uri);
                    imageUri1 = uri.toString();
                }else if(requestCode == REQ_IMAGE2){
                    setImage2.setImageURI(uri);
                    imageUri2 = uri.toString();
                }else if(requestCode == REQ_IMAGE3){
                    setImage3.setImageURI(uri);
                    imageUri3 = uri.toString();
                }else if(requestCode == REQ_IMAGE4){
                    setImage4.setImageURI(uri);
                    imageUri4 = uri.toString();
                }
            }
        }
    }

    public void onDelete(View view) {
        if(qid > -1){
            finish();
        }
        dbHelper.delete(qid);
        setResult(RESULT_OK);
        finish();
    }
}
