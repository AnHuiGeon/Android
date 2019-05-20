package com.andrstudy.simplememo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MemoActivity extends AppCompatActivity {

    private int id;
    private MemoBean memo;
    private MemoDBHelper dbHelper;

    private EditText editTextTitle;
    private EditText editTextBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        editTextBody = findViewById(R.id.editTextBody);
        editTextTitle = findViewById(R.id.editTextTitle);

        dbHelper = new MemoDBHelper(this, "DB", null, 1);

        id = getIntent().getIntExtra("id", -1);
        if(id > -1){
            memo = dbHelper.get(id);
            editTextTitle.setText(memo.getTitle());
            editTextBody.setText(memo.getBody());
        } else {
            memo = new MemoBean();
        }
    }

    public void onSave(View v){
        memo.setBody(editTextBody.getText().toString());
        memo.setTitle(editTextTitle.getText().toString());
        if(id > -1)
            dbHelper.update(memo);
        else
            dbHelper.insert(memo);
        setResult(RESULT_OK);
        finish();
    }

    public void onDelete(View v){
        if(id == -1) finish();
        dbHelper.delete(id);
        setResult(RESULT_OK);
        finish();
    }
}
