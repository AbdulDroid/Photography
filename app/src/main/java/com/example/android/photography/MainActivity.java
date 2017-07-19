package com.example.android.photography;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.photography.data.DatabaseManager;
import com.example.android.photography.data.Question;
import com.example.android.photography.data.QuestionDbHelper;
import com.example.android.photography.fragments.FeedbackFragment;
import com.example.android.photography.fragments.MainFragment;
import com.example.android.photography.fragments.QuizFragment;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.ButterKnife;

import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_CORRECT_ANSWER;
import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_OPTION_ONE;
import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_OPTION_THREE;
import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_OPTION_TWO;
import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_QUESTION;

public class MainActivity extends AppCompatActivity implements
        MainFragment.OnAssessmentSelectedListener, QuizFragment.OnFeedbackReadyListener,
        FeedbackFragment.OnButtonClickListener {


    public static final String TAG = MainActivity.class.getSimpleName();
    QuestionDbHelper dbHelper;
    DatabaseManager databaseManager;
    MainFragment mainFragment;
    QuizFragment quizFragment;
    FeedbackFragment feedbackFragment;

    public static ArrayList<Question> getQuestions(Cursor data) {
        ArrayList<Question> questionList = new ArrayList<>();

        if (data != null && data.moveToFirst()) {
            do {
                Question question = new Question();

                question.question = data.getString(data.getColumnIndex(COLUMN_QUESTION));
                question.optionOne = data.getString(data.getColumnIndex(COLUMN_OPTION_ONE));
                question.optionTwo = data.getString(data.getColumnIndex(COLUMN_OPTION_TWO));
                question.optionThree = data.getString(data.getColumnIndex(COLUMN_OPTION_THREE));
                question.correctAnswer = data.getString(data.getColumnIndex(COLUMN_CORRECT_ANSWER));

                questionList.add(question);
            } while (data.moveToNext());
        }

        Collections.shuffle(questionList);
        return new ArrayList<>(questionList.subList(0, QuizFragment.QUIZ_SIZE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        databaseManager = new DatabaseManager(this);

        dbHelper = new QuestionDbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor testCursor = databaseManager.queryAllQuestions();

        if (database == null || testCursor.getCount() == 0) {
            try {
                dbHelper.readQuestionFromResource(database);
                Log.i(TAG, "Added resource from json file");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }

        mainFragment = MainFragment.newInstance();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mainFragment, R.id.view_container);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_setting) {
            return true;
        }
        if (itemId == android.R.id.home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAssessmentSelected(ArrayList<Question> question) {
        quizFragment = QuizFragment.newInstance(question);
        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), quizFragment,
                R.id.view_container);
    }

    @Override
    public void onFeedbackReady(boolean pass, boolean timeout) {
        feedbackFragment = FeedbackFragment.newInstance(pass, timeout);
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), feedbackFragment,
                R.id.view_container);
    }

    @Override
    public void onContinueClicked() {
        feedbackFragment = FeedbackFragment.newInstance(false, false);
        ActivityUtils.removeFragmentFromActivity(getSupportFragmentManager(), feedbackFragment);
    }

    @Override
    public void onQuitClicked() {
        mainFragment = MainFragment.newInstance();
        feedbackFragment = FeedbackFragment.newInstance(false, false);
        ActivityUtils.removeFragmentFromActivity(getSupportFragmentManager(), feedbackFragment);
        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), mainFragment,
                R.id.view_container);
    }

    @Override
    public void onBackPressed() {
        /*        int count = getSupportFragmentManager().getBackStackEntryCount();
        FragmentManager fragmentManager = getSupportFragmentManager();

        for (Fragment fragment : fragmentManager.getFragments()) {
            if (fragment instanceof MainFragment) {
                super.onBackPressed();
                finish();
                return;
            }
            if (fragment == null) {
                super.onBackPressed();
                finish();
                return;
            }
            if (fragment.isVisible()) {
                FragmentManager childFM = fragment.getChildFragmentManager();
                if (childFM.getFragments() == null) {
                    super.onBackPressed();
                    finish();
                    return;
                }
                if (childFM.getBackStackEntryCount() > 0) {
                    childFM.popBackStack();
                    return;
                } else {
                    fragmentManager.popBackStack();
                    if (fragmentManager.getFragments().size() <= 1) {
                        finish();
                    }
                    return;
                }
            }
        }
    }*/
        //        if(count==0){
//            super.onBackPressed();
//        }else{
//            getSupportFragmentManager().popBackStack();
//        }
    }
}