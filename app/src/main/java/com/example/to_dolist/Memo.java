package com.example.to_dolist;

public class Memo {
    private long id;
    private String subjectInput;
    private String memoInput;
    private String date;
    private String criticality;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCriticality() {
        return criticality;
    }

    public void setCriticality(String criticality) {
        this.criticality = criticality;
    }
}
