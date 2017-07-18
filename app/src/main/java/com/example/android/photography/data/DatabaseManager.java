package com.example.android.photography.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.example.android.photography.data.QuestionContract.QuestionEntry.TABLE_NAME;

/**
 * Created by Abdulkarim on 7/17/2017.
 */

public class DatabaseManager {
    private static DatabaseManager sInstance;

    public static synchronized DatabaseManager getInstance(Context context){
        if (sInstance == null){
            sInstance = new DatabaseManager(context.getApplicationContext());
        }
        return sInstance;
    }

    private QuestionDbHelper mDbHelper;

    public DatabaseManager(Context context){
        mDbHelper = new QuestionDbHelper(context);
    }

    public Cursor queryAllQuestions (){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        return db.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public Cursor queryQuestionsById(int id){
         String where = QuestionContract.QuestionEntry._ID + "=?";
        String[] whereArgs = {String.valueOf(id)};

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        return db.query(TABLE_NAME,
                null,
                where,
                whereArgs,
                null,
                null,
                null);
    }
}
