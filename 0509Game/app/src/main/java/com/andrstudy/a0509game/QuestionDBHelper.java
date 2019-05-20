package com.andrstudy.a0509game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

//OpenHistory를 상속받음,
public class QuestionDBHelper extends SQLiteOpenHelper {
    private ArrayList<QuestionBean> data;

    public QuestionDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        data = new ArrayList<>();
    }

    //onCreate메서드
    // -> 앱 설치 후 DB를 최초 실행할 때 이 메서드가 실행됨(Create Table 같은 느낌임), 앱을 사용하면서 단 한번만 실행
    // -> 사용자가 지정한 이름의 DB가 존재하지 않을 때 호출
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table quiz (qid integer primary key autoincrement, question text, type text, ex1 text, ex2 text, ex3 text, ex4 text, score integer, answer integer, time integer)";  // 테이블 생성 및 컬럼 추가
        db.execSQL(sql);    // 아마도 이걸로 진또배기 생성인듯
    }

    //onUpgrade
    // -> oloVersion : 현재 버전, newVersion : 업데이트 해야하는 최신 버전
    // -> 테이블의 구조 수정, 새로 만드는 동작 등이 가능함.
    // -> 사용자가 지정한 이름의 DB는 존재하지만 사용자가 전달한 버전이 현재 버전보다 높을 때 호출.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table quiz";
        db.execSQL(sql);
        onCreate(db);
    }

    // 삽입
    public long insert(QuestionBean question){
        SQLiteDatabase db = getWritableDatabase();  // 추가, 수정, 삭제 기능이 가능하도록 힘을 부여하는듯...!!
        ContentValues values = new ContentValues();
        values.put("question", question.getQuestion());
        values.put("type", question.getType());
        values.put("ex1", question.getEx1());
        values.put("ex2", question.getEx2());
        values.put("ex3", question.getEx3());
        values.put("ex4", question.getEx4());
        values.put("score", question.getScore());
        values.put("answer", question.getAnswer());
        values.put("time", System.currentTimeMillis());
        return db.insert("quiz", null, values);
    }

    // 수정
    public int update(QuestionBean question){
        SQLiteDatabase db = getWritableDatabase();  // 추가, 수정, 삭제 기능이 가능하도록 힘을 부여하는듯!!!!
        ContentValues values = new ContentValues();
        values.put("question", question.getQuestion());
        values.put("type", question.getType());
        values.put("ex1", question.getEx1());
        values.put("ex2", question.getEx2());
        values.put("ex3", question.getEx3());
        values.put("ex4", question.getEx4());
        values.put("score", question.getScore());
        values.put("answer", question.getAnswer());
        values.put("time", System.currentTimeMillis());
        String qid = String.valueOf(question.getQid());
        return db.update("quiz", values, "qid=?", new String[] {qid});
    }

    // 삭제
    public int delete(int qid){
        SQLiteDatabase db = getWritableDatabase();
        String qidStr = String.valueOf(qid);
        return db.delete("quiz", "qid=?", new String[] {qidStr});
    }

    public QuestionBean get(int qid){
        QuestionBean question = new QuestionBean();
        SQLiteDatabase db = getReadableDatabase();  // 추가, 수정, 삭제 기능이 가능하도록 힘을 부여한당!
        String qidStr = String.valueOf(qid);
        Cursor c = db.query("quiz", null, "qid=?", new String[] {qidStr}, null, null, null);
        if(c.moveToNext()){
            question.setQid(c.getInt(c.getColumnIndex("qid")));
            question.setQuestion(c.getString(c.getColumnIndex("question")));
            question.setType(c.getString(c.getColumnIndex("type")));
            question.setEx1(c.getString(c.getColumnIndex("ex1")));
            question.setEx2(c.getString(c.getColumnIndex("ex2")));
            question.setEx3(c.getString(c.getColumnIndex("ex3")));
            question.setEx4(c.getString(c.getColumnIndex("ex4")));
            question.setScore(c.getInt(c.getColumnIndex("score")));
            question.setAnswer(c.getInt(c.getColumnIndex("answer")));
            question.setTime(c.getLong(c.getColumnIndex("time")));        // 날짜 저장하고싶다...
            return question;
        }else{
            return null;
        }
    }

    public ArrayList<QuestionBean> get(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query("quiz", null, null, null, null, null, null);
        data.clear();
        while(c.moveToNext()){
            QuestionBean question = new QuestionBean();
            question.setQid(c.getInt(c.getColumnIndex("qid")));
            question.setQuestion(c.getString(c.getColumnIndex("question")));
            question.setType(c.getString(c.getColumnIndex("type")));
            question.setEx1(c.getString(c.getColumnIndex("ex1")));
            question.setEx2(c.getString(c.getColumnIndex("ex2")));
            question.setEx3(c.getString(c.getColumnIndex("ex3")));
            question.setEx4(c.getString(c.getColumnIndex("ex4")));
            question.setScore(c.getInt(c.getColumnIndex("score")));
            question.setAnswer(c.getInt(c.getColumnIndex("answer")));
            question.setTime(c.getLong(c.getColumnIndex("time")));        // 날짜 저장하고싶다...
            data.add(question);
        }
        return data;
    }
}



//    public long insert(QuestionBean bean){
//        return 0;
//    }
//    public int update(QuestionBean bean){
//        return 0;
//    }
//    public int delete(int qid){
//        return 0;
//    }
//    public QuestionBean select(int qid){
//        return null;
//    }
//    public ArrayList<QuestionBean> select(){
//        return null;
//    }
//    // 위 처럼 하거나 아래 처럼 하거나
//    public long insert(QuestionBean bean);
//    public int update(QuestionBean bean);
//    public int delete(int qid);
//    public QuestionBean select(int qid);
//    public ArrayList<QuestionBean> select();
