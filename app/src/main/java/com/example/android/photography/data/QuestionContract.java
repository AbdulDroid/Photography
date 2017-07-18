package com.example.android.photography.data;

import android.provider.BaseColumns;

/**
 * Created by Abdulkarim on 7/17/2017.
 */

public class QuestionContract {

    public static final class QuestionEntry implements BaseColumns{

        public static final String TABLE_NAME = "question_list";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION_ONE = "option_one";
        public static final String COLUMN_OPTION_TWO = "option_two";
        public static final String COLUMN_OPTION_THREE = "option_three";
        public static final String COLUMN_CORRECT_ANSWER = "correct_answer";
    }
}
