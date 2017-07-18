package com.example.android.photography.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.photography.FeedbackActivity;
import com.example.android.photography.R;
import com.example.android.photography.data.Question;
import com.example.android.photography.views.TimeLeftView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizFragment extends Fragment implements View.OnClickListener {

    public static final int QUIZ_SIZE = 5;
    public static final int QUIZ_TIME = 60 * 1000;
    public static Context mContext;
    int index = 0, questionNo = 1, correctAnswerCount = 0, wrongAnswerCount = 0;
    String currentAnswer;
    public static List<Question> questions;
    @BindView(R.id.question_id_tv)
    TextView questionIdTV;
    @BindView(R.id.time_left)
    TextView timeLeftTV;
    @BindView(R.id.question_tv)
    TextView questionTV;
    @BindView(R.id.option_one_tv)
    Button optionOneButton;
    @BindView(R.id.option_two_tv)
    Button optionTwoButton;
    @BindView(R.id.option_three_tv)
    Button optionThreeButton;
    @BindView(R.id.time_view)
    TimeLeftView timeLeftView;

    CountDownTimer quizTIme;

    public static QuizFragment newInstance(Context context, List<Question> q) {
        questions = q;
        mContext = context;
        return new QuizFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        ButterKnife.bind(this, view);


        optionOneButton.setOnClickListener(this);
        optionTwoButton.setOnClickListener(this);
        optionThreeButton.setOnClickListener(this);


        buildQuiz(questions);

        return view;
    }


    //This method is used to display quiz questions and options to the user
    public void buildQuiz(List<Question> questions) {

        if (index > questions.size() - 1) {
            stopTimer();
            showResults();
        } else {
            Question currentQuestion = questions.get(index);

            String question = getString(R.string.question_text, currentQuestion.question);
            String questionId = getString(R.string.question_id_text, questionNo, QUIZ_SIZE);
            String option_one = getString(R.string.option_one, currentQuestion.optionOne);
            String option_two = getString(R.string.option_two, currentQuestion.optionTwo);
            String option_three = getString(R.string.option_three, currentQuestion.optionThree);
            questionIdTV.setText(questionId);
            questionTV.setText(question);
            optionOneButton.setText(option_one);
            optionTwoButton.setText(option_two);
            optionThreeButton.setText(option_three);
            currentAnswer = currentQuestion.correctAnswer;
            if (quizTIme != null)
                stopTimer();
            startQuizTimer(QUIZ_TIME);
            index++;
            questionNo++;
        }
    }

    //This method is called when the user answers the last question of the quiz
    private void showResults() {
        Intent intent = new Intent(mContext, FeedbackActivity.class);
        if (correctAnswerCount >= 3) {
            intent.putExtra(FeedbackActivity.PASS_STATUS, true);
        } else {
            intent.putExtra(FeedbackActivity.PASS_STATUS, false);
        }
        intent.putExtra(FeedbackActivity.TIMEOUT_STATUS, false);
        startActivity(intent);
    }

    //This method keeps time per question
    public void startQuizTimer(long timeLeftMillis) {
        quizTIme = new CountDownTimer(timeLeftMillis, 1000) {
            @Override
            public void onTick(long millis) {
                long minutes = millis / (60 * 1000);
                long seconds = millis / 1000;
                timeLeftTV.setText(minutes + " : " + seconds);
                timeLeftView.setValue((int) millis / 1000);
            }

            @Override
            public void onFinish() {
                timeLeftTV.setText("0 : 00");
                timeLeftView.setValue(0);
                showTimeUp();
            }
        }.start();
    }

    public void stopTimer() {
        timeLeftTV.setText("0 : 00");
        timeLeftView.setValue(0);
        quizTIme.cancel();
    }

    //This method is called when the time runs out before the user is able to
    // provide an answer to the given question.
    private void showTimeUp() {
        Intent intent = new Intent(mContext, FeedbackActivity.class);
        intent.putExtra(FeedbackActivity.TIMEOUT_STATUS, true);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        String answer = null;
        if (viewId == R.id.option_one_tv) {
            answer = optionOneButton.getText().toString();
        } else if (viewId == R.id.option_two_tv) {
            answer = optionTwoButton.getText().toString();
        } else if (viewId == R.id.option_three_tv) {
            answer = optionThreeButton.getText().toString();
        }
        checkAnswer(answer, currentAnswer);

        buildQuiz(questions);
    }

    //This method checks the answer provided by the user against the answer to the
    // selected question and update the related variables accordingly
    private void checkAnswer(String selected, String answer) {
        if (selected.equals(answer)) {
            correctAnswerCount++;
        } else {
            wrongAnswerCount++;
        }
    }
}
