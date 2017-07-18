package com.example.android.photography;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String PASS_STATUS = "pass_fail";
    public static final String TIMEOUT_STATUS = "timeout";

    boolean passed, timeout;
    @BindView(R.id.indicator_iv)
    CircleImageView imageView;
    @BindView(R.id.status)
    TextView userStatusTV;
    @BindView(R.id.status_comment)
    TextView userCommentTV;
    @BindView(R.id.continue_button)
    Button continueButton;
    @BindView(R.id.time_out_options)
    RelativeLayout timeoutOptions;
    @BindView(R.id.quit_test_button)
    Button quitTest;
    @BindView(R.id.next_question)
    Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        if(intent != null){
            passed = intent.getBooleanExtra(PASS_STATUS, false);
            timeout = intent.getBooleanExtra(TIMEOUT_STATUS, false);
        }
        continueButton.setOnClickListener(this);
        quitTest.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        setViews(passed, timeout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.continue_button:
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                break;
            case R.id.quit_test_button:
                NavUtils.navigateUpTo(this, new Intent(this, MainActivity.class));
                break;
            case R.id.next_question:
                finish();
                break;
        }
    }

    public void setViews(boolean pass, boolean timeout){
        if (timeout){
            imageView.setImageResource(R.drawable.time_up_drawable);
            imageView.setBorderColor(ContextCompat.getColor(this, R.color.black));
            userStatusTV.setText(getString(R.string.time_up));
            userCommentTV.setText(getString(R.string.time_up_text));
            continueButton.setVisibility(View.GONE);
            timeoutOptions.setVisibility(View.VISIBLE);
        }else {
            if (pass) {
                imageView.setImageResource(R.drawable.pass_drawable);
                imageView.setBorderColor(ContextCompat.getColor(this, R.color.optionButtonColor));
                userStatusTV.setText(getString(R.string.you_passed));
                userStatusTV.setTextColor(ContextCompat.getColor(this, R.color.optionButtonColor));
                userCommentTV.setText(getString(R.string.passed_text));
                continueButton.setVisibility(View.VISIBLE);
                timeoutOptions.setVisibility(View.GONE);
            } else {
                imageView.setImageResource(R.drawable.fail_drawable);
                imageView.setBorderColor(ContextCompat.getColor(this, R.color.red_color));
                userStatusTV.setText(getString(R.string.you_failed));
                userStatusTV.setTextColor(ContextCompat.getColor(this, R.color.red_color));
                userCommentTV.setText(getString(R.string.failure_text));
                continueButton.setVisibility(View.VISIBLE);
                timeoutOptions.setVisibility(View.GONE);
            }
        }
    }
}
