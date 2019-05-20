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

public class MainActivity extends AppCompatActivity {
    static int READ_STOREAGE = 0;
    static boolean easymode = false;
    static boolean hardmode = false;
    static boolean checked = false;
    private Button buttonHard, buttonEasy, buttonStart;
    private ImageView imageLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= 23) {
            int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STOREAGE);
            }
        }

        buttonEasy = findViewById(R.id.buttonEasy);
        buttonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = true;
                easymode = true;
                hardmode = false;
            }
        });
        buttonHard = findViewById(R.id.buttonHard);
        buttonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked = true;
                hardmode = true;
                easymode = false;
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
