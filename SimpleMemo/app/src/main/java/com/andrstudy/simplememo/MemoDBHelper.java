package com.andrstudy.simplememo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class MemoDBHelper extends SQLiteOpenHelper {

    private ArrayList<MemoBean> data;

    public MemoDBHelper(@Nullable Context context, @Nullable String name,
                        @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        data = new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="create table memo(id integer primary key autoincrement, ";
        sql += "title text, body text, time integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table memo";
        db.execSQL(sql);
        onCreate(db);
    }

    public long insert(MemoBean memo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", memo.getTitle());
        values.put("body", memo.getBody());
        values.put("time", System.currentTimeMillis());
        return db.insert("memo", null, values);
    }

    public int update(MemoBean memo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", memo.getTitle());
        values.put("body", memo.getBody());
        values.put("time", System.currentTimeMillis());
        String idStr = String.valueOf(memo.getId());
        return db.update("memo", values, "id=?", new String[] {idStr});
    }

    public int delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        String idStr = String.valueOf(id);
        return db.delete("memo", "id=?", new String[] {idStr});
    }

    public MemoBean get(int id){
        SQLiteDatabase db = getReadableDatabase();
        String idStr = String.valueOf(id);
        Cursor c = db.query("memo", null,
                "id=?", new String[] {idStr},
                null, null, null);
        if(c.moveToNext()){
            MemoBean memo = new MemoBean();
            memo.setId(c.getInt(c.getColumnIndex("id")));
            memo.setTitle(c.getString(c.getColumnIndex("title")));
            memo.setBody(c.getString(c.getColumnIndex("body")));
            memo.setTime(c.getLong(c.getColumnIndex("time")));
            return memo;
        } else {
            return null;
        }
    }

    public ArrayList<MemoBean> get(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("memo", null,
                null, null,
                null, null, "time desc");
        data.clear();
        while(c.moveToNext()){
            MemoBean memo = new MemoBean();
            memo.setId(c.getInt(c.getColumnIndex("id")));
            memo.setTitle(c.getString(c.getColumnIndex("title")));
            memo.setBody(c.getString(c.getColumnIndex("body")));
            memo.setTime(c.getLong(c.getColumnIndex("time")));
            data.add(memo);
        }
        return data;
    }
}
