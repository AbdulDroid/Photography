package com.example.android.photography;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.android.photography.data.DatabaseManager;
import com.example.android.photography.data.Question;
import com.example.android.photography.data.QuestionDbHelper;
import com.example.android.photography.fragments.QuizFragment;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_CORRECT_ANSWER;
import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_OPTION_ONE;
import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_OPTION_THREE;
import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_OPTION_TWO;
import static com.example.android.photography.data.QuestionContract.QuestionEntry.COLUMN_QUESTION;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,QuizFragment.OnFragmentInteractionListener {

    @BindView(R.id.header_view_flipper)
    ViewFlipper headerViewFlipper;
    @BindView(R.id.assessment_button)
    Button takeTestButton;
    @BindView(R.id.take_course_button)
    Button takeCourseButton;
    public static Question singleQuestion;
    QuestionDbHelper dbHelper;
    private DatabaseManager databaseManager;

    public static final String TAG = MainActivity.class.getSimpleName();
    String[] video_links = {"https://www.youtube.com/watch?v=6_B8pVoANyY",
            "https://www.youtube.com/watch?v=0q_qfSrTcLI",
            "https://www.youtube.com/watch?v=7cOb2qlXsDY",
            "https://www.youtube.com/watch?v=nQJQg3VBT7Q"};
    int videoIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);

        setSupportActionBar(toolbar);

        databaseManager = new DatabaseManager(this);
        headerViewFlipper.setAutoStart(true);
        takeTestButton.setOnClickListener(this);
        takeCourseButton.setOnClickListener(this);

        dbHelper = new QuestionDbHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor testCursor = databaseManager.queryAllQuestions();

        if (database == null || testCursor.getCount() == 0){
            try{
                dbHelper.readQuestionFromResource(database);
                Log.i(TAG, "Added resource from json file");
            }catch (IOException | JSONException e){
                e.printStackTrace();
            }
        }
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

        if (itemId == R.id.action_setting){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.assessment_button){
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            ArrayList<Question> randomQuestions = getQuestions(databaseManager.queryAllQuestions());
            intent.putParcelableArrayListExtra(QuizActivity.EXTRA_QUESTION, randomQuestions);
            startActivity(intent);
        }

        if (viewId == R.id.take_course_button){

            Random random = new Random();
            videoIndex = random.nextInt(video_links.length);

            Uri uri = Uri.parse(video_links[videoIndex]);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);

            if (intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }else {
                Log.i(TAG, "Could not call" + uri.toString() + ", no receiving app"
                        +"installed on your device!");
                Toast.makeText(this, "Could not call " + uri.toString() + ", no received app"
                        + "installed on your device!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static ArrayList<Question> getQuestions(Cursor data){
        ArrayList<Question> questionList = new ArrayList<>();

        if (data != null && data.moveToFirst()){
            do {
               Question question = new Question();

                question.question = data.getString(data.getColumnIndex(COLUMN_QUESTION));
                question.optionOne = data.getString(data.getColumnIndex(COLUMN_OPTION_ONE));
                question.optionTwo = data.getString(data.getColumnIndex(COLUMN_OPTION_TWO));
                question.optionThree = data.getString(data.getColumnIndex(COLUMN_OPTION_THREE));
                question.correctAnswer = data.getString(data.getColumnIndex(COLUMN_CORRECT_ANSWER));

                questionList.add(question);
            }while (data.moveToNext());
        }

        Collections.shuffle(questionList);
        return new ArrayList<>(questionList.subList(0, QuizActivity.QUIZ_SIZE));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
