package com.example.android.photography.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.photography.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class FeedbackFragment extends Fragment implements View.OnClickListener {

    static boolean passed, timeoutChecker;
    OnButtonClickListener mCallback;
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

    public static FeedbackFragment newInstance(boolean pass, boolean timeout) {
        passed = pass;
        timeoutChecker = timeout;
        return new FeedbackFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.AppTheme_Dialog);
        LayoutInflater layoutInflater = inflater.cloneInContext(contextThemeWrapper);
        View view = layoutInflater.inflate(R.layout.fragment_feedback, container, false);
        ButterKnife.bind(this, view);

        continueButton.setOnClickListener(this);
        quitTest.setOnClickListener(this);
        nextButton.setOnClickListener(this);

        setViews(passed, timeoutChecker);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnButtonClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    "must implement OnButtonClickListener");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continue_button:
                mCallback.onQuitClicked();
                break;
            case R.id.quit_test_button:
                mCallback.onQuitClicked();
                break;
            case R.id.next_question:
                mCallback.onContinueClicked();
                break;
        }
    }

    public void setViews(boolean pass, boolean timeout) {
        if (timeout) {
            imageView.setImageResource(R.drawable.time_up_drawable);
            imageView.setBorderColor(ContextCompat.getColor(getContext(), R.color.black));
            userStatusTV.setText(getString(R.string.time_up));
            userCommentTV.setText(getString(R.string.time_up_text));
            continueButton.setVisibility(View.GONE);
            timeoutOptions.setVisibility(View.VISIBLE);
        } else {
            if (pass) {
                imageView.setImageResource(R.drawable.pass_drawable);
                imageView.setBorderColor(ContextCompat.getColor(getContext(), R.color.optionButtonColor));
                userStatusTV.setText(getString(R.string.you_passed));
                userStatusTV.setTextColor(ContextCompat.getColor(getContext(), R.color.optionButtonColor));
                userCommentTV.setText(getString(R.string.passed_text));
                continueButton.setVisibility(View.VISIBLE);
                timeoutOptions.setVisibility(View.GONE);
            } else {
                imageView.setImageResource(R.drawable.fail_drawable);
                imageView.setBorderColor(ContextCompat.getColor(getContext(), R.color.red_color));
                userStatusTV.setText(getString(R.string.you_failed));
                userStatusTV.setTextColor(ContextCompat.getColor(getContext(), R.color.red_color));
                userCommentTV.setText(getString(R.string.failure_text));
                continueButton.setVisibility(View.VISIBLE);
                timeoutOptions.setVisibility(View.GONE);
            }
        }
    }

    public interface OnButtonClickListener {
        void onContinueClicked();

        void onQuitClicked();
    }
}
