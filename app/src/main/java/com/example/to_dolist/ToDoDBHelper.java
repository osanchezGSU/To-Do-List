package com.example.to_dolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ToDoDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "todolist.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_MEMO =
            "CREATE TABLE memo (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "subjectInput TEXT NOT NULL," +
                    "memoInput TEXT," +
                    "date TEXT," +
                    "criticality TEXT);";

    public ToDoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MEMO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ToDoDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS memo");
        onCreate(db);
    }
}
