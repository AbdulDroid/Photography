package com.example.android.photography.data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.photography.R;
import com.example.android.photography.data.QuestionContract.QuestionEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_CORRECT_ANSWER;
import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_OPTION_ONE;
import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_OPTION_THREE;
import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_OPTION_TWO;
import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_QUESTION;

/**
 * Created by Abdulkarim on 7/17/2017.
 */

public class QuestionDbHelper extends SQLiteOpenHelper{
    public static final String TAG = QuestionDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "question.db";
    private static final int DATABASE_VERSION = 1;
    private Resources mResources;

    public QuestionDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mResources = context.getResources();
    }

    @Override
    public void onCreate(SQLiteDatabase db){

        final String CREATE_QUESTION_LIST_TABLE = "CREATE TABLE " + QuestionEntry.TABLE_NAME + " (" +
                QuestionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_QUESTION + " TEXT NOT NULL, " +
                QuestionEntry.COLUMN_OPTION_ONE + " TEXT NOT NULL, " +
                QuestionEntry.COLUMN_OPTION_TWO + " TEXT NOT NULL, " +
                QuestionEntry.COLUMN_OPTION_THREE + " TEXT NOT NULL, " +
                QuestionEntry.COLUMN_CORRECT_ANSWER + " TEXT NOT NULL" +
                "); ";
        db.execSQL(CREATE_QUESTION_LIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABL IF EXISTS " + QuestionEntry.TABLE_NAME);
        onCreate(db);
    }

    public void readQuestionFromResource(SQLiteDatabase db) throws IOException, JSONException{
        StringBuilder builder = new StringBuilder();
        InputStream in = mResources.openRawResource(R.raw.questions);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        while((line = reader.readLine()) != null){
            builder.append(line);
        }

        final String rawJson = builder.toString();

        JSONObject object = new JSONObject(rawJson);

        JSONArray questions = object.getJSONArray("questions");
        for (int i = 0; i < questions.length(); i++) {

            JSONObject question = questions.getJSONObject(i);
            ContentValues values = new ContentValues();

            values.put(COLUMN_QUESTION, question.getString("question"));
            values.put(COLUMN_OPTION_ONE, question.getString("optionOne"));
            values.put(COLUMN_OPTION_TWO, question.getString("optionTwo"));
            values.put(COLUMN_OPTION_THREE, question.getString("optionThree"));
            values.put(COLUMN_CORRECT_ANSWER, question.getString("correctAnswer"));

            db.insert(QuestionEntry.TABLE_NAME, null, values);
        }
    }
}
