package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;

    private Button mCheatButton;
    private boolean mIsCheater;
    private boolean mIsCheated[];


    private TextView mQuestionTextView;

    private static final String KEY_INDEX = "index";
    private static final String CHEAT_STATUS = "cheat_status";
    private int mCurrentIndex = 0;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBooleanArray(CHEAT_STATUS, mIsCheated);
    }

    private void nextQuestion(boolean isForward){
        int res = (isForward) ? mCurrentIndex++ : mCurrentIndex--;
        mCurrentIndex = (mCurrentIndex + mQuestionBank.length) % mQuestionBank.length;
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
    }

    private void checkAnswer(boolean userResponse){
        int messageResId = 0;

        if(mIsCheated[mCurrentIndex]){
            messageResId = R.string.judgment_toast;
        }
        else {
            if (userResponse == mQuestionBank[mCurrentIndex].isAnswerTrue())
                messageResId = R.string.correct_response;
            else
                messageResId = R.string.incorrect_response;
        }
        Toast.makeText(QuizActivity.this, messageResId,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Enter OnCreate() %d");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mIsCheated = new boolean[mQuestionBank.length];

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheated = savedInstanceState.getBooleanArray(CHEAT_STATUS);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getTextResId());
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion(true);
            }
        });
        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {checkAnswer(true);}
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion(true);
            }
        });

        mPrevButton = (ImageButton)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion(false);
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cheatIntent = CheatActivity.newIntent(QuizActivity.this,
                                                mQuestionBank[mCurrentIndex].isAnswerTrue());
                startActivityForResult(cheatIntent, REQUEST_CODE_CHEAT);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Log.d(TAG, "Exit OnCreate()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mIsCheated[mCurrentIndex] = false;
        /*
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        */
        if (requestCode == REQUEST_CODE_CHEAT && data != null) {
            mIsCheated[mCurrentIndex] = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart(){
        Log.d(TAG, "OnStart()");
        super.onStart();
    }

    @Override
    public void onPause(){
        Log.d(TAG, "onPause()");
        super.onPause();
    }

    @Override
    public void onStop(){
        Log.d(TAG, "onStop()");
        super.onStop();
    }

    @Override
    public void onResume(){
        Log.d(TAG, "onResume()");
        super.onResume();
    }

    @Override
    public void onDestroy(){
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }
}
