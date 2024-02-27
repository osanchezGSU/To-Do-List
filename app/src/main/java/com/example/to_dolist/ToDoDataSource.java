package com.example.to_dolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.Calendar;
import java.util.ArrayList;

public class ToDoDataSource {
    private SQLiteDatabase database;
    private ToDoDBHelper dbHelper;

    public ToDoDataSource(Context context) {
        dbHelper = new ToDoDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertMemo(Memo c) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put("subjectInput", c.getSubjectInput());
            initialValues.put("memoInput", c.getMemoInput());
            initialValues.put("date", String.valueOf(c.getDate().getTimeInMillis()));
            initialValues.put("criticality", c.getCriticality());
            didSucceed = database.insert("memo", null, initialValues) > 0;

        } catch (Exception e) {
            //do nothing here
        }
        return didSucceed;
    }
    public boolean updateMemo(Memo c) {
        boolean didSucceed = false;
        try{
            int rowId = c.getId();
            ContentValues updateValues = new ContentValues();
            updateValues.put("subjectInput", c.getSubjectInput());
            updateValues.put("memoInput", c.getMemoInput());
            updateValues.put("date", String.valueOf(c.getDate().getTimeInMillis()));
            updateValues.put("criticality", c.getCriticality());
            didSucceed = database.update("memo", updateValues, "_id=" +rowId,
                    null)>0;
        } catch (Exception e) {

        }
        return didSucceed;
    }
    public int getLastId(){
        int lastId;
        try{
            String query = "Select MAX (_id) from memo";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        } catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }
    // code for ArrayList/ ListActivity
    public ArrayList<Memo> getMemos(String sortBy, String sortOrder) {
        ArrayList<Memo> memos = new ArrayList<>();
        try {
            String query = "SELECT * FROM memo ORDER BY " + sortBy + " " + sortOrder;
            Cursor cursor = database.rawQuery(query, null);

            Memo newMemo;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                newMemo = new Memo();
                newMemo.setId(cursor.getInt(0));
                newMemo.setSubjectInput(cursor.getString(1));
                newMemo.setMemoInput(cursor.getString(2));
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.valueOf(cursor.getString(3)));
                newMemo.setDate(calendar);
                newMemo.setCriticality(cursor.getString(4));
                memos.add(newMemo);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {
            memos = new ArrayList<>();
        }
        return memos;
    }

    public Memo getSpecificMemo(int id) {
        Memo memo = new Memo();
        String query = "SELECT * FROM memo WHERE _id =" + id;
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            memo.setId(cursor.getInt(0));
            memo.setSubjectInput(cursor.getString(1));
            memo.setMemoInput(cursor.getString(2));
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.valueOf(cursor.getString(3)));
            memo.setDate(calendar);
            memo.setCriticality(cursor.getString(4));

            cursor.close();
        }
        return memo;
    }

    public boolean deleteMemo(int id) {
        boolean didDelete = false;
        try {
            didDelete = database.delete("memo", "_id=" + id, null) > 0;
        } catch (Exception e) {
        }
        return didDelete;
    }

}
