package com.example.to_dolist;

import java.util.Calendar;

public class Memo {


    private int id;
    private String subjectInput;
    private String memoInput;
    private Calendar date;
    private String criticality;

    public Memo(){
        id = -1;
        date =  Calendar.getInstance();


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectInput() {
        return subjectInput;
    }

    public void setSubjectInput(String subjectInput) {
        this.subjectInput = subjectInput;
    }

    public String getMemoInput() {
        return memoInput;
    }

    public void setMemoInput(String memoInput) {
        this.memoInput = memoInput;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getCriticality() {
        return criticality;
    }

    public void setCriticality(String criticality) {
        this.criticality = criticality;
    }
}
