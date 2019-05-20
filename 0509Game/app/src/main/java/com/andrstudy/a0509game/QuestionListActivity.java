package com.andrstudy.a0509game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

//ImageView처럼 창문과 같은 역할을 하는 RecyclerView가 존재.
public class QuestionListActivity extends AppCompatActivity implements ItemClickListener {
    private static final int CODE = 1234;
    private EditText password;
    private Button login;
    private ConstraintLayout mainView, loginView;
    private ImageView makeQuiz;

    //DB파트 시작
    private QuestionDBHelper dbHelper;
    private RecyclerView listView;
    private QuestionAdapter adapter;
    private ArrayList<QuestionBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        dbHelper = new QuestionDBHelper(this, "quizdb", null, 1);
        data = dbHelper.get();

        adapter = new QuestionAdapter(data, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setLayoutManager(manager);

        password = findViewById(R.id.password);

        mainView = findViewById(R.id.mainView);
        loginView = findViewById(R.id.loginView);
        makeQuiz = findViewById(R.id.makeQuiz);

        makeQuiz.setOnClickListener(new View.OnClickListener() {    // + 버튼임!!!!!!!!!!!
            @Override
            public void onClick(View v) {
                Intent make = new Intent(QuestionListActivity.this, QuestionActivity.class);
                startActivityForResult(make, 1);
            }
        });

        login = findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().length() != 0) {
                    if (Integer.parseInt(password.getText().toString()) == CODE) {
                        mainView.setVisibility(View.INVISIBLE);
                        loginView.setVisibility(View.VISIBLE);
                    }else {
                        finish();
                    }
                }else {
                    finish();
                }
            }
        });
    }

    @Override
    public void onItemClick(View v, int index) {
            Intent intent = new Intent(this, QuestionActivity.class);
            intent.putExtra("qid", data.get(index).getQid());
            startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == -1){   // RESULT_OK = -1, RESULT_FIRST_USER = 1
            return;
        }
        this.dbHelper.get();
        adapter.notifyDataSetChanged();
    }
}
