package com.andrstudy.a0509game;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static int READ_STOREAGE = 0;
    static boolean easymode = false;
    static boolean hardmode = false;
    static boolean checked = false;
    private Button buttonHard, buttonEasy, buttonStart;
    private ImageView imageLogin;
    private TextView textViewMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 버전 검사 + 권한 부여
        if (Build.VERSION.SDK_INT >= 23) {
            int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STOREAGE);
            }
        }

        textViewMode = findViewById(R.id.textViewMode);
        textViewMode.setText("모드를 선택해주세요");
        buttonEasy = findViewById(R.id.buttonEasy);
        buttonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = true;
                easymode = true;
                hardmode = false;
                textViewMode.setText("모든 문제가 객관식으로 출제됩니다.");
            }
        });
        buttonHard = findViewById(R.id.buttonHard);
        buttonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = true;
                hardmode = true;
                easymode = false;
                textViewMode.setText("객관식과 주관식이 출제됩니다.");
            }
        });

        buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked == true){
                    Intent start = new Intent(MainActivity.this, QuizActivity.class);
                    if(easymode == true){
                        start.putExtra("GameMode",false);
                    } else if(hardmode == true){
                        start.putExtra("GameMode", true);
                    }
                    startActivity(start);
                }
            }
        });

        imageLogin = findViewById(R.id.imageLogin);
        imageLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(MainActivity.this, QuestionListActivity.class);
                startActivity(login);
            }
        });
    }
}
