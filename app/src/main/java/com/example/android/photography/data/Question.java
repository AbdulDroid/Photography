package com.example.android.photography.data;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_CORRECT_ANSWER;
import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_OPTION_ONE;
import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_OPTION_THREE;
import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_OPTION_TWO;
import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_QUESTION;

/**
 * Created by Abdulkarim on 7/17/2017.
 */

public final class Question implements Parcelable {

    //Quiz Question
    public String question;
    //First option for the quiz
    public String optionOne;
    //Second option for the quiz
    public String optionTwo;
    //Third option for the quiz
    public String optionThree;
    //Correct answer for the quiz
    public String correctAnswer;

    /**
     *Creates a new Question from discrete values
     */
    public Question(String question, String optionOne, String optionTwo, String optionThree, String correctAnswer){
        this.question = question;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionThree = optionThree;
        this.correctAnswer = correctAnswer;
    }

    /*
     * Creates a new Question from a daatbase cursor
     */
    public Question (Cursor cursor){
        if (cursor != null) {
            this.question = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION));
            this.optionOne = cursor.getString(cursor.getColumnIndex(COLUMN_OPTION_ONE));
            this.optionTwo = cursor.getString(cursor.getColumnIndex(COLUMN_OPTION_TWO));
            this.optionThree = cursor.getString(cursor.getColumnIndex(COLUMN_OPTION_THREE));
            this.correctAnswer = cursor.getString(cursor.getColumnIndex(COLUMN_CORRECT_ANSWER));
        }
    }

    /*
     * Create a new Question from a data Parcel
     */
    protected Question(Parcel in){
        this.question = in.readString();
        this.optionOne = in.readString();
        this.optionTwo = in.readString();
        this.optionThree = in.readString();
        this.correctAnswer = in.readString();
    }

    public Question(){

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(optionOne);
        dest.writeString(optionTwo);
        dest.writeString(optionThree);
        dest.writeString(correctAnswer);
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
