package com.example.android.photography.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.android.photography.R;
import com.example.android.photography.data.DatabaseManager;
import com.example.android.photography.data.Question;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.photography.MainActivity.TAG;
import static com.example.android.photography.MainActivity.getQuestions;


public class MainFragment extends Fragment implements View.OnClickListener {

    OnAssessmentSelectedListener mCallback;
    @Nullable
    @BindView(R.id.header_view_flipper)
    ViewFlipper headerViewFlipper;
    @BindView(R.id.assessment_button)
    Button takeTestButton;
    @BindView(R.id.take_course_button)
    Button takeCourseButton;
    DatabaseManager databaseManager;
    String[] video_links = {"https://www.youtube.com/watch?v=6_B8pVoANyY",
            "https://www.youtube.com/watch?v=0q_qfSrTcLI",
            "https://www.youtube.com/watch?v=7cOb2qlXsDY",
            "https://www.youtube.com/watch?v=nQJQg3VBT7Q"};
    int videoIndex = 0;

    public static MainFragment newInstance() {
        return new MainFragment();


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        databaseManager = new DatabaseManager(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.bind(this, view);

        headerViewFlipper.setAutoStart(true);
        takeTestButton.setOnClickListener(this);
        takeCourseButton.setOnClickListener(this);
        databaseManager = new DatabaseManager(getContext());

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.main_toolbar);
        toolbar.setTitle(getContext().getString(R.string.app_name));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnAssessmentSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    "must implement OnAssessmentSelectedListener");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
    public void onClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.assessment_button) {
            ArrayList<Question> questions;
            questions = getQuestions(databaseManager.queryAllQuestions());
            mCallback.onAssessmentSelected(questions);
        }

        if (viewId == R.id.take_course_button) {

            Random random = new Random();
            videoIndex = random.nextInt(video_links.length);

            Uri uri = Uri.parse(video_links[videoIndex]);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);

            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Log.i(TAG, "Could not call" + uri.toString() + ", no receiving app"
                        + "installed on your device!");
                Toast.makeText(getContext(), "Could not call " + uri.toString() + ", no received app"
                        + "installed on your device!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public interface OnAssessmentSelectedListener {
        void onAssessmentSelected(ArrayList<Question> question);
    }
}
