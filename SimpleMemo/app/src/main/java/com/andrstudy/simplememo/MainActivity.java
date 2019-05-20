package com.andrstudy.simplememo;

import android.content.ClipData;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemClickListener{

    private MemoDBHelper dbHelper;
    private RecyclerView listView;
    private MemoAdapter adapter;
    private ArrayList<MemoBean> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MemoDBHelper(this, "DB", null, 1);
        data = dbHelper.get();

        adapter = new MemoAdapter(data, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        listView = findViewById(R.id.memoList);
        listView.setAdapter(adapter);
        listView.setLayoutManager(manager);
    }

    @Override
    public void onItemClick(View v, int index) {
        Intent intent = new Intent(this, MemoActivity.class);
        intent.putExtra("id", data.get(index).getId());
        startActivityForResult(intent, 1);
    }

    public void onNew(View v){
        Intent intent = new Intent(this, MemoActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            this.data = dbHelper.get();
            adapter.notifyDataSetChanged();
        }
    }
}
