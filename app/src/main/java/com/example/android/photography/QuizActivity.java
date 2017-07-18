package com.example.android.photography;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.photography.data.Question;
import com.example.android.photography.fragments.QuizFragment;

import java.util.List;

import butterknife.ButterKnife;

public class QuizActivity extends AppCompatActivity {


    public static final String KEY_VALUES = "key-values";
    public static final String EXTRA_QUESTION = "questions";
    QuizFragment quizFragment;
    List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.quiz_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }


        questions = getIntent().getParcelableArrayListExtra(EXTRA_QUESTION);
        quizFragment = QuizFragment.newInstance(this, questions);

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), quizFragment, R.id.container);
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

        return super.onOptionsItemSelected(item);
    }
}
