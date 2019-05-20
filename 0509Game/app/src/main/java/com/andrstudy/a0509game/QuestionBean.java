package com.andrstudy.a0509game;

//데이터를 저장하고 DB와 Activity 사이에 데이터를 전달
public class QuestionBean {
    public static final String TYPE_TEXT = "TEXT";
    public static final String TYPE_IMAGE = "IMAGE";

    private int qid;
    private String question;
    private String type;
    private String ex1;
    private String ex2;
    private String ex3;
    private String ex4;
    private int score;
    private int answer;
    private long time;

    public void setQid(int qid) {
        this.qid = qid;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setEx1(String ex1) {
        this.ex1 = ex1;
    }

    public void setEx2(String ex2) {
        this.ex2 = ex2;
    }

    public void setEx3(String ex3) {
        this.ex3 = ex3;
    }

    public void setEx4(String ex4) {
        this.ex4 = ex4;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getQid() {
        return qid;
    }

    public String getQuestion() {
        return question;
    }

    public String getType() {
        return type;
    }

    public String getEx1() {
        return ex1;
    }

    public String getEx2() {
        return ex2;
    }

    public String getEx3() {
        return ex3;
    }

    public String getEx4() {
        return ex4;
    }

    public int getScore() {
        return score;
    }

    public int getAnswer() {
        return answer;
    }

    public long getTime(){ return time; }
    public void setTime(long time){ this.time = time; }
}
